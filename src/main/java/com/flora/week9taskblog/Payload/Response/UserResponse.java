package com.flora.week9taskblog.Payload.Response;

import com.flora.week9taskblog.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String status;
    private Page<User> users;
    private User user;

    public UserResponse(String status, User user) {
        this.status = status;
        this.user = user;
    }

    public UserResponse(String status, Page<User> users) {
        this.status = status;
        this.users = users;
    }
}
