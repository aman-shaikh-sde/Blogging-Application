package com.blogapp.blog_app_apis.service;

import com.blogapp.blog_app_apis.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


public interface FileService {

    String uploadImage(String path, MultipartFile file) throws IOException;
     InputStream   getResource(String path,String fileName) throws ResourceNotFoundException;
}
