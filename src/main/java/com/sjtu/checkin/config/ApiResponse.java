package com.sjtu.checkin.config;

import lombok.Data;

import java.util.HashMap;

@Data
public final class ApiResponse {

  private String message;
  private Object data;
  private boolean success;
  private String access;

  private ApiResponse(String message, Object data, boolean success) {
    this.message = message;
    this.data = data;
    this.success = true;
  }

  private ApiResponse(String message, Object data, String access, boolean success) {
    this.message = message;
    this.data = data;
    this.access = access;
    this.success = true;
  }
  public static ApiResponse ok() {
    return new ApiResponse("", new HashMap<>(), true);
  }

  public static ApiResponse ok(Object data) {
    return new ApiResponse("", data, true);
  }

  public static ApiResponse loginOk(Object data, String access) {
    return new ApiResponse("", data, access,true);
  }

  public static ApiResponse error(String errorMsg) {
    return new ApiResponse(errorMsg, new HashMap<>(), false);
  }
}
