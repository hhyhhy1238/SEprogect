package com.mxy.bbs_server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PostData {
    private String id;
    private String locations;
    private String date;
    private String owner;
    private String title;
    private String content;
    private String images;
    private Integer likeNum;
    private String reviews;
}
