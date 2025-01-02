package com.mxy.bbs_server.response.userinfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeOrNotResponse {
    private Boolean iLike;
    private Boolean iFavorite;
}
