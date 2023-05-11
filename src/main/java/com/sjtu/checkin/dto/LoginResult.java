package com.sjtu.checkin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResult {
    private String status;
    private String type;
    private String currentAuthority;
}