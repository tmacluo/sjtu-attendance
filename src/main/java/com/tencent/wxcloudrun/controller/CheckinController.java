package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckinController {


    @PostMapping(value = "/api/checkin")
    ApiResponse getBeacon() {



        return ApiResponse.ok();
    }
}
