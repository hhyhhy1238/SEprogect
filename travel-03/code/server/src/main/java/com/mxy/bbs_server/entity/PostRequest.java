package com.mxy.bbs_server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@Builder
public class PostRequest {
    private String id;
    private String planId;
    private String owner;
    private String title;
    private String content;
    private MultipartFile[] images;
}
