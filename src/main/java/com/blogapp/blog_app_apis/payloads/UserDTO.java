package com.blogapp.blog_app_apis.payloads;


import com.blogapp.blog_app_apis.entity.Comment;
import com.blogapp.blog_app_apis.entity.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private int userId;

    @NotEmpty
    @Size(min=4,message = "Username must be greater than 4 character")
    private String userName;
    @Email(message = "Enter Valid Email")
    private String userEmail;
    @NotEmpty
    @Size(min=4,max=10,message = "Password must be between 4 To 10 Char" )
    private String userPass;
    @NotEmpty
    private String userAbout;

    private Set<RoleDTO> roles = new HashSet<>();


    private Set<Comment> comment=new HashSet<>();

}
