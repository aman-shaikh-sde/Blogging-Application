package com.blogapp.blog_app_apis.controller;


import com.blogapp.blog_app_apis.payloads.ApiResponse;
import com.blogapp.blog_app_apis.payloads.PostResponse;
import com.blogapp.blog_app_apis.payloads.UserDTO;
import com.blogapp.blog_app_apis.payloads.UserResponse;
import com.blogapp.blog_app_apis.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;


    //POST Create User
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO  userDTO){
       UserDTO createdUserDTO= this.userService.createUser(userDTO);
        System.out.println("Created Successfully");

        return  new ResponseEntity<>(createdUserDTO,HttpStatus.CREATED);

    }

    //PUT Update User
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO,@PathVariable Integer userId){
        UserDTO updateUserDTO=this.userService.updateUser(userDTO,userId);
        System.out.println("Updated Successfully");

        return new ResponseEntity<>(updateUserDTO,HttpStatus.OK);

    }

    //Admin
    //DELETE Delete User
    @PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
      this.userService.deleteUser(userId);
      return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully ",true),HttpStatus.OK);
}
    //GET Get All User

    @GetMapping("/")
    public ResponseEntity<UserResponse> getAllUser(@RequestParam(value = "pageNumber",defaultValue = "1",required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
                                                   @RequestParam(value = "sortBy",defaultValue = "userId",required = false)String sortBy
                                                   ) {
        return ResponseEntity.ok(this.userService.getAllUser(pageNumber,pageSize,sortBy));
    }
    //GET Get By Id

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId) {
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);    }

}
