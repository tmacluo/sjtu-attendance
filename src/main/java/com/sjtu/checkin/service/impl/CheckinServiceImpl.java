package com.sjtu.checkin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjtu.checkin.dao.CheckinMapper;
import com.sjtu.checkin.model.Beacon;
import com.sjtu.checkin.service.CheckinService;
import com.sjtu.checkin.model.Checkin;
import com.sjtu.checkin.service.BeaconService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CheckinServiceImpl implements CheckinService {

    private static final long checkinDurationInSeconds = 120;

    public static final String EMPTY = "";
    @Autowired
    private CheckinMapper checkinMapper;
    @Autowired
    private BeaconService beaconService;
    final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Checkin> get() {
        List<Checkin> checkins = checkinMapper.getCheckins();
        checkins.forEach(this::decodeBeacon);
        return checkins;
    }

    @Override
    public List<Checkin> getCheckinsWithFilter(Checkin checkin) {
        List<Checkin> checkins = checkinMapper.getCheckinsWithFilter(checkin);
        checkins.forEach(this::decodeBeacon);
        return checkins;
    }

    @Override
    public Checkin save(Checkin checkin) throws Exception {
        if (StringUtils.hasLength(checkin.getStudentNo())) {
            boolean isMatched = checkin.getStudentNo().matches("^\\d{12}$");
            if (!isMatched) {
                throw new Exception("学号不符合规范");
            }
        }

        List<Beacon> availableBeacons = beaconService.getBeacons();
        checkin.setClassroom(determineClassroom(availableBeacons, checkin.getBeacons()));

        avoidDuplicatedCheckin(checkin);

        checkin.setCreatedTime(ZonedDateTime.now(ZoneOffset.UTC));
        checkin.setLastUpdatedTime(ZonedDateTime.now(ZoneOffset.UTC));
        encodeBeacon(checkin);
        checkinMapper.saveCheckins(checkin);
        return checkin;
    }

    private void avoidDuplicatedCheckin(Checkin checkin) throws Exception {
        List<Checkin> checkinHistory = getCheckinsWithFilter(checkin);
        if (!CollectionUtils.isEmpty(checkinHistory)) {
            val lastCheckin = checkinHistory.stream()
                    .filter(history -> StringUtils.hasLength(history.getClassroom()))
                    .max((o1, o2) -> {
                        val createdTime1 = o1.getCreatedTime();
                        val createdTime2 = o2.getCreatedTime();
                        if (createdTime1.isEqual(createdTime2)) {
                            return 0;
                        }
                        return o1.getCreatedTime().isAfter(o2.getCreatedTime()) ? 1 : -1;
                    });

            if (lastCheckin.isPresent()) {
                val seconds = Duration.between(lastCheckin.get().getCreatedTime().withZoneSameInstant(ZoneOffset.UTC),
                        ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC)).getSeconds();
                if (seconds <= checkinDurationInSeconds) {
                    throw new Exception("你已签到成功，请2分钟后再试");
                }
            }
        }
    }

    private void decodeBeacon(Checkin checkin) {
        try {
            if (StringUtils.hasLength(checkin.getBeaconsJson())) {
                checkin.setBeacons(objectMapper.readValue(checkin.getBeaconsJson(), new TypeReference<List<Checkin.Beacon>>() {
                }));
            }
        } catch (JsonProcessingException e) {
            log.error("convert json to beacon failed when getting checkin");
            throw new RuntimeException(e);
        }
    }

    private void encodeBeacon(Checkin checkin) {
        try {
            checkin.setBeaconsJson(objectMapper.writeValueAsString(checkin.getBeacons()));
        } catch (JsonProcessingException e) {
            log.error("convert beacons to json failed when doing checkin");
        }
    }

    private String determineClassroom(List<Beacon> availableBeacons, List<Checkin.Beacon> checkinBeacons) {
        if (CollectionUtils.isEmpty(checkinBeacons)) {
            return EMPTY;
        }
        Optional<Checkin.Beacon> strongestBeacon = getBeaconWithStrongestSignal(checkinBeacons);
        if (strongestBeacon.isPresent()) {
            log.info("found the beacon with strongest rssi signal {}", strongestBeacon.get());
            Optional<Beacon> matchedBeacon = availableBeacons.stream()
                    // the name passed from frontend is UUID of that beacon with dash delimiter
                    .filter(beacon -> beacon.getId().equalsIgnoreCase(strongestBeacon.get().getBeaconName().replaceAll("-", "")))
                    .findFirst();
            if (matchedBeacon.isPresent()) {
                log.info("found matched classroom {}", matchedBeacon.get().getClassroom());
                return matchedBeacon.get().getClassroom();
            }
        }
        return EMPTY;
    }

    private static Optional<Checkin.Beacon> getBeaconWithStrongestSignal(List<Checkin.Beacon> checkinBeacons) {

        return checkinBeacons.stream().max((o1, o2) -> {
            int rssi1 = Integer.parseInt(o1.getRssi());
            int rssi2 = Integer.parseInt(o2.getRssi());
            if (rssi1 == rssi2) {
                return 0;
            }
            return rssi1 > rssi2 ? 1 : -1;
        });
    }
}
