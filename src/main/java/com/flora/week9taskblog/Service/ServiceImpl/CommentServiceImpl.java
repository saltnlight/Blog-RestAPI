package com.flora.week9taskblog.Service.ServiceImpl;

import com.flora.week9taskblog.Payload.Request.CommentRequest;
import com.flora.week9taskblog.Payload.Request.PostRequest;
import com.flora.week9taskblog.Payload.Response.CommentResponse;
import com.flora.week9taskblog.Payload.Response.StatusResponse;
import com.flora.week9taskblog.Repository.CommentRepository;
import com.flora.week9taskblog.Service.CommentService;
import com.flora.week9taskblog.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PageRequest pageable = PageRequest.of(0, 5);

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public ResponseEntity createComment(CommentRequest commentRequest) {
        commentRepository.saveComment(commentRequest);
        return new ResponseEntity(new StatusResponse("Successfully created comment"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity searchComment(String title) {
        String queryString = title.trim().replaceAll(" ", "&");
        Page<Comment> result = commentRepository.searchComment(queryString, pageable);
        if (result.getContent().isEmpty()) return new ResponseEntity(
                    new CommentResponse("Can't find similar comments", result), HttpStatus.NOT_FOUND);
        return new ResponseEntity(new CommentResponse("Successful", result), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getCommentsByPostId(Long userId) {
        return new ResponseEntity(commentRepository.getPostComments(userId, pageable), HttpStatus.OK);
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
