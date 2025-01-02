package com.mxy.bbs_server.controller;

import com.mxy.bbs_server.entity.NumFavor;
import com.mxy.bbs_server.entity.PostRequest;
import com.mxy.bbs_server.entity.PostType;
import com.mxy.bbs_server.response.NumFavorResponse.NumFavorResponse;
import com.mxy.bbs_server.response.post.PostResponse;
import com.mxy.bbs_server.response.post.PostResponseFailedReason;
import com.mxy.bbs_server.service.PostService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/post")
@CrossOrigin("*")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/add")
    public PostResponse add(@RequestBody PostRequest postRequest) throws IOException {
//        System.out.println(postRequest);
        return postService.add(postRequest);
    }

    @PostMapping("/addWithoutImage")
    public PostResponse add(String id, String planId, String owner, String title, String content) throws IOException {
//        System.out.println("----Debug: owner:"+owner+"  -------");
        return postService.add(new PostRequest(id, planId, owner, title, content, new MultipartFile[0]));
    }

    @PostMapping("/query")
    public PostResponse query(@RequestBody PostRequest postRequest) {
        return postService.query(postRequest);
    }

    @GetMapping("/query/{postId}")
    public PostResponse query(@PathVariable("postId") String postId) {
        return postService.query(new PostRequest(postId, "","", "", "", null));
    }

    @GetMapping("/queryFavorNum/{postId}")
    public NumFavorResponse queryFavorNum(@PathVariable("postId") String postId){
        return postService.queryFavorNum(postId);
    }
}
