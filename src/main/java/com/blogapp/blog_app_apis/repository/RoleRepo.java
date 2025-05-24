package com.blogapp.blog_app_apis.repository;

import com.blogapp.blog_app_apis.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Roles,Integer> {
}
