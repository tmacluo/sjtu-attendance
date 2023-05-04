package com.sjtu.checkin.controller;

import com.sjtu.checkin.config.ApiResponse;
import com.sjtu.checkin.dto.UserLoginResponse;
import com.sjtu.checkin.model.User;
import com.sjtu.checkin.dto.LoginResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


@RestController
@Slf4j
public class UserController {

    /**
     * the url violate the naming convention rule of restful API design
     * @return
     */
    @PostMapping(value = "/api/login/account")
    LoginResult login() {
        log.info("/api/login/account start to login");
        //return ApiResponse.loginOk(new UserLoginResponse("ok", "account", "admin"), "admin");
        return new LoginResult("ok", "account", "admin");
    }

    @PostMapping(value = "/api/login/outLogin")
    ApiResponse outLogin() {
        log.info("/api/login/account start to log out");
        return ApiResponse.ok(new UserLoginResponse("ok", "", ""));
    }

    @GetMapping(value = "/api/currentUser")
    ApiResponse getUser() {
        log.info("/api/login/account start to get current user");
        User response = new User();
        response.setAccess("admin");
        response.setAvatar("https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png");
        //response.setEmail();
        response.setCountry("CN");
        response.setAddress("上海交通大学");
        response.setName("tmac luo");
        response.setTags(Arrays.asList(new User.Label("a", "b"),
                new User.Label("a", "b")));
        //response.setGeographic(new GetCurrentUserResponse.Geographic(new GetCurrentUserResponse.Label("上海","上海")));
        return ApiResponse.ok(response);
    }

    /**
     * 1. get user api
     *  try to get user from redis cache
     */
}
