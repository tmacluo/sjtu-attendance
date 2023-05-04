package com.sjtu.checkin.service.impl;

import com.sjtu.checkin.dao.BeaconMapper;
import com.sjtu.checkin.model.Beacon;
import com.sjtu.checkin.service.BeaconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BeaconServiceImpl implements BeaconService {
    @Autowired
    private BeaconMapper beaconMapper;

    @Override
    public List<Beacon> getBeacons() {
        return beaconMapper.getBeacons();
    }

    @Override
    public void saveBeacon(Beacon beacon) {
        beaconMapper.saveBeacon(beacon);
    }
}
