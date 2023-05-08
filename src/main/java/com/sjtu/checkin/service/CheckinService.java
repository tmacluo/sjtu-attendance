package com.sjtu.checkin.service;

import com.sjtu.checkin.model.Checkin;

import java.util.List;

public interface CheckinService {
    Checkin save(Checkin checkin) throws Exception;
    List<Checkin> get();

    List<Checkin> getCheckinsWithFilter(Checkin checkin);
}
