package com.flora.week9taskblog.controller;

import com.flora.week9taskblog.Payload.Request.LoginRequest;
import com.flora.week9taskblog.Payload.Request.RegisterRequest;
import com.flora.week9taskblog.Payload.Response.AuthenticationResponse;
import com.flora.week9taskblog.Security.JwtUtil;
import com.flora.week9taskblog.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {

    private final AuthService authService;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("authenticate")
    public ResponseEntity createAuthToken(@RequestBody LoginRequest loginRequest){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        // get details of user maybe my calling a service that will call the repo
        // instead of calling UserDetailService
        final String jwt = jwtUtil.generateToken(loginRequest.getUsername());
        System.out.println("Our jwt: "+jwt);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
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
