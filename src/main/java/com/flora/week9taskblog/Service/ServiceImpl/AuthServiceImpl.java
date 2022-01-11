package com.flora.week9taskblog.Service.ServiceImpl;

import com.flora.week9taskblog.Model.User;
import com.flora.week9taskblog.Payload.Request.LoginRequest;
import com.flora.week9taskblog.Payload.Request.RegisterRequest;
import com.flora.week9taskblog.Payload.Response.StatusResponse;
import com.flora.week9taskblog.Payload.Response.UserResponse;
import com.flora.week9taskblog.Repository.AuthRepository;
import com.flora.week9taskblog.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl  implements AuthService {
    private AuthRepository authRepository;

    @Autowired
    public AuthServiceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public ResponseEntity registerUser(RegisterRequest registerRequest) {
        //validate input before sending to repo
        authRepository.registerUser(registerRequest);
        return new ResponseEntity(new StatusResponse("Successfully Registered"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity loginUser(LoginRequest loginRequest) {
        //validate input before sending to repo
        User user = authRepository.loginUser(loginRequest);
        UserResponse response = new UserResponse("Successfully Login", List.of(user));
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
