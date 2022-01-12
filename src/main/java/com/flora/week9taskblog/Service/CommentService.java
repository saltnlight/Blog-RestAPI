package com.flora.week9taskblog.Service;

import com.flora.week9taskblog.Payload.Request.CommentRequest;
import com.flora.week9taskblog.Payload.Request.PostRequest;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    /**
     * create a post
     * */
    ResponseEntity createComment(CommentRequest commentRequest);

    /**
     * search for a blogpost by its title
     * */
    ResponseEntity searchComment(String title);

    /**
     * get all the posts of a specified user
     * */
    ResponseEntity getCommentsByPostId(Long userId);

    /**
     * like or unlike a post from favorite
     * */
    ResponseEntity likeUnlikeComment(Long userId, Long postId);

    ResponseEntity editComment(Long userId, Long postId, PostRequest postRequest);

    ResponseEntity deleteComment(Long userId, Long postId);

}
