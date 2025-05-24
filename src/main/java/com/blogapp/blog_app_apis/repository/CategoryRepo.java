package com.blogapp.blog_app_apis.repository;

import com.blogapp.blog_app_apis.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {

}
