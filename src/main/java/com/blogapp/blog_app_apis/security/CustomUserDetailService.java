package com.blogapp.blog_app_apis.security;

import com.blogapp.blog_app_apis.entity.User;
import com.blogapp.blog_app_apis.exception.ResourceNotFoundException;
import com.blogapp.blog_app_apis.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=this.userRepo.findByUserEmail(username).orElseThrow(()->(new ResourceNotFoundException("User","userEmail: "+username,0)));

        return user;
    }
}
