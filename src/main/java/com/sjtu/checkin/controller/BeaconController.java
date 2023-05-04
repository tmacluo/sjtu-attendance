package com.sjtu.checkin.controller;

import com.sjtu.checkin.config.ApiPaginationResponse;
import com.sjtu.checkin.config.ApiResponse;
import com.sjtu.checkin.model.Beacon;
import com.sjtu.checkin.service.BeaconService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class BeaconController {
    @Autowired
    private BeaconService beaconService;

    @GetMapping(value = "/api/beacon")
    ApiResponse getBeacon() {
        return ApiResponse.ok(beaconService.getBeacons());
    }
    @PostMapping(value = "/api/beacons")
    ApiResponse createBeacon(@RequestBody Beacon beacon) {
        beaconService.saveBeacon(beacon);
        return ApiResponse.ok();
    }

    @GetMapping(value = "/api/beacons")
    ApiPaginationResponse getBeacons(@RequestParam Map<String, String> params) {
        List<Beacon> beacons = beaconService.getBeacons();
        beacons = filter(params, beacons);
        return ApiPaginationResponse.ok(beacons, beacons.size(), 5, 1);
    }

    private List<Beacon> filter(Map<String, String> params, List<Beacon> beacons) {
        if (!CollectionUtils.isEmpty(params)) {
            String name = params.get("name");
            if (StringUtils.hasLength(name)) {
                beacons = beacons.stream().filter(beacon -> name.equals(beacon.getName())).collect(Collectors.toList());
            }
            String id = params.get("id");
            if (StringUtils.hasLength(id)) {
                beacons = beacons.stream().filter(beacon -> id.equals(beacon.getId())).collect(Collectors.toList());
            }
            String classroom = params.get("classroom");
            if (StringUtils.hasLength(classroom)) {
                beacons = beacons.stream().filter(beacon -> classroom.equals(beacon.getClassroom())).collect(Collectors.toList());
            }
        }
        return beacons;
    }
}
