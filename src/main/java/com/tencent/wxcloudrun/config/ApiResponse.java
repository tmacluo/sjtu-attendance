package com.tencent.wxcloudrun.config;

import lombok.Data;

import java.util.HashMap;

@Data
public final class ApiResponse {

  private Integer code;
  private String errorMessage;
  private Object data;
  private boolean success;
  private String access;

  private ApiResponse(int code, String errorMessage, Object data, boolean success) {
    this.code = code;
    this.errorMessage = errorMessage;
    this.data = data;
    this.success = true;
  }

  private ApiResponse(int code, String errorMessage, Object data, String access, boolean success) {
    this.code = code;
    this.errorMessage = errorMessage;
    this.data = data;
    this.access = access;
    this.success = true;
  }
  
  public static ApiResponse ok() {
    return new ApiResponse(0, "", new HashMap<>(), true);
  }

  public static ApiResponse ok(Object data) {
    return new ApiResponse(0, "", data, true);
  }

  public static ApiResponse loginOk(Object data, String access) {
    return new ApiResponse(0, "", data, access,true);
  }

  public static ApiResponse error(String errorMsg) {
    return new ApiResponse(0, errorMsg, new HashMap<>(), false);
  }
}
