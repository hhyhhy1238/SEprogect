package com.mxy.bbs_server.controller;

import com.mxy.bbs_server.response.postlist.PostListResponse;
import com.mxy.bbs_server.service.PostListService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postList")
@CrossOrigin("*")
public class PostListController {

    private final PostListService postListService;

    public PostListController(PostListService postListService) {
        this.postListService = postListService;
    }

    @GetMapping("/get")
    public PostListResponse getPosts() {
        return postListService.getPosts();
    }
}
