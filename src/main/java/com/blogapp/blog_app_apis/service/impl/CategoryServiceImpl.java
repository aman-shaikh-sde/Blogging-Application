package com.blogapp.blog_app_apis.service.impl;

import com.blogapp.blog_app_apis.entity.Category;
import com.blogapp.blog_app_apis.exception.ResourceNotFoundException;
import com.blogapp.blog_app_apis.payloads.CategoryDTO;
import com.blogapp.blog_app_apis.payloads.CategoryResponse;
import com.blogapp.blog_app_apis.repository.CategoryRepo;
import com.blogapp.blog_app_apis.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category=this.modelMapper.map(categoryDTO,Category.class);
        Category addedCategory=this.categoryRepo.save(category);
        return this.modelMapper.map(addedCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));

        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDesc(categoryDTO.getCategoryDesc());

        Category updateCategory=this.categoryRepo.save(category);

        return this.modelMapper.map(updateCategory,CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));

    this.categoryRepo.delete(category);


    }

    @Override
    public CategoryResponse getAllCategory(Integer pageNumber,Integer pageSize,String sortBy ) {

        Pageable pageable= PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));

        Page<Category> page=this.categoryRepo.findAll(pageable);
        List<Category> allcategory=page.getContent();



        List<CategoryDTO> categoryDTO= allcategory.stream()
                .map((category )-> this.modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());

        CategoryResponse categoryResponse=new CategoryResponse();

        categoryResponse.setCategoryContent(categoryDTO);
        categoryResponse.setPageNumber(page.getNumber());
        categoryResponse.setLastPage(page.isLast());
        categoryResponse.setTotalPages(page.getTotalPages());
        categoryResponse.setTotalElements(page.getTotalElements());

        return categoryResponse;

    }

    @Override
    public CategoryDTO getCategoryById(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));
        return this.modelMapper.map(category, CategoryDTO.class);
    }



}
