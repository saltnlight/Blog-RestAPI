package com.flora.week9taskblog.controller;

import com.flora.week9taskblog.Payload.Response.UserResponse;
import com.flora.week9taskblog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/connections/{conUsername}/{id}")
    public ResponseEntity addConnection(@PathVariable(value = "conUsername") String conUsername,
                                        @PathVariable(value = "id") Long id){
        return userService.addConnection(id, conUsername);
    }

    @GetMapping("/{id}/connections")
    public ResponseEntity viewConnections(@PathVariable(value = "id") Long userId){
        return userService.viewAllConnections(userId);
    }

    @GetMapping("/{id}/connections/{conUsername}/posts")
    public ResponseEntity viewOneConnectionPosts(@PathVariable(value = "id") Long id,
                                                 @PathVariable(value = "conUsername") String conUsername){
        return userService.viewAllPostsFromOneConnection(id, conUsername);
    }

    @GetMapping("/{id}/connections/posts")
    public ResponseEntity viewAllConnectionsPosts(@PathVariable(value = "id") Long id){
        return userService.viewAllPostsFromAllConnections(id);
    }

    @PatchMapping("/{username}")
    public UserResponse activateDeactivateAccount(){
        return new UserResponse();
    }
}
