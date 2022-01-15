package com.flora.week9taskblog.controller;

import com.flora.week9taskblog.Payload.Request.LoginRequest;
import com.flora.week9taskblog.Payload.Request.RegisterRequest;
import com.flora.week9taskblog.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /** requestBody should contain
     * { "username", "password" }
     * */
    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        return authService.loginUser(loginRequest);
    }

    /** requestBody should contain
     * { "username", "firstName", "lastName", "email", "password" }
     * */
    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest){
        return authService.registerUser(registerRequest);
    }
}
