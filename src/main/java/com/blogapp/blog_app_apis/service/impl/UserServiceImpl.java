package com.blogapp.blog_app_apis.service.impl;

import com.blogapp.blog_app_apis.entity.Roles;
import com.blogapp.blog_app_apis.entity.User;
import com.blogapp.blog_app_apis.exception.ResourceNotFoundException;
import com.blogapp.blog_app_apis.payloads.PostResponse;
import com.blogapp.blog_app_apis.payloads.UserDTO;
import com.blogapp.blog_app_apis.payloads.UserResponse;
import com.blogapp.blog_app_apis.repository.RoleRepo;
import com.blogapp.blog_app_apis.repository.UserRepo;
import com.blogapp.blog_app_apis.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User user=this.dtoToUser(userDTO);
        User savedUser=this.userRepo.save(user);
        return this.userDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {

        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" ,"id" ,userId));

        user.setUserName(userDTO.getUserName());
        user.setUserEmail(userDTO.getUserEmail());
        user.setUserPass(userDTO.getUserPass());
        user.setUserAbout(userDTO.getUserAbout());

        User updatedUser=this.userRepo.save(user);
        UserDTO userDTO1=this.userDTO(updatedUser);
        return userDTO1;
    }

    @Override
    public UserDTO getUserById(Integer userId) {
    User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" ,"id" ,userId));

        return this.userDTO(user);
    }

    @Override

    public UserResponse getAllUser(Integer pageNumber, Integer pageSize,String sortBy) {

        Pageable pageable= PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));

        Page<User> page=this.userRepo.findAll(pageable);
        List <User> users=page.getContent();

        List<UserDTO> userDTOs=users.stream()
               .map(user -> this.userDTO(user))
               .collect(Collectors.toList());

        UserResponse userResponse=new UserResponse();

        userResponse.setUserContent(userDTOs);
        userResponse.setPageSize(page.getSize());
        userResponse.setTotalPages(page.getTotalPages());
        userResponse.setLastPage(page.isLast());
        userResponse.setTotalElements(page.getTotalElements());



              return userResponse;
    }

    @Override
    public void deleteUser(Integer userId) {

        User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" ,"id" ,userId));
        this.userRepo.delete(user);

    }


    private User dtoToUser(UserDTO userDTO){

        User user=this.modelMapper.map(userDTO,User.class);
        return user;

//    User user=new User();
////    user.setUserId(userDTO.getUserId());
////    user.setUserName(userDTO.getUserName());
////    user.setUserEmail(userDTO.getUserEmail());
////    user.setUserPass(userDTO.getUserPass());
////    user.setUserAbout(userDTO.getUserAbout());
////    return user;

    }

    private UserDTO userDTO(User user){


        UserDTO userDTO=this.modelMapper.map(user,UserDTO.class);
        return userDTO;

//        UserDTO userDTO=new UserDTO();
//        userDTO.setUserName(user.getUserName());
//        userDTO.setUserId(user.getUserId());
//        userDTO.setUserAbout(user.getUserAbout());
//        userDTO.setUserEmail(userDTO.getUserEmail());
//        userDTO.setUserPass(user.getUserPass());
//        return userDTO;
    }


    @Override
    public UserDTO registerNewUser(UserDTO userDTO) {
        User user= this.modelMapper.map(userDTO,User.class);
        user.setUserPass(this.passwordEncoder.encode(user.getPassword()));

        Roles role=this.roleRepo.findById(502).get();
    user.getRoles().add(role);
    User newUser=this.userRepo.save(user);

        return this.modelMapper.map(newUser,UserDTO.class );
    }
}
