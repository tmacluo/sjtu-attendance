package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.BeaconMapper;
import com.tencent.wxcloudrun.model.Beacon;
import com.tencent.wxcloudrun.service.BeaconService;
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
}
