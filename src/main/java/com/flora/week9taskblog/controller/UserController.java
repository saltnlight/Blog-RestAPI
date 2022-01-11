package com.flora.week9taskblog.controller;

import com.flora.week9taskblog.Payload.Request.ConnectionRequest;
import com.flora.week9taskblog.Payload.Response.StatusResponse;
import com.flora.week9taskblog.Payload.Response.UserResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/connections/{conUsername}")
    public StatusResponse addConnection(@RequestBody ConnectionRequest connectionRequest){
        return new StatusResponse();
    }

    @GetMapping("/{username}/connections/{conUsername}/posts")
    public UserResponse viewOneConnectionPosts(){
        //  call this method - getOneUserPosts
        return new UserResponse();
    }

    @GetMapping("/{username}/connections/posts")
    public UserResponse viewAllConnectionsPosts(){
        return new UserResponse();
    }

    @PatchMapping("/{username}")
    public UserResponse activateDeactivateAccount(){
        return new UserResponse();
    }
}
