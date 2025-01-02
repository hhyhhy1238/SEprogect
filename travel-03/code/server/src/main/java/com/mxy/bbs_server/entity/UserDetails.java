package com.mxy.bbs_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDetails {
    private String username;
    private String gender;
    private String birthday;
    private String registerTime;
    private String signature;
}
