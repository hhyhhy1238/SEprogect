package com.mxy.bbs_server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TravelPlan {
    private String StartLocation;
    private String EndLocation;
}
