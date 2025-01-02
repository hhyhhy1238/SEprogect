package com.mxy.bbs_server.entity;

import com.mxy.bbs_server.utility.Utility;
import jdk.jshell.execution.Util;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class UserInfoData {
    private String username;
    private String nickname;
    private String personalSign;
    private String avatarUrl;
    private String myPosts;
    private String myCollections;

    public UserInfo convertToUserInfo(){
        return new UserInfo(this.username,this.nickname,this.personalSign,this.avatarUrl,
                Utility.fromJson(this.myPosts,ArrayList.class),
                Utility.fromJson(this.myCollections, ArrayList.class));
    }
}
