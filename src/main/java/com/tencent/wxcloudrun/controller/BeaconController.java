package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiPaginationResponse;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.Beacon;
import com.tencent.wxcloudrun.service.BeaconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class BeaconController {
    @Autowired
    private BeaconService beaconService;

    @GetMapping(value = "/api/beacon")
    ApiResponse getBeacon() {
        return ApiResponse.ok(beaconService.getBeacons());
    }

    @GetMapping(value = "/api/beacons")
    ApiPaginationResponse getBeacons(@RequestParam Map<String, String> allParams) {
        List<Beacon> beacons = beaconService.getBeacons();
        beacons = filter(allParams, beacons);
        return ApiPaginationResponse.ok(beacons, beacons.size(), 5, 1);
    }

    private List<Beacon> filter(Map<String, String> allParams, List<Beacon> beacons) {
        if (!CollectionUtils.isEmpty(allParams)) {
            String name = allParams.get("name");
            if (StringUtils.hasLength(name)) {
                beacons = beacons.stream().filter(beacon -> name.equals(beacon.getName())).collect(Collectors.toList());
            }
            String id = allParams.get("id");
            if (StringUtils.hasLength(id)) {
                beacons = beacons.stream().filter(beacon -> id.equals(beacon.getId())).collect(Collectors.toList());
            }
            String classroom = allParams.get("classroom");
            if (StringUtils.hasLength(classroom)) {
                beacons = beacons.stream().filter(beacon -> classroom.equals(beacon.getClassroom())).collect(Collectors.toList());
            }
        }
        return beacons;
    }
}
