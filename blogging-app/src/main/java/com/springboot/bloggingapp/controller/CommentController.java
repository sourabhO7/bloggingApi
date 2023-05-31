package com.springboot.bloggingapp.controller;

import com.springboot.bloggingapp.payload.CommentDto;
import com.springboot.bloggingapp.service.CommentService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //spring boot REST api for create post
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommentDto> createPost(
            @PathVariable("postId") long postId,
            @Valid @RequestBody CommentDto commentDto
    ){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    //spring boot REST api for get all comments by post id
    @GetMapping
    public List<CommentDto> getAllCommentsByPostId(
            @PathVariable("postId") long postId
    ){
        return commentService.getCommentsByPostId(postId);
    }

    //spring boot REST api for get comment by id
    @GetMapping("/{id}")
    public CommentDto getCommentById(
            @PathVariable("postId") long postId,
            @PathVariable("id") long id
    ){
        return commentService.getCommentById(postId, id);
    }

    //spring boot REST api for update comment
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CommentDto updateComment(
            @PathVariable("postId") long postId,
            @PathVariable("id") long id,
            @Valid @RequestBody CommentDto commentDto
    ){
        return commentService.updateComment(postId, id, commentDto);
    }

    //spring boot REST api for delete comment
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteComment(
            @PathVariable("postId") long postId,
            @PathVariable("id") long id
    ){
        commentService.deleteComment(postId, id);
        return new ResponseEntity("Comment deleted successfully",HttpStatus.OK);
    }
}
