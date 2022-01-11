package com.flora.week9taskblog.Service;

import com.flora.week9taskblog.Payload.Request.LoginRequest;
import com.flora.week9taskblog.Payload.Request.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity registerUser(RegisterRequest registerRequest);

    ResponseEntity loginUser(LoginRequest loginRequest);
}
