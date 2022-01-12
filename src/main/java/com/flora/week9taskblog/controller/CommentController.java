package com.flora.week9taskblog.controller;

import com.flora.week9taskblog.Payload.Request.CommentRequest;
import com.flora.week9taskblog.Payload.Response.CommentResponse;
import com.flora.week9taskblog.Payload.Response.StatusResponse;
import com.flora.week9taskblog.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/search")
    public ResponseEntity searchComment(@RequestParam(value = "comment") String commentBody){
        return commentService.searchComment(commentBody);
    }

    @GetMapping("/{postId}")
    public ResponseEntity getAllCommentsOnPost(@PathVariable Long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @PostMapping
    public ResponseEntity createComment(@RequestBody CommentRequest commentRequest){
        return commentService.createComment(commentRequest);
    }

    @PatchMapping("/{commentId}/likes/{userId}")
    public ResponseEntity likeUnlikePost(@PathVariable(value = "userId") Long userId,
                                          @PathVariable(value = "commentId") Long commentId){
        return commentService.likeUnlikeComment(userId, commentId);
    }

    @PutMapping("/{commentId}")
    public CommentResponse editComment(@RequestBody CommentRequest commentRequest){
        return new CommentResponse();
    }

    @DeleteMapping("/{postId}/{commentId}/{userId}")
    public StatusResponse deletePost(){
        return new StatusResponse();
    }

}
