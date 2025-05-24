package com.blogapp.blog_app_apis.service;

import com.blogapp.blog_app_apis.payloads.UserDTO;
import com.blogapp.blog_app_apis.payloads.UserResponse;

public interface UserService {

    UserDTO createUser(UserDTO user);
    UserDTO updateUser(UserDTO user,Integer userId);
    UserDTO getUserById(Integer userId);
    UserResponse getAllUser(Integer pageNumber, Integer pageSize,String sortBy);
    void deleteUser(Integer userId);


    UserDTO registerNewUser(UserDTO userDTO);

}
