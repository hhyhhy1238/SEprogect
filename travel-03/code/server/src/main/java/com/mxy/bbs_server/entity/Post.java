package com.mxy.bbs_server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
@Builder
public class Post {
    private String id;
    private List<String> locations;
    private String date;
    private String owner;
    private String title;
    private String content;
    private List<String> images;
    private Integer likeNum;
    private List<String> reviews;
}
