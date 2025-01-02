package com.mxy.bbs_server.mapper;

import com.mxy.bbs_server.entity.TravelPlanDetailsData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TravelPlansMapper {
    @Insert("insert into TravelPlans(id, path) values (#{id}, #{locations})")
    void addPlan(TravelPlanDetailsData travelPlanDetailsData);

    @Select("Select path from TravelPlans where id = #{id}")
    String query(String id);
}
