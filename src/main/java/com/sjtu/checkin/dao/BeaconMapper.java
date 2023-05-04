package com.sjtu.checkin.dao;

import com.sjtu.checkin.model.Beacon;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BeaconMapper {
    @Select("SELECT * FROM beacon")
    List<Beacon> getBeacons() ;

    @Insert("insert into beacon (`id`, `name`, `classroom`, `x`, `y`, `n`) values (#{id}, #{name}, #{classroom}, #{x}, #{y}, #{n})")
    void saveBeacon(Beacon beacon);
}
