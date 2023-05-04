package com.sjtu.checkin.dao;

import com.sjtu.checkin.model.Checkin;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class SelectProvider {
    public String query(Checkin checkin) {
        return new SQL() {{
            SELECT("*");
            FROM("checkin");
            if (StringUtils.hasLength(checkin.getClassroom())) {
                WHERE("classroom = #{classroom}");
            }
            if (StringUtils.hasLength(checkin.getStudentNo())){
                WHERE("student_no = #{studentNo}");
            }
        }}.toString();
    }
}
