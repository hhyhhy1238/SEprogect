package com.mxy.bbs_server.controller;

import org.springframework.web.bind.annotation.*;

import com.mxy.bbs_server.entity.TravelPlan;
import com.mxy.bbs_server.response.Travel.TravelPlanResponse;
import com.mxy.bbs_server.service.TravelPlansService;

@RestController
@RequestMapping("/travel")
@CrossOrigin("*")
public class TravelPlanController {

    private final TravelPlansService travelPlanService;

    public TravelPlanController(TravelPlansService travelPlanService){
        this.travelPlanService = travelPlanService;
    }

    @PostMapping("/query")
    public TravelPlanResponse queryTravelPlan(@RequestBody TravelPlan travelPlan){
        return travelPlanService.query(travelPlan);
    }
}
