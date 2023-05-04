package com.sjtu.checkin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class Checkin {
    private String id;
    private String studentNo;
    private String studentName;
    private String classroom;
    private ZonedDateTime createdTime;
    private ZonedDateTime lastUpdatedTime;
    private List<Beacon> beacons;
    @JsonIgnore
    private String beaconsJson;
    @Data
    public static class Beacon {
        private String name;
        private String rssi;
    }
}
