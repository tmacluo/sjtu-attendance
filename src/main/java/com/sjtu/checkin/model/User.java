package com.sjtu.checkin.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
public class User {
    private String name;
    private String avatar;
    private String userid;
    private String email;
    private String signature;
    private String title;
    private String group;
    private List<Label> tags;
    private Integer notifyCount;
    private Integer unreadCount;
    private String country;
    private String access;
    private Geographic geographic;
    private String address;
    private String phone;

    @Data
    @AllArgsConstructor
    public static class Geographic {
        private Label province;
        private Label city;
    }

    @Data
    @AllArgsConstructor
    public static class Label {
        private String key;
        private String label;
    }

}
