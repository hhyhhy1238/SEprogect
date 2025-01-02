package com.mxy.bbs_server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor

public class TravelPlanDetails {
    private String id;
    private List<String> locations;
}
