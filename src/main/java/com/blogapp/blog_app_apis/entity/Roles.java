package com.blogapp.blog_app_apis.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Roles {
    @Id
    private int id;
    private String name;


    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

}
