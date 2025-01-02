package com.mxy.bbs_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class UserDetailsRequest {
    private String username;
    private String nickname;
    private MultipartFile[] avatar;
    private String birthday;
    private String gender;
    private String signature;
}
