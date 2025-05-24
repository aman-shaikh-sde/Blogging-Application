package com.blogapp.blog_app_apis.service;

import com.blogapp.blog_app_apis.entity.Post;
import com.blogapp.blog_app_apis.payloads.CommentDTO;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO, Integer postId,Integer userId);

    public void deleteComment(Integer commentId);
}
