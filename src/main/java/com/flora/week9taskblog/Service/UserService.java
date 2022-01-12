package com.flora.week9taskblog.Service;

import org.springframework.http.ResponseEntity;

public interface UserService {
    /**
     * add another user as a connection
     * */
    ResponseEntity addConnection(Long userId, String conUsername);

    /**
     * get list of all your connections
     **/
    ResponseEntity viewAllConnections(Long userId);

    /**
     * view all posts from a single connection
     * */
    ResponseEntity viewAllPostsFromOneConnection(Long userId, String conUsername);

    /**
     * view all posts from all connections
     *
     * @return*/
    ResponseEntity viewAllPostsFromAllConnections(Long userId);

}
