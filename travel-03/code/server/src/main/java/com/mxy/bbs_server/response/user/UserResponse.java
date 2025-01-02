package com.mxy.bbs_server.response.user;

import com.mxy.bbs_server.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Boolean success;
    private UserResponseFailedReason userResponseFailedReason;
    private User user;
}

