package com.sjtu.checkin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.sjtu.checkin.dao"})
public class CheckinRunApplication {

  public static void main(String[] args) {
    SpringApplication.run(CheckinRunApplication.class, args);
  }
}
