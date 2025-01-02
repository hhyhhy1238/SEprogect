package com.mxy.bbs_server.response.post;

import com.mxy.bbs_server.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostResponse {
    private Boolean success;
    private PostResponseFailedReason postResponseFailedReason;
    private Post post;
}
