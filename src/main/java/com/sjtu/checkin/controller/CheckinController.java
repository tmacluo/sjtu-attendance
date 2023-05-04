package com.sjtu.checkin.controller;

import com.sjtu.checkin.config.ApiResponse;
import com.sjtu.checkin.model.Checkin;
import com.sjtu.checkin.service.CheckinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CheckinController {

    @Autowired
    private CheckinService checkinService;

    @PostMapping(value = "/api/checkin")
    ApiResponse checkin(@RequestBody Checkin checkin) {
        log.info("start to checkin, request:{}", checkin);
        return ApiResponse.ok(checkinService.save(checkin));
    }

    @GetMapping(value = "/api/checkin")
    ApiResponse getCheckin(@RequestParam(name = "classroom", required = false) String classroom,
                           @RequestParam(name = "studentNo", required = false) String studentNo) {
        Checkin checkin = new Checkin();
        checkin.setClassroom(classroom);
        checkin.setStudentNo(studentNo);
        log.info("start to get checkins, parameter {}", checkin);
        return ApiResponse.ok(checkinService.getCheckinsWithFilter(checkin));
    }
}
