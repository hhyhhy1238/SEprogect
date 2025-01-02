package com.mxy.bbs_server.service;

import com.mxy.bbs_server.entity.*;
import com.mxy.bbs_server.mapper.FriendMapper;
import com.mxy.bbs_server.mapper.LikeOrNotMapper;
import com.mxy.bbs_server.mapper.UserInfoMapper;
import com.mxy.bbs_server.mapper.UserMapper;
import com.mxy.bbs_server.response.user.UserResponse;
import com.mxy.bbs_server.response.user.UserResponseFailedReason;
import com.mxy.bbs_server.response.userinfo.FavorOrNotResponse;
import com.mxy.bbs_server.response.userinfo.LikeOrNotResponse;
import com.mxy.bbs_server.response.userinfo.UserInfoResponse;
import com.mxy.bbs_server.response.userinfo.UserInfoResponseFailedReason;
import com.mxy.bbs_server.utility.Const;
import com.mxy.bbs_server.utility.NginxHelper;
import com.mxy.bbs_server.utility.Utility;
;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoService {
    private final UserInfoMapper userInfoMapper;
    private final UserMapper userMapper;

    private final LikeOrNotMapper likeOrNotMapper;
    private final FriendMapper friendMapper;

    public UserInfoService(UserInfoMapper userInfoMapper, UserMapper userMapper, LikeOrNotMapper likeOrNotMapper, FriendMapper friendMapper) {
        this.userInfoMapper = userInfoMapper;
        this.userMapper = userMapper;
        this.likeOrNotMapper = likeOrNotMapper;
        this.friendMapper = friendMapper;
    }

    public UserInfoResponse update(UserInfoRequest userInfoRequest) throws IOException {
        final var userInfoToQuery = new UserInfoData(userInfoRequest.getUsername(), null, null, null, null, null);
        if (userInfoMapper.query(userInfoToQuery) == null) {
            return new UserInfoResponse(false, UserInfoResponseFailedReason.USERNAME_DOES_NOT_EXIST, null);
        }
//        final var avatar = Utility.saveAvatar(userInfoRequest.getAvatar(), userInfoRequest.getUsername());
        String defaultAvatarUrl = "http://354174c9.r25.cpolar.top/ImageResource/avatars/default.png";
        final var previousUserInfo = userInfoMapper.query(userInfoToQuery);
        userInfoMapper.update(new UserInfoData(userInfoRequest.getUsername(), userInfoRequest.getNickname(), userInfoRequest.getPersonalSign(), defaultAvatarUrl, previousUserInfo.getMyPosts(), previousUserInfo.getMyCollections()));
        final var userInfoRes = userInfoMapper.query(userInfoToQuery);

        return new UserInfoResponse(true, null, new UserInfo(userInfoRes.getUsername(),
                userInfoRes.getNickname(),
                userInfoRes.getPersonalSign(),
//                userInfoRes.getAvatarUrl(),
                defaultAvatarUrl,
                Utility.fromJson(userInfoRes.getMyPosts(), ArrayList.class),
                Utility.fromJson(userInfoRes.getMyCollections(), ArrayList.class)
        ));
    }

    public UserInfoResponse query(UserInfoRequest userInfoRequest) {
        //var userInfoRes = userInfoMapper.query(new UserInfoData(userInfoRequest.getUsername(), null, null, null, null, null));
        var userInfoRes = userInfoMapper.queryByUsername(userInfoRequest.getUsername());
        if (userInfoRes == null) {
            return new UserInfoResponse(false, UserInfoResponseFailedReason.USERNAME_DOES_NOT_EXIST, null);
        }
        return new UserInfoResponse(true, null,
                new UserInfo(userInfoRes.getUsername(),
                        userInfoRes.getNickname(),
                        userInfoRes.getPersonalSign(),
                        userInfoRes.getAvatarUrl(),
                        Utility.fromJson(userInfoRes.getMyPosts(), ArrayList.class),
                        Utility.fromJson(userInfoRes.getMyCollections(), ArrayList.class)
                )
        );
    }

    public UserInfoResponse login(User user) {
        if (userMapper.query(user) == null) {
            //username不存在
            return new UserInfoResponse(false, UserInfoResponseFailedReason.USERNAME_DOES_NOT_EXIST, null);
        }
        User check = userMapper.query(user);
        String p4wd = user.getPassword();
//        System.out.println(check.toString());
//        System.out.println(p4wd);

        if (!(p4wd.equals(check.getPassword()))) {
            //System.out.println("密码错误!");
            //密码不符
            return new UserInfoResponse(false, UserInfoResponseFailedReason.WRONG_PASSWORD, null);
        } else {
            return new UserInfoResponse(true, null, userInfoMapper.queryByUsername(check.getUsername()).convertToUserInfo());
        }

    }

    public LikeOrNotResponse postQuery(LikeOrNotRequest lOn) {
        boolean iLike = false;
        boolean iConcerned = false;
        var userInfoRes = userInfoMapper.query(new UserInfoData(lOn.getUsername(), null, null, null, null, null));
        var LikeOrNotRes = likeOrNotMapper.query(lOn.getUsername());
        final List<String> myCollections = Utility.fromJson(userInfoRes.getMyCollections(), List.class);
        if (myCollections == null) {

        } else {
            for (final String collection : myCollections) {
                if (collection == null) {

                } else {
                    if (collection.equals(lOn.getPostId())) {
                        iConcerned = true;
                    }
                }
            }
        }

        final List<String> myLikes = Utility.fromJson(LikeOrNotRes.getPostId(), List.class);
        if (myLikes == null) {

        } else {
            for (final String Like : myLikes) {
                if (Like == null) {

                } else {
                    if (Like.equals(lOn.getPostId())) {
                        iLike = true;
                    }
                }
            }
        }

        return new LikeOrNotResponse(iLike, iConcerned);
    }

    public FavorOrNotResponse favorOrNotResponseQuery(FavorOrNotRequest foN) {
        final var friendData = friendMapper.query(new FriendshipData(foN.getUsername(),
                null, null, null, null));
        List<String> concernedList = Utility.fromJson(friendData.getMyConcerned(), ArrayList.class);
        for (var concernedId : concernedList) {
            if (concernedId.equals(foN.getTargetusername())) {
                return new FavorOrNotResponse(true);
            }
        }
        return new FavorOrNotResponse(false);
    }
}
