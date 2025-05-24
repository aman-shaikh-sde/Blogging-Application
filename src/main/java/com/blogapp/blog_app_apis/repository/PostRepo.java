package com.blogapp.blog_app_apis.repository;

import com.blogapp.blog_app_apis.entity.Category;
import com.blogapp.blog_app_apis.entity.Post;
import com.blogapp.blog_app_apis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByCategory(Category category);
    List<Post> findByUser(User user);

    List<Post> findByPostTitleContaining(String keyword);
}
