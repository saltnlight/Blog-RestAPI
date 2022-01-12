package com.flora.week9taskblog.Service.ServiceImpl;

import com.flora.week9taskblog.Payload.Request.CommentRequest;
import com.flora.week9taskblog.Payload.Request.PostRequest;
import com.flora.week9taskblog.Payload.Response.CommentResponse;
import com.flora.week9taskblog.Payload.Response.StatusResponse;
import com.flora.week9taskblog.Repository.CommentRepository;
import com.flora.week9taskblog.Repository.PostRepository;
import com.flora.week9taskblog.Service.CommentService;
import com.flora.week9taskblog.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ResponseEntity createComment(CommentRequest commentRequest) {
        commentRepository.saveComment(commentRequest);
        return new ResponseEntity(new StatusResponse("Successfully created comment"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity searchComment(String title) {
        String queryString = title.trim().replaceAll(" ", " & ");
        List<Comment> result = commentRepository.searchComment(queryString);
        CommentResponse response = new CommentResponse("Successful", result);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getCommentsByPostId(Long userId) {
        return new ResponseEntity(commentRepository.getPostComments(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity likeUnlikeComment(Long userId, Long commentId) {
        boolean result = commentRepository.likeUnlike(userId, commentId);
        if (result) return new ResponseEntity(new StatusResponse("Liked"), HttpStatus.OK);
        return new ResponseEntity(new StatusResponse("Unliked"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity editComment(Long userId, Long postId, PostRequest postRequest) {
        return null;
    }

    @Override
    public ResponseEntity deleteComment(Long userId, Long postId) {
        return null;
    }
}
