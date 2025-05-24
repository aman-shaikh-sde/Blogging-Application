package com.blogapp.blog_app_apis.service;

import com.blogapp.blog_app_apis.payloads.CategoryDTO;
import com.blogapp.blog_app_apis.payloads.CategoryResponse;

import java.util.List;

public interface CategoryService {


    //Create
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    //Update
    CategoryDTO updateCategory(CategoryDTO categoryDTO,Integer categoryId);

    //Delete
    public void deleteCategory(Integer categoryId);

    //GetAll
   CategoryResponse getAllCategory(Integer pageNumber,Integer pageSize,String sortBy);

    //GetById
    CategoryDTO getCategoryById(Integer categoryId);
}
