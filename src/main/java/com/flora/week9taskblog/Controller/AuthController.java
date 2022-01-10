package com.flora.week9taskblog.Controller;

import com.flora.week9taskblog.Model.User;
import com.flora.week9taskblog.Payload.Request.LoginRequest;
import com.flora.week9taskblog.Payload.Request.RegisterRequest;
import com.flora.week9taskblog.Payload.Response.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/")
public class AuthController {

    @PostMapping("login")
    public UserResponse login(@RequestBody LoginRequest loginRequest){
        return new UserResponse("ok", new ArrayList<User>(Arrays.asList(new User())));
    }

    @PostMapping("register")
    public UserResponse register(@RequestBody RegisterRequest registerRequest){
        return new UserResponse(new ArrayList<User>(Arrays.asList(new User())));
    }
}
