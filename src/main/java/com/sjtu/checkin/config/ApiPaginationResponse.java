package com.sjtu.checkin.config;

import lombok.Data;

import java.util.HashMap;

@Data
public final class ApiPaginationResponse {

  private String message;
  private Object data;
  private boolean success;

  private int total;
  private int pageSize;
  private int current;


  private ApiPaginationResponse(String message, Object data, int total, int pageSize, int current, boolean success) {
    this.message = message;
    this.data = data;
    this.success = true;
  }
  
  public static ApiPaginationResponse ok() {
    return new ApiPaginationResponse("", new HashMap<>(), 0,0,0,true);
  }

  public static ApiPaginationResponse ok(Object data, int total, int pageSize, int current) {
    return new ApiPaginationResponse("", data, total, pageSize, current, true);
  }

  public static ApiPaginationResponse error(String errorMsg) {
    return new ApiPaginationResponse(errorMsg, new HashMap<>(), 0, 0, 0, false);
  }
}
