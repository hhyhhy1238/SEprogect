package com.mxy.bbs_server.controller;

import com.mxy.bbs_server.entity.User;
import com.mxy.bbs_server.entity.UserDetails;
import com.mxy.bbs_server.entity.UserDetailsRequest;
import com.mxy.bbs_server.response.user.UserResponse;
import com.mxy.bbs_server.response.userDetails.UserDetailsResponse;
import com.mxy.bbs_server.service.UserDetailsService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@CrossOrigin("*")
@RestController
@RequestMapping("/UserDetails")
public class UserDetailsController {
    private final UserDetailsService userDetailsService;

    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/update")
    public UserDetailsResponse update(@Valid UserDetailsRequest request) throws IOException {
        return userDetailsService.update(request);
    }

    @GetMapping("/query/{username}")
    public UserDetails updateUserDetailsByUsername(@PathVariable("username")  String username){
        return userDetailsService.updateUserDetailsByUsername(username);
    }


}
