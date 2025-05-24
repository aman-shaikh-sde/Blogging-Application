package com.blogapp.blog_app_apis.repository;

import com.blogapp.blog_app_apis.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo  extends JpaRepository<Comment,Integer> {
}
