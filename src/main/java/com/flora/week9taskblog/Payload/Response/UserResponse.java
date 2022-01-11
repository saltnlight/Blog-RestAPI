package com.flora.week9taskblog.Payload.Response;

import com.flora.week9taskblog.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String status;
    private List<User> users;
}
