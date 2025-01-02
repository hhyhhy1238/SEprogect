package com.mxy.bbs_server.response.Travel;

import com.mxy.bbs_server.entity.TravelPlanDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TravelPlanResponse {
    private Boolean success;
    private TravelPlanResponseFailedReason travelPlanResponseFailedReason;
    private TravelPlanDetails travelPlanDetails;
}
