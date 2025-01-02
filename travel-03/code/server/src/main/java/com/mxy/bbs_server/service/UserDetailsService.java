package com.mxy.bbs_server.service;

import com.mxy.bbs_server.entity.UserDetails;
import com.mxy.bbs_server.entity.UserDetailsRequest;
import com.mxy.bbs_server.entity.UserInfoData;
import com.mxy.bbs_server.mapper.UserDetailsMapper;
import com.mxy.bbs_server.mapper.UserInfoMapper;
import com.mxy.bbs_server.response.userDetails.UserDetailsResponse;
import com.mxy.bbs_server.utility.Utility;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserDetailsService {
    private final UserDetailsMapper userDetailsMapper;
    private final UserInfoMapper userInfoMapper;
    public UserDetailsService(UserDetailsMapper userDetailsMapper, UserInfoMapper userInfoMapper) {
        this.userDetailsMapper = userDetailsMapper;
        this.userInfoMapper = userInfoMapper;
    }

    public UserDetailsResponse update(UserDetailsRequest userDetailsRequest) throws IOException {
        //System.out.println(userDetailsRequest.getUsername());
        UserDetails details=userDetailsMapper.getUserDetailByUsername(userDetailsRequest.getUsername());
        //System.out.println(details);

        final var avatar = Utility.savePostImages(userDetailsRequest.getAvatar(), userDetailsRequest.getUsername());

        var OlduserInfo=userInfoMapper.queryByUsername(userDetailsRequest.getUsername());
        userDetailsMapper.updateUserDetailsByUsername(new UserDetails(userDetailsRequest.getUsername(), userDetailsRequest.getGender(),userDetailsRequest.getBirthday(), details.getRegisterTime(),userDetailsRequest.getSignature()));
        userInfoMapper.update(new UserInfoData(userDetailsRequest.getUsername(),userDetailsRequest.getNickname(),
               null, Utility.toJson(avatar),OlduserInfo.getMyPosts(), OlduserInfo.getMyCollections()));
        return new UserDetailsResponse(true);
    }


    public UserDetails updateUserDetailsByUsername(String username) {
        UserDetails userDetails=userDetailsMapper.getUserDetailByUsername(username);
        return userDetails;
    }
}
