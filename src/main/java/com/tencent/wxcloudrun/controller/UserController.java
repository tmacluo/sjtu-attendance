package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.GetCurrentUserResponse;
import com.tencent.wxcloudrun.dto.LoginResult;
import com.tencent.wxcloudrun.dto.UserLoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


@RestController
@Slf4j
public class UserController {

    private boolean isLogged = false;

    @PostMapping(value = "/api/login/account")
    LoginResult login() {
        isLogged = true;
        log.info("/api/login/account start to login");
        //return ApiResponse.loginOk(new UserLoginResponse("ok", "account", "admin"), "admin");
        return new LoginResult("ok", "account", "admin");
    }

    @PostMapping(value = "/api/login/outLogin")
    ApiResponse outLogin() {
        isLogged = true;
        log.info("/api/login/account start to log out");
        return ApiResponse.ok(new UserLoginResponse("ok", "", ""));
    }

    @GetMapping(value = "/api/currentUser")
    ApiResponse getUser() {
        log.info("/api/login/account start to get current user");
        GetCurrentUserResponse response = new GetCurrentUserResponse();
        response.setAccess("admin");
        response.setAvatar("https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png");
        //response.setEmail();
        response.setCountry("CN");
        response.setAddress("上海交通大学");
        response.setName("tmac luo");
        response.setTags(Arrays.asList(new GetCurrentUserResponse.Label("a", "b"),
                new GetCurrentUserResponse.Label("a", "b")));
        //response.setGeographic(new GetCurrentUserResponse.Geographic(new GetCurrentUserResponse.Label("上海","上海")));
        return ApiResponse.ok(response);
    }
}
