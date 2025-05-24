package com.blogapp.blog_app_apis.service;

import com.blogapp.blog_app_apis.payloads.PostDTO;
import com.blogapp.blog_app_apis.payloads.PostResponse;

import java.util.List;

public interface PostService {

    //Create
    PostDTO createPost(PostDTO postDTO,Integer postId,Integer categoryId);

    //Update
    PostDTO updatePost(PostDTO postDTO,Integer postId);

    //Delete
    void deletePost(Integer postId);

    //GetAllPst
    PostResponse  getAllPost(Integer pageNumber, Integer pageSize,String sortBy);

    //GetPostById
    PostDTO getPostById(Integer postId);

    //GetAllPost Of Category
    List<PostDTO> getPostByCategory(Integer CategoryId);

    //getAllPost Of User
    List<PostDTO> getPostByUser(Integer userId);

    //GetPostBySearch
    List<PostDTO> getPostBySearch(String keyword);



}
