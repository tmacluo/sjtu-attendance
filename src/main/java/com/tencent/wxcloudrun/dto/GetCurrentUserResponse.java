package com.tencent.wxcloudrun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
public class GetCurrentUserResponse {

    /**
     * name?: string;
     *     avatar?: string;
     *     userid?: string;
     *     email?: string;
     *     signature?: string;
     *     title?: string;
     *     group?: string;
     *     tags?: { key?: string; label?: string }[];
     *     notifyCount?: number;
     *     unreadCount?: number;
     *     country?: string;
     *     access?: string;
     *     geographic?: {
     *       province?: { label?: string; key?: string };
     *       city?: { label?: string; key?: string };
     *     };
     *     address?: string;
     *     phone?: string;
     */

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
