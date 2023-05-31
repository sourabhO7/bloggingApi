package com.springboot.bloggingapp.controller;

import com.springboot.bloggingapp.payload.PostDto;
import com.springboot.bloggingapp.payload.PostResponse;
import com.springboot.bloggingapp.service.PostService;
import com.springboot.bloggingapp.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //spring boot REST api for create post
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody PostDto postDto
    ){
        return new ResponseEntity(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //spring boot REST api for get all posts
    @GetMapping
    public PostResponse getAllPost(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NO,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    //spring boot REST api for get post by id
    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable("id") long id){
        return postService.getPostById(id);
    }

    //spring boot REST api for update post
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PostDto updatePost(
            @PathVariable("id") long id,
            @Valid @RequestBody PostDto postDto
            ){
        return postService.updatePost(id,postDto);
    }

    //spring boot REST api for delete post
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePost(
            @PathVariable("id") long id
    ){
        postService.deletePost(id);
        return new ResponseEntity("Post deleted successfully",HttpStatus.OK);
    }
}
