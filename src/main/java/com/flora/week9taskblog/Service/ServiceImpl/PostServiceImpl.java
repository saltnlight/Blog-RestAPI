package com.flora.week9taskblog.Service.ServiceImpl;

import com.flora.week9taskblog.Payload.Request.PostRequest;
import com.flora.week9taskblog.Payload.Response.PostResponse;
import com.flora.week9taskblog.Payload.Response.StatusResponse;
import com.flora.week9taskblog.Repository.PostRepository;
import com.flora.week9taskblog.Service.PostService;
import com.flora.week9taskblog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PageRequest pageable = PageRequest.of(0, 5);

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public ResponseEntity createPost(Long userId, PostRequest postRequest) {
        postRepository.savePost(userId, postRequest);
        StatusResponse response = new StatusResponse("Successfully Posted");
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity searchPostByTitle(String title) {
        String queryString = title.trim().replaceAll(" ", " & ");
        Page<Post> result = postRepository.searchPosts(queryString, pageable);
        PostResponse response = new PostResponse("Successful", result);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getPostsByUserId(Long userId) {
        PostResponse response = new PostResponse("Successful", postRepository.getUsersPosts(userId, pageable));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getAllPosts() {
        PostResponse response = new PostResponse("Successful", postRepository.getAllPosts(pageable));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity likeUnlikePost(Long userId, Long postId) {
        boolean result = postRepository.likeUnlike(userId, postId);
        if (result) return new ResponseEntity(new StatusResponse("Liked"), HttpStatus.OK);
        return new ResponseEntity(new StatusResponse("Unliked"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity addRemovePostToFavorite(Long userId, Long postId) {
        boolean result = postRepository.favorite(userId, postId);
        if (result) return new ResponseEntity(new StatusResponse("Added to favourite"), HttpStatus.OK);
        return new ResponseEntity(new StatusResponse("Removed from favourite"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity editPost(Long userId, Long postId, PostRequest postRequest) {
        return null;
    }

    @Override
    public ResponseEntity deletePost(Long userId, Long postId) {
        return null;
    }
}
