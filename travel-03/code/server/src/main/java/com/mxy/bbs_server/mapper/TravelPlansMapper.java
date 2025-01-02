package com.mxy.bbs_server.mapper;

import com.mxy.bbs_server.entity.TravelPlanDetailsData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TravelPlansMapper {
    @Insert("insert into TravelPlans(id, path) values (#{id}, #{locations})")
    void addPlan(TravelPlanDetailsData travelPlanDetailsData);
}
