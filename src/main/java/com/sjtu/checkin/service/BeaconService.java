package com.sjtu.checkin.service;

import com.sjtu.checkin.model.Beacon;

import java.util.List;

public interface BeaconService {
    List<Beacon> getBeacons();

    void saveBeacon(Beacon beacon);
}
