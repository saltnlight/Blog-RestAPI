package com.flora.week9taskblog.Service.ServiceImpl;

import com.flora.week9taskblog.Payload.Response.PostResponse;
import com.flora.week9taskblog.Payload.Response.UserResponse;
import com.flora.week9taskblog.Repository.UserRepository;
import com.flora.week9taskblog.Service.UserService;
import com.flora.week9taskblog.model.Post;
import com.flora.week9taskblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PageRequest pageable = PageRequest.of(0, 5);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity addConnection(Long userId, String conUsername) {
        var response = userRepository.addConnect(userId, conUsername);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity viewAllConnections(Long userId) {
        Page<User> users = userRepository.getAllConnections(userId, pageable);
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
        List<Page<Post>> connPosts = userRepository.getAllConnectionsPosts(userId);
        if (connPosts.isEmpty()) return new ResponseEntity("No Posts Found", HttpStatus.NO_CONTENT);
        return new ResponseEntity(connPosts, HttpStatus.OK);
    }
}
