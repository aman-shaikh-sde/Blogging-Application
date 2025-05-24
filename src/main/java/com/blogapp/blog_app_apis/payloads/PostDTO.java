package com.blogapp.blog_app_apis.payloads;

import com.blogapp.blog_app_apis.entity.Category;
import com.blogapp.blog_app_apis.entity.Comment;
import com.blogapp.blog_app_apis.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
public class PostDTO {
    private int postId;
    private String postTitle;
    private String postContent;
    private String postImgName;
    private Date postDate;

    private CategoryDTO category;
    private UserDTO user;

    private Set<CommentDTO> comment=new HashSet<>();

}
