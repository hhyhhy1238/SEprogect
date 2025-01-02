package com.mxy.bbs_server.service;

import com.mxy.bbs_server.mapper.PostMapper;
import com.mxy.bbs_server.response.postlist.PostListResponse;
import com.mxy.bbs_server.utility.Const;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class PostListService {

    private final PostMapper postMapper;

    public PostListService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public PostListResponse getPosts() {
//        System.out.println("got postList/get request");
        final var postIds = postMapper.selectAll();
        if( postIds.isEmpty() ){
//            System.out.println("PostList::getPosts false");
            return new PostListResponse(false,null);
        }

        Collections.shuffle(postIds);
        if (postIds.size() < Const.POST_NUM) {
            return new PostListResponse(true, postIds);
        }
        return new PostListResponse(true, postIds.subList(0, Const.POST_NUM));
    }
}

