package com.devashree.ticketing.controller;

import com.devashree.ticketing.dto.UserRequest;
import com.devashree.ticketing.entity.User;
import com.devashree.ticketing.service.UserService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping
    public User createUser(@RequestBody UserRequest request){
        return userService.createUser(request);
    }
}
