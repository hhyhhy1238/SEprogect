package com.mxy.bbs_server.controller;

import com.mxy.bbs_server.entity.User;
import com.mxy.bbs_server.response.user.UserResponse;
import com.mxy.bbs_server.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public UserResponse addUser(@RequestBody User user) {
        return userService.add(user);
    }

    @PostMapping("/query")
    public UserResponse queryUser(@RequestBody User user) {
        return userService.query(user);
    }

    @GetMapping("query/{username}")
    public UserResponse queryUser(@PathVariable("username") String username) {
        return userService.query(new User(username, ""));
    }
}
