package com.mxy.bbs_server.service;

import com.mxy.bbs_server.entity.FriendshipData;
import com.mxy.bbs_server.entity.User;
import com.mxy.bbs_server.entity.UserDetails;
import com.mxy.bbs_server.entity.UserInfoData;
import com.mxy.bbs_server.mapper.*;
import com.mxy.bbs_server.response.user.UserResponse;
import com.mxy.bbs_server.response.user.UserResponseFailedReason;
import com.mxy.bbs_server.utility.Const;
import com.mxy.bbs_server.utility.NginxHelper;
import com.mxy.bbs_server.utility.Utility;
import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@Service
@Log
public class UserService {

    private final UserMapper userMapper;
    private final FriendMapper friendMapper;
    private final UserInfoMapper userInfoMapper;
    private final LikeOrNotMapper likeOrNotMapper;
    private final UserDetailsMapper userDetailsMapper;


    public UserService(UserMapper userMapper, FriendMapper friendMapper, UserInfoMapper userInfoMapper, LikeOrNotMapper likeOrNotMapper, UserDetailsMapper userDetailsMapper) {
        this.userMapper = userMapper;
        this.friendMapper = friendMapper;
        this.userInfoMapper = userInfoMapper;
        this.likeOrNotMapper = likeOrNotMapper;
        this.userDetailsMapper = userDetailsMapper;
    }

    public UserResponse add(User user) {
        if (userMapper.query(user) != null) {
            //username已经存在
            System.out.println(user.getUsername());
            return new UserResponse(false, UserResponseFailedReason.USERNAME_ALREADY_EXIST, user);
        }
        userMapper.add(user);
//        friendMapper.add(new FriendshipData(
//                user.getUsername(),
//                null,
//                null,
//                0,
//                0
//
//        ));
        Properties properties=new Properties();
        try{
            properties.load(Resources.getResourceAsStream("resourceLocations.properties"));
        }catch (Exception e){
            log.info(e.getMessage());
        }
        String default_avatar_url=properties.getProperty("default_avatar_url");

        userInfoMapper.add(new UserInfoData(
                user.getUsername(),
                Const.DEFAULT_NICKNAME,
                Const.DEFAULT_PERSONAL_SIGN,
                //NginxHelper.getAbsoluteUrl(Const.DEFAULT_AVATAR_URL)
                default_avatar_url,
                Utility.toJson(Const.DEFAULT_POSTS),
                Utility.toJson(Const.DEFAULT_COLLECTIONS))
        );

        //System.out.println("add1 run");
        likeOrNotMapper.add(user.getUsername());
        //System.out.println("add1finished");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        userDetailsMapper.addUserDetails(new UserDetails(user.getUsername(),"","",dtf.format(now),""));
        return new UserResponse(true, null, user);
    }

    public UserResponse query(User user) {

        var userRes = userMapper.query(user);

        if (userRes== null) {
            return new UserResponse(false, UserResponseFailedReason.USERNAME_DOES_NOT_EXIST, user);
        }
        else {
            return new UserResponse(true, null, userRes);
        }
    }
}
