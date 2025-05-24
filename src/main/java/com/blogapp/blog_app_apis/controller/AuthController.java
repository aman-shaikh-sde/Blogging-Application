package com.blogapp.blog_app_apis.controller;


import com.blogapp.blog_app_apis.payloads.JwtAuthRequest;
import com.blogapp.blog_app_apis.payloads.JwtAuthResponse;
import com.blogapp.blog_app_apis.payloads.UserDTO;
import com.blogapp.blog_app_apis.security.JwtTokenHelper;
import com.blogapp.blog_app_apis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request
            ){
        this.authenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails=this.userDetailsService.loadUserByUsername(request.getUsername());
       String token= this.jwtTokenHelper.generateToken(userDetails);
       JwtAuthResponse response=new JwtAuthResponse();
       response.setToken(token);
       return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);


        this.authenticationManager.authenticate(authenticationToken);
    }

// Register new user

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO){
       UserDTO registerUser= this.userService.registerNewUser(userDTO);

       return new ResponseEntity<UserDTO>(registerUser,HttpStatus.CREATED);

    }
}
