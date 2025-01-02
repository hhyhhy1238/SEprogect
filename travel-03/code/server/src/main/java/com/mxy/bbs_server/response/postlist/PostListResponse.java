package com.mxy.bbs_server.response.postlist;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostListResponse {
    private Boolean success;
    private List<String> postIds;
}
