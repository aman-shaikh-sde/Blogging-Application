package com.blogapp.blog_app_apis.service.impl;

import com.blogapp.blog_app_apis.exception.ResourceNotFoundException;
import com.blogapp.blog_app_apis.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //File Name

        String name=file.getOriginalFilename();

        //full Path

        String fullPath=path+ File.separator+name;

        //create file
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs(); // <- fixes directory creation
        }

        //file copy

        Files.copy(file.getInputStream(), Paths.get(fullPath));

        return name;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws ResourceNotFoundException {
        String fullPath=path+File.separator+fileName;
        InputStream is= null;
        try {
            is = new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return is;
    }
}
