package com.blogapp.blog_app_apis.service.impl;

import com.blogapp.blog_app_apis.entity.Comment;
import com.blogapp.blog_app_apis.entity.Post;
import com.blogapp.blog_app_apis.entity.User;
import com.blogapp.blog_app_apis.exception.ResourceNotFoundException;
import com.blogapp.blog_app_apis.payloads.CommentDTO;
import com.blogapp.blog_app_apis.payloads.PostDTO;
import com.blogapp.blog_app_apis.payloads.UserResponse;
import com.blogapp.blog_app_apis.repository.CommentRepo;
import com.blogapp.blog_app_apis.repository.PostRepo;
import com.blogapp.blog_app_apis.repository.UserRepo;
import com.blogapp.blog_app_apis.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO , Integer postId,Integer userId){
        Post post =this.postRepo.findById(postId).orElseThrow(()->(new ResourceNotFoundException("Post","Post Id",postId)));
        User user =this.userRepo.findById(userId).orElseThrow(()->(new ResourceNotFoundException("User","User Id",userId)));
       Comment comment=this.modelMapper.map(commentDTO, Comment.class);
         comment.setPost(post);
         comment.setUser(user);
        Comment savedComment= this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment,CommentDTO.class);
    }

    @Override
    public void deleteComment( Integer commentId) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->(new ResourceNotFoundException("Comment","Comment Id",commentId)));
        this.commentRepo.delete(comment);

    }
}
