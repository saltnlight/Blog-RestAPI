package com.flora.week9taskblog.controller;

import com.flora.week9taskblog.Payload.Request.PostRequest;
import com.flora.week9taskblog.Payload.Response.PostResponse;
import com.flora.week9taskblog.Payload.Response.StatusResponse;
import com.flora.week9taskblog.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping("/search")
    public ResponseEntity searchPostTitle(@RequestParam(value = "blog_title") String title){
        return postService.searchPostByTitle(title);
    }

    @GetMapping
    public ResponseEntity getAllUsersPosts(Pageable pageable){
        return postService.getAllPosts();
    }

    @GetMapping("/{userId}")
    public ResponseEntity getOneUserPosts(@PathVariable Long userId){
        return postService.getPostsByUserId(userId);
    }

    @PostMapping("/{userId}")
    public ResponseEntity createPost(@PathVariable Long userId,
                                     @RequestBody PostRequest postRequest){

        return postService.createPost(userId, postRequest);
    }

    @PutMapping("/{postId}/{userId}")
    public PostResponse editPost(@RequestBody PostRequest postRequest){return new PostResponse();}

    @PatchMapping("/{postId}/{userId}")
    public ResponseEntity favoritePost(@PathVariable Long userId, @PathVariable Long postId){
        return postService.addRemovePostToFavorite(userId, postId);
    }

    @PatchMapping("/{postId}/likes/{userId}")
    public ResponseEntity likeUnlikePost(@PathVariable Long userId, @PathVariable Long postId){
        return postService.likeUnlikePost(userId, postId);
    }

    @DeleteMapping("/{postId}/{userId}")
    public StatusResponse deletePost(){
        return new StatusResponse();
    }

}
