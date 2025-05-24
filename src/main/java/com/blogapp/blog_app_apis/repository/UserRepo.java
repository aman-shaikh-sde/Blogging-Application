package com.blogapp.blog_app_apis.repository;

import com.blogapp.blog_app_apis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

        Optional<User> findByUserEmail(String email);
}
