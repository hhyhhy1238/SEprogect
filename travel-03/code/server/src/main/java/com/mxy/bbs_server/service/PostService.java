package com.mxy.bbs_server.service;

import com.mxy.bbs_server.entity.*;
import com.mxy.bbs_server.mapper.PostMapper;
import com.mxy.bbs_server.mapper.TravelPlansMapper;
import com.mxy.bbs_server.mapper.UserInfoMapper;
import com.mxy.bbs_server.mapper.numFavorMapper;
import com.mxy.bbs_server.response.NumFavorResponse.NumFavorResponse;
import com.mxy.bbs_server.response.post.PostResponse;
import com.mxy.bbs_server.response.post.PostResponseFailedReason;
import com.mxy.bbs_server.utility.Const;
import com.mxy.bbs_server.utility.Utility;
import jdk.jshell.execution.Util;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PostService {
    private final PostMapper postMapper;

    private final TravelPlansMapper travelPlansMapper;
    private final UserInfoMapper userInfoMapper;
    private final numFavorMapper numfavorMapper;
    public PostService(PostMapper postMapper, TravelPlansMapper travelPlansMapper,UserInfoMapper userInfoMapper, numFavorMapper numfavorMapper) {
        this.postMapper = postMapper;
        this.travelPlansMapper = travelPlansMapper;
        this.userInfoMapper = userInfoMapper;
        this.numfavorMapper = numfavorMapper;
    }

    public PostResponse add(PostRequest postRequest) throws IOException {
        while (postMapper.query(new PostData(postRequest.getId(), null,null, null, null, null, null, null, null)) != null) {
            //return new PostResponse(false, PostResponseFailedReason.POST_ALREADY_EXISTS, null);
            Random random=new Random(System.currentTimeMillis());
            postRequest.setId(String.valueOf(random.nextInt()));
        }
        final var images = Utility.savePostImages(postRequest.getImages(), postRequest.getId());

        final var locations = travelPlansMapper.query(postRequest.getPlanId());
//        System.out.println("PostServer:: locations is "+ locations);

        postMapper.add(new PostData(
                postRequest.getId(),
                locations,
                Utility.getDate(Const.DATE_FORMAT),
                postRequest.getOwner(),
                postRequest.getTitle(),
                postRequest.getContent(),
                Utility.toJson(images),
                0,
                Utility.toJson(new ArrayList<String>())
                )
        );
//        System.out.println("-----Debug:postRequest.owner is:"+postRequest.getOwner()+" ------");
        final var previousUserInfo = userInfoMapper.query(new UserInfoData(postRequest.getOwner(), null, null, null, null, null));
        //在"我的帖子"中添加当前帖子
        final List<String> myPosts = Utility.fromJson(previousUserInfo.getMyPosts(), List.class);
        myPosts.add(postRequest.getId());
        previousUserInfo.setMyPosts(Utility.toJson(myPosts));
        userInfoMapper.update(previousUserInfo);

        numfavorMapper.add(postRequest.getId());

        final var postRes = postMapper.query(new PostData(postRequest.getId(), null,null, null, null, null, null, null, null));


        return new PostResponse(true, null,
                new Post(
                        postRes.getId(),
                        Utility.fromJson(postRes.getLocations(),ArrayList.class),
                        postRes.getDate(),
                        postRes.getOwner(),
                        postRes.getTitle(),
                        postRes.getContent(),
                        Utility.fromJson(postRes.getImages(), ArrayList.class),
                        postRes.getLikeNum(),
                        Utility.fromJson(postRes.getReviews(), ArrayList.class)
        ));
    }

    public PostResponse query(PostRequest postRequest) {
        var postRes = postMapper.query(new PostData(postRequest.getId(), null,null, null, null, null, null, null, null));
        if (postRes == null) {
            return new PostResponse(false, PostResponseFailedReason.POST_DOES_NOT_EXIST, null);
        }
        return new PostResponse(true, null,
                new Post(postRes.getId(),
                        Utility.fromJson(postRes.getLocations(),ArrayList.class),
                        postRes.getDate(),
                        postRes.getOwner(),
                        postRes.getTitle(),
                        postRes.getContent(),
                        Utility.fromJson(postRes.getImages(), ArrayList.class),
                        postRes.getLikeNum(), Utility.fromJson(postRes.getReviews(),
                        ArrayList.class)
                )
        );
    }

    public  NumFavorResponse queryFavorNum(String postId) {
        if(numfavorMapper.query(postId)==null){return new NumFavorResponse(false,null);}
        else{
            return new NumFavorResponse(true,numfavorMapper.query(postId).getNumFavor());
        }

    }
}
