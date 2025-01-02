package com.mxy.bbs_server.mapper;

import com.mxy.bbs_server.entity.LocationInfo;
import com.mxy.bbs_server.entity.TravelPlanDetailsData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LocationMapper {
    @Select("Select * from LocationInfo where LocationName = #{LocationName}")
    LocationInfo query(String LocationName);
}
