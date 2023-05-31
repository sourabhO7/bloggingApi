package com.springboot.bloggingapp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;
    //name should not be null or empty
    //size should be minimum 5 characters
    @NotEmpty
    @Size(min = 5,message = "Comment name should be minimum 5 characters")
    private String name;
    //email valid constraints
    @Email
    private String email;
    //body should not be empty or null
    @NotBlank
    private String body;
}
