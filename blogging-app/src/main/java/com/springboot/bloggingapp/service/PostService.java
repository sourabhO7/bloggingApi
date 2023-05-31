package com.springboot.bloggingapp.service;

import com.springboot.bloggingapp.payload.PostDto;
import com.springboot.bloggingapp.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);
    PostDto updatePost(long id, PostDto postDto);
    void deletePost(long id);
}
