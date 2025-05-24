package com.blogapp.blog_app_apis.controller;

import com.blogapp.blog_app_apis.entity.Category;
import com.blogapp.blog_app_apis.entity.Post;
import com.blogapp.blog_app_apis.entity.User;
import com.blogapp.blog_app_apis.payloads.ApiResponse;
import com.blogapp.blog_app_apis.payloads.ImageResponse;
import com.blogapp.blog_app_apis.payloads.PostDTO;
import com.blogapp.blog_app_apis.payloads.PostResponse;
import com.blogapp.blog_app_apis.service.FileService;
import com.blogapp.blog_app_apis.service.PostService;
import com.blogapp.blog_app_apis.service.impl.PostServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RequestMapping("/api/")
@RestController
public class PostController {

    @Autowired
    private FileService fileService;

    @Autowired
    private PostService postService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId) {
        PostDTO createPost = this.postService.createPost(postDTO, userId, categoryId);

        return new ResponseEntity<PostDTO>(createPost, HttpStatus.CREATED);
    }


    @GetMapping("/category/{categoryId}/post")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDTO> postDTOS = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDTO>>(postDTOS, HttpStatus.OK);


    }

    @GetMapping("/user/{userId}/post")
    public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable Integer userId) {
        List<PostDTO> postDTOS = this.postService.getPostByUser(userId);
        return new ResponseEntity<List<PostDTO>>(postDTOS, HttpStatus.OK);


    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId) {
        return ResponseEntity.ok(this.postService.getPostById(postId));
    }

    @GetMapping("/post")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value="pageNumber",defaultValue = "1",required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                                                   @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy
                                                   ) {

        PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully ", true), HttpStatus.OK);
    }

        @PutMapping("/post/{postId}")
        public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer postId){
       PostDTO updatePost= this.postService.updatePost(postDTO,postId);
       return new ResponseEntity<PostDTO>(updatePost,HttpStatus.OK);
        }

        @GetMapping("/post/search/{keyword}")
        public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable String keyword){
        List<PostDTO> searchTitle=this.postService.getPostBySearch(keyword);
        return new ResponseEntity<List<PostDTO>>(searchTitle,HttpStatus.OK);
        }

        @PostMapping("/post/image/upload/{postId}")
        public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                             @PathVariable Integer postId) throws IOException {

        String fileName=this.fileService.uploadImage(path,image);
        PostDTO postDTO=this.postService.getPostById(postId);
        postDTO.setPostImgName(fileName);
       PostDTO updatePost= this.postService.updatePost(postDTO,postId);
        return new ResponseEntity<PostDTO>(updatePost,HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadIMage(@PathVariable("imageName") String imageName,
                              HttpServletResponse response) throws IOException{
        InputStream resource=this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}