package com.springboot.bloggingapp.service.impl;

import com.springboot.bloggingapp.entity.Post;
import com.springboot.bloggingapp.exception.ResourceNotFoundException;
import com.springboot.bloggingapp.payload.PostDto;
import com.springboot.bloggingapp.payload.PostResponse;
import com.springboot.bloggingapp.repository.PostRepository;
import com.springboot.bloggingapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post createdPost = postRepository.save(post);
        return mapToDto(createdPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> content = posts.getContent();
        List<PostDto> contentDto = content.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return createPostResponse(posts,contentDto);
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = findByIdPost(id);
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = findByIdPost(id);

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = findByIdPost(id);
        postRepository.delete(post);
    }

    private Post findByIdPost(long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        return post;
    }

    private PostResponse createPostResponse(Page<Post> response, List<PostDto> contentDto){
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(contentDto);
        postResponse.setPageNo(response.getNumber());
        postResponse.setPageSize(response.getSize());
        postResponse.setTotalElements(response.getTotalElements());
        postResponse.setTotalPages(response.getTotalPages());
        postResponse.setLast(response.isLast());
        return postResponse;
    }

    private Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }

    private PostDto mapToDto(Post post){
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return postDto;
    }
}
