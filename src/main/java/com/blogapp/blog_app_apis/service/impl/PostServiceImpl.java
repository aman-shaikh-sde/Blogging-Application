package com.blogapp.blog_app_apis.service.impl;

import com.blogapp.blog_app_apis.entity.Category;
import com.blogapp.blog_app_apis.entity.Post;
import com.blogapp.blog_app_apis.entity.User;
import com.blogapp.blog_app_apis.exception.ResourceNotFoundException;
import com.blogapp.blog_app_apis.payloads.CategoryDTO;
import com.blogapp.blog_app_apis.payloads.PostDTO;
import com.blogapp.blog_app_apis.payloads.PostResponse;
import com.blogapp.blog_app_apis.repository.CategoryRepo;
import com.blogapp.blog_app_apis.repository.PostRepo;
import com.blogapp.blog_app_apis.repository.UserRepo;
import com.blogapp.blog_app_apis.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDTO createPost(PostDTO postDTO,Integer userId,Integer categoryId) {

        User user=this.userRepo.findById(userId).orElseThrow(()->(new ResourceNotFoundException("User","User Id",userId)));

        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->(new ResourceNotFoundException("Category","Category Id",categoryId)));


        Post post=this.modelMapper.map(postDTO,Post.class);
        post.setPostImgName("defualt.png");
        post.setPostDate(new Date());
        post.setUser(user);
        post.setCategory(category);


        Post newPost=this.postRepo.save(post);

        return this.modelMapper.map(newPost,PostDTO.class);
    }


    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->(new ResourceNotFoundException("Post","Post Id",postId)));

       post.setPostTitle(postDTO.getPostTitle());
       post.setPostContent(postDTO.getPostContent());
       post.setPostImgName(postDTO.getPostImgName());

       Post updatePost=this.postRepo.save(post);

       return this.modelMapper.map(updatePost,PostDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post deletePost=this.postRepo.findById(postId).orElseThrow(()-> (new ResourceNotFoundException("Post","Post Id",postId)));
        this.postRepo.delete(deletePost);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {


        Pageable  pageable= PageRequest.of(pageNumber,pageSize,Sort.by(sortBy));

        Page<Post> page=this.postRepo.findAll(pageable);
        List<Post> allPost=page.getContent();

        List<PostDTO> postDTOS= allPost.stream()
                .map((post) -> this.modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();

        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(page.getNumber());
        postResponse.setPageSize(page.getSize());
        postResponse.setTotalElements(page.getTotalElements());
        postResponse.setTotalPages(page.getTotalPages());
        postResponse.setLastPage(page.isLast());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->(new ResourceNotFoundException("Post","Post Id",postId)));
        return this.modelMapper.map(post,PostDTO.class);
    }

    @Override
    public List<PostDTO> getPostByCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->(new ResourceNotFoundException("Category","Category Id",categoryId)));
       List<Post> posts=this.postRepo.findByCategory(category);
       List<PostDTO> postDTOS= posts.stream().map((post)->this.modelMapper.map(post,PostDTO.class)).collect(Collectors.toList());

        return postDTOS;
    }

    @Override
    public List<PostDTO> getPostByUser(Integer userId) {

        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User Id",userId));
        List<Post> posts=this.postRepo.findByUser(user);
        List<PostDTO> postDTOS= posts.stream().map((post)->this.modelMapper.map(post,PostDTO.class)).collect(Collectors.toList());

        return postDTOS ;
    }

    @Override
    public List<PostDTO> getPostBySearch(String keyword) {
        List<Post> posts=this.postRepo.findByPostTitleContaining(keyword);
        List<PostDTO> postDTOS=posts.stream()
                .map((post) ->this.modelMapper.map(post,PostDTO.class) )
                .collect(Collectors.toList());

        return postDTOS;
    }
}
