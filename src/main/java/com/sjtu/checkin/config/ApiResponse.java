package com.sjtu.checkin.config;

import lombok.Data;

import java.util.HashMap;

@Data
public final class ApiResponse {

  private String message;
  private Object data;
  private boolean success;

  private ApiResponse(String message, Object data, boolean success) {
    this.message = message;
    this.data = data;
    this.success = success;
  }

  public static ApiResponse ok() {
    return new ApiResponse("", new HashMap<>(), true);
  }

  public static ApiResponse ok(Object data) {
    return new ApiResponse("", data, true);
  }

  public static ApiResponse error(String errorMsg) {
    return new ApiResponse(errorMsg, new HashMap<>(), false);
  }
}
