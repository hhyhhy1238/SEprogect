package com.mxy.bbs_server.service;

import com.mxy.bbs_server.entity.TravelPlan;
import com.mxy.bbs_server.entity.TravelPlanDetailsData;
import com.mxy.bbs_server.entity.TravelPlanDetails;
import com.mxy.bbs_server.response.Travel.TravelPlanResponse;
import com.mxy.bbs_server.response.Travel.TravelPlanUserResponse;
import com.mxy.bbs_server.mapper.TravelPlansMapper;
import com.mxy.bbs_server.utility.Utility;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
@Log
public class TravelPlansService {
    private final TravelMapService travelMapService = new TravelMapService();

    private final TravelPlansMapper travelPlansMapper;

    public TravelPlansService(TravelPlansMapper travelPlansMapper){
        this.travelPlansMapper = travelPlansMapper;
    }

    public TravelPlanResponse query(TravelPlan travelPlan){

        Random random = new Random(System.currentTimeMillis());
        String id = String.valueOf(random.nextInt());

        TravelPlanDetails travelPlanDetails = new TravelPlanDetails(id,travelMapService.findShortestPath(travelPlan));
//        System.out.println("----DEBUG:travelPlanMapper.addPlan() :: " + travelPlanDetails.getId() + travelPlanDetails.getLocations());

        travelPlansMapper.addPlan(
                new TravelPlanDetailsData(id,
                                      Utility.toJson(travelPlanDetails.getLocations())
                )
        );

        return new TravelPlanResponse(true,null,travelPlanDetails);

    }


//    public TravelPlanUserResponse add(TravelPlanUser travelPlanUser){
//
//
//        //TODO:这里是更新绑定用户id和行程id数据库
//    }


}
