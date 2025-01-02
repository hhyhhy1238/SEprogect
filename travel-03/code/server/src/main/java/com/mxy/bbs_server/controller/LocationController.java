package com.mxy.bbs_server.controller;


import com.mxy.bbs_server.response.Travel.LocationResponse;
import com.mxy.bbs_server.service.LocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService){
        this.locationService = locationService;
    }

    @GetMapping("/query/{locationName}")
    public LocationResponse queryLocation(@PathVariable("locationName") String locationName){
//        System.out.println("LocationController got locationName:" + locationName);
        return locationService.query(locationName);
    }
}
