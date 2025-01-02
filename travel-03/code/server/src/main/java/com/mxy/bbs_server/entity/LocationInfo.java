package com.mxy.bbs_server.entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationInfo {
    private String locationName;
    private String locationDetail;
    private String locationPictureUrl;
}
