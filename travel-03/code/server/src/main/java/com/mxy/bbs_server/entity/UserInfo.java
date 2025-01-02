package com.mxy.bbs_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfo {
    private String username;
    private String nickname;
    private String personalSign;
    private String avatarUrl;
    private List<String> myPosts;
    private List<String> myCollections;
}
