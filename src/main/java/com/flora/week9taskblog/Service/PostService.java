package com.flora.week9taskblog.Service;

import com.flora.week9taskblog.Payload.Request.PostRequest;
import org.springframework.http.ResponseEntity;

public interface PostService {

    /**
     * create a post
     * */
    ResponseEntity createPost(Long userId, PostRequest postRequest);

    /**
     * search for a blogpost by its title
     * */
    ResponseEntity searchPostByTitle(String title);

    /**
     * get all the posts of a specified user
     * */
    ResponseEntity getPostsByUserId(Long userId);

    /**
     * get all the posts of every user
     * */
    ResponseEntity getAllPosts();

    /**
     * like or unlike a post from favorite
     * */
    ResponseEntity likeUnlikePost(Long userId, Long postId);

    /**
     * add or remove a post from favorite
     * */
    ResponseEntity addRemovePostToFavorite(Long userId, Long postId);

    ResponseEntity editPost(Long userId, Long postId, PostRequest postRequest);

    ResponseEntity deletePost(Long userId, Long postId);

//    /**
//     * get all the posts of all a user conncetions
//     * */
//    ResponseEntity getPostsOfConnection(Long userId);

}
