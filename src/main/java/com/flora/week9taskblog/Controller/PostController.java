package com.flora.week9taskblog.Controller;

import com.flora.week9taskblog.Payload.Request.PostRequest;
import com.flora.week9taskblog.Payload.Response.PostResponse;
import com.flora.week9taskblog.Payload.Response.StatusResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/posts")
public class PostController {

    @GetMapping("/search")
    public PostResponse searchPostTitle(@RequestParam(value = "blog_title") String title){
        return new PostResponse();
    }

    @GetMapping
    public PostResponse getAllUsersPosts(Pageable pageable){
        return new PostResponse();
    }

    @GetMapping("/{userId}")
    public PostResponse getOneUserPosts(){
        return new PostResponse();
    }

    @PostMapping("/{userId}")
    public PostResponse createPost(@RequestBody PostRequest postRequest){
        return new PostResponse();
    }

    @PutMapping("/{postId}/{userId}")
    public PostResponse editPost(@RequestBody PostRequest postRequest){
        return new PostResponse();
    }

    @PatchMapping("/{postId}/{userId}")
    public PostResponse favoritePost(){
        return new PostResponse();
    }

    @PatchMapping("/{postId}/likes/{userId}/")
    public PostResponse likeUnlikePost(){
        return new PostResponse();
    }

//    is it better to send a body with a delete request
    @DeleteMapping("/{postId}/{userId}")
    public StatusResponse deletePost(){
        return new StatusResponse();
    }

}
