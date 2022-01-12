package com.flora.week9taskblog.Service.ServiceImpl;

import com.flora.week9taskblog.Payload.Response.PostResponse;
import com.flora.week9taskblog.Payload.Response.StatusResponse;
import com.flora.week9taskblog.Payload.Response.UserResponse;
import com.flora.week9taskblog.Repository.PostRepository;
import com.flora.week9taskblog.Repository.UserRepository;
import com.flora.week9taskblog.Service.UserService;
import com.flora.week9taskblog.model.Post;
import com.flora.week9taskblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PostRepository postRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public ResponseEntity addConnection(Long userId, String conUsername) {
        var response = userRepository.addConnect(userId, conUsername);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity viewAllConnections(Long userId) {
        List<User> users = userRepository.getAllConnections(userId);
        if (users.isEmpty()) return new ResponseEntity(
                new UserResponse("You have no connections", users), HttpStatus.NO_CONTENT
        );
        return new ResponseEntity(new UserResponse("Successful", users), HttpStatus.OK);
    }

    @Override
    public ResponseEntity viewAllPostsFromOneConnection(Long userId, String conUsername) {
        PostResponse response = userRepository.getOneConnectionPosts(userId, conUsername);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity viewAllPostsFromAllConnections(Long userId) {
        List<List<Post>> connPosts = userRepository.getAllConnectionsPosts(userId);
        if (connPosts.isEmpty()) return new ResponseEntity("No Posts Found", HttpStatus.NO_CONTENT);
        return new ResponseEntity(connPosts, HttpStatus.OK);
    }
}
