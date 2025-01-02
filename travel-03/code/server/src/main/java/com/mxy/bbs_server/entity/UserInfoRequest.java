package com.mxy.bbs_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class UserInfoRequest {
    private String username;
    private String nickname;
    private String personalSign;
    private MultipartFile avatar;
}
