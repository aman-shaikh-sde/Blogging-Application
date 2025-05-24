package com.blogapp.blog_app_apis.controller;

import com.blogapp.blog_app_apis.entity.Comment;
import com.blogapp.blog_app_apis.payloads.ApiResponse;
import com.blogapp.blog_app_apis.payloads.CommentDTO;
import com.blogapp.blog_app_apis.service.CommentService;
import com.blogapp.blog_app_apis.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;


    @PostMapping("/post/{postId}/{userId}/comment")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO,
                                                    @PathVariable Integer postId,
                                                    @PathVariable Integer userId){
        CommentDTO CreateComment=this.commentService.createComment(commentDTO,postId,userId);

        return new ResponseEntity<CommentDTO>(CreateComment, HttpStatus.CREATED);

    }
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(
                                                     @PathVariable Integer commentId){
       this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully",true),HttpStatus.OK);
    }
}
