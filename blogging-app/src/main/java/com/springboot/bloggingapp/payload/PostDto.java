package com.springboot.bloggingapp.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private long id;
    //title should not be null or empty
    //title should have minimum 2 characters
    @NotEmpty
    @Size(min = 2,message = "Post title should have 2 characters minimum")
    private String title;
    //description should have at least 10 characters
    //description should not be null or empty
    @NotEmpty
    @Size(min = 10,message = "Post description should have 10 characters minimum")
    private String description;
    //content should not be empty
    @NotBlank
    private String content;

    private List<CommentDto> comments;
}
