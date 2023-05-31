package com.springboot.bloggingapp.service.impl;

import com.springboot.bloggingapp.entity.Comment;
import com.springboot.bloggingapp.entity.Post;
import com.springboot.bloggingapp.exception.BlogAPIException;
import com.springboot.bloggingapp.exception.ResourceNotFoundException;
import com.springboot.bloggingapp.payload.CommentDto;
import com.springboot.bloggingapp.payload.PostResponse;
import com.springboot.bloggingapp.repository.CommentRepository;
import com.springboot.bloggingapp.repository.PostRepository;
import com.springboot.bloggingapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepository,
                              CommentRepository commentRepository,
                              ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId,CommentDto commentDto) {
        Post post = findByIdPost(postId);
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment createdComment = saveToDb(comment);
        return mapToDto(createdComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> postComments = commentRepository.findByPostId(postId);
        List<CommentDto> comments = postComments.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return comments;
    }

    @Override
    public CommentDto getCommentById(long postId, long id) {
        Post post = findByIdPost(postId);
        Comment comment = findByIdComment(id);

        checkBlogAPIException(post,comment);

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        Post post = findByIdPost(postId);
        Comment comment = findByIdComment(id);
        checkBlogAPIException(post,comment);
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = saveToDb(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long id) {
        Post post = findByIdPost(postId);
        Comment comment = findByIdComment(id);
        checkBlogAPIException(post,comment);
        commentRepository.delete(comment);
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    private Post findByIdPost(long postId){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );
        return post;
    }

    private Comment findByIdComment(long id){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", id)
        );
        return comment;
    }
    private boolean checkBlogAPIException(Post post, Comment comment){
        if(!post.getId().equals(comment.getPost().getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment doesn't belong to the post" );
        }
        return true;
    }

    private Comment saveToDb(Comment comment){
        Comment save = commentRepository.save(comment);
        return save;
    }
}
