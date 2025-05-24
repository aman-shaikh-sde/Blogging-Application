package com.blogapp.blog_app_apis.controller;

import com.blogapp.blog_app_apis.entity.Category;
import com.blogapp.blog_app_apis.payloads.ApiResponse;
import com.blogapp.blog_app_apis.payloads.CategoryDTO;
import com.blogapp.blog_app_apis.payloads.CategoryResponse;
import com.blogapp.blog_app_apis.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;


    //Create
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO createCategory=this.categoryService.createCategory(categoryDTO);
        return new ResponseEntity<CategoryDTO>(createCategory, HttpStatus.CREATED);
    }

    //Update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable Integer categoryId ){
        CategoryDTO updateCategory=this.categoryService.updateCategory(categoryDTO,categoryId);
        return new ResponseEntity<CategoryDTO>(updateCategory,HttpStatus.OK);

    }


    //Delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted Successfully",true),HttpStatus.OK);
    }

    //Get ALl Category
    @GetMapping("/")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(value="pageNumber",defaultValue = "1",required = false) Integer pageNumber,
                                                             @RequestParam(value ="pageSize",defaultValue = "5",required = false)Integer pageSize,
                                                             @RequestParam(value = "sortBy",defaultValue = "categoryId",required = false)String sortBy
                                                             ){
        return ResponseEntity.ok(this.categoryService.getAllCategory(pageNumber,pageSize,sortBy));
    }

    //Get By Id
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
    }


}
