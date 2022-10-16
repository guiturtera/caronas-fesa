package com.carona.caronasfesa.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.carona.caronasfesa.entities.Post;
import com.carona.caronasfesa.repositories.PostRepository;
import com.carona.caronasfesa.repositories.UserRepository;

@RestController
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{userId}/post")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable(name = "userId") Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Not found User with id = " + userId);
        }

        List<Post> posts = postRepository.findByUserUserId(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/post")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);

    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable("postId") Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Post with id = " + postId));

        return new ResponseEntity<>(post, HttpStatus.OK);

    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("postId") Integer postId) {
        Optional<Post> postToDelete = postRepository.findById(postId);
        if (postToDelete.isPresent()) {
            postRepository.delete(postToDelete.get());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "user/{userId}/post")
    public ResponseEntity<Post> newPost(@PathVariable(value = "userId") Integer userId, @RequestBody Post newPost) {
        Post createdPost = userRepository.findById(userId).map(user_found -> {
            newPost.setUser(user_found);
            return postRepository.save(newPost);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found User with id = " + userId));

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    
    @PutMapping("/post/{postId}")
    public ResponseEntity<Post> updateComment(@PathVariable("postId") Integer postId, @RequestBody Post postRequest) {
        Post post = postRepository.findById(postId)
          .orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + "not found"));

      post.setTitle(postRequest.getTitle());

      return new ResponseEntity<>(postRepository.save(post), HttpStatus.OK);
    }
     
}
