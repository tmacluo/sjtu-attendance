package com.tencent.wxcloudrun.config;

import lombok.Data;

import java.util.HashMap;

@Data
public final class ApiPaginationResponse {

  private Integer code;
  private String errorMessage;
  private Object data;
  private boolean success;

  private int total;
  private int pageSize;
  private int current;


  private ApiPaginationResponse(int code, String errorMessage, Object data, int total, int pageSize, int current, boolean success) {
    this.code = code;
    this.errorMessage = errorMessage;
    this.data = data;
    this.success = true;
  }
  
  public static ApiPaginationResponse ok() {
    return new ApiPaginationResponse(0, "", new HashMap<>(), 0,0,0,true);
  }

  public static ApiPaginationResponse ok(Object data, int total, int pageSize, int current) {
    return new ApiPaginationResponse(0, "", data, total, pageSize, current, true);
  }

  public static ApiPaginationResponse error(String errorMsg) {
    return new ApiPaginationResponse(0, errorMsg, new HashMap<>(), 0, 0, 0, false);
  }
}
