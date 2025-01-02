package com.mxy.bbs_server.response.Travel;

import com.mxy.bbs_server.entity.LocationInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationResponse {
    private Boolean success;
    private LocationInfo locationInfo;
}
