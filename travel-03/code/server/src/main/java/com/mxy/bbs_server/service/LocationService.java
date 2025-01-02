package com.mxy.bbs_server.service;

import com.mxy.bbs_server.entity.LocationInfo;
import com.mxy.bbs_server.mapper.LocationMapper;
import com.mxy.bbs_server.response.Travel.LocationResponse;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final LocationMapper locationMapper;

    public LocationService(LocationMapper locationMapper){
        this.locationMapper = locationMapper;
    }

    public LocationResponse query(String locationName){
        LocationInfo locationInfo = locationMapper.query(locationName);
        if(null == locationInfo){
            return new LocationResponse(false,null);
        }
        return new LocationResponse(true,
                new LocationInfo(locationInfo.getLocationName(),
                        locationInfo.getLocationDetail(),
                        locationInfo.getLocationPictureUrl())
        );
    }
}
