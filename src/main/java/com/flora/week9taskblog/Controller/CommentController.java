package com.flora.week9taskblog.Controller;

import com.flora.week9taskblog.Payload.Request.CommentRequest;
import com.flora.week9taskblog.Payload.Response.CommentResponse;
import com.flora.week9taskblog.Payload.Response.StatusResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @GetMapping("/search")
    public CommentResponse searchComment(@RequestParam(value = "comment") String comment){
        return new CommentResponse();
    }

    @GetMapping("/{postId}")
    public CommentResponse getAllCommentsOnPost(){
        return new CommentResponse();
    }

    @PostMapping
    public CommentResponse createComment(@RequestBody CommentRequest commentRequest){
        return new CommentResponse();
    }

    @PutMapping("/{commentId}")
    public CommentResponse editComment(@RequestBody CommentRequest commentRequest){
        return new CommentResponse();
    }

    @PatchMapping("/{postId}/{commentId}/likes/{userId}/")
    public CommentResponse likeUnlikePost(){
        return new CommentResponse();
    }

    @DeleteMapping("/{postId}/{commentId}/{userId}")
    public StatusResponse deletePost(){
        return new StatusResponse();
    }

}
