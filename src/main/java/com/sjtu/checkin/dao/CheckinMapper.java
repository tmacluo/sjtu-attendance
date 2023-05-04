package com.sjtu.checkin.dao;

import com.sjtu.checkin.model.Checkin;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface CheckinMapper {
    @Results({
            @Result(property = "studentNo", column = "student_no"),
            @Result(property = "studentName", column = "student_name"),
            @Result(property = "beaconsJson", column = "beacons"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "lastUpdatedTime", column = "last_updated_time"),
    })
    @Select("select * from checkin")
    List<Checkin> getCheckins();

    @SelectProvider(type = com.sjtu.checkin.dao.SelectProvider.class, method = "query")
    @Results({
            @Result(property = "studentNo", column = "student_no"),
            @Result(property = "studentName", column = "student_name"),
            @Result(property = "beaconsJson", column = "beacons"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "lastUpdatedTime", column = "last_updated_time"),
    })
    List<Checkin> getCheckinsWithFilter(Checkin checkin);
    @Insert("insert into checkin (`student_no`, `student_name`, `classroom`, `beacons`, `created_time`, `last_updated_time`)\n" +
            "values (#{studentNo}, #{studentName}, #{classroom}, #{beaconsJson}, #{createdTime}, #{lastUpdatedTime})")
    @SelectKey(keyProperty = "id",keyColumn = "id",statement = "select last_insert_id()", before = false, resultType = String.class)
    void saveCheckins(Checkin checkin);
}
