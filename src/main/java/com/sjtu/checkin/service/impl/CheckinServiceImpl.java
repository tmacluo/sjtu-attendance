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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CheckinServiceImpl implements CheckinService {
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
        if (StringUtils.hasLength(checkin.getStudentNo())){
            boolean isMatched = checkin.getStudentNo().matches("^\\d{12}$");
            if (!isMatched) {
                throw new Exception("学号不符合规范");
            }
        }

        List<Beacon> availableBeacons = beaconService.getBeacons();
        checkin.setClassroom(determineClassroom(availableBeacons, checkin.getBeacons()));
        checkin.setCreatedTime(ZonedDateTime.now(ZoneOffset.UTC));
        checkin.setLastUpdatedTime(ZonedDateTime.now(ZoneOffset.UTC));
        encodeBeacon(checkin);
        checkinMapper.saveCheckins(checkin);
        return checkin;
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
                    .filter(beacon -> beacon.getId().equalsIgnoreCase(strongestBeacon.get().getBeaconName().replaceAll("-","")))
                    .findFirst();
            if (matchedBeacon.isPresent()) {
                log.info("found matched classroom {}", matchedBeacon.get().getClassroom());
                return matchedBeacon.get().getClassroom();
            }
        }
        return EMPTY;
    }

    private static Optional<Checkin.Beacon> getBeaconWithStrongestSignal(List<Checkin.Beacon> checkinBeacons) {

        //FDA50693-A4E2-4FB1-AFCF-C6EB07647802


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
