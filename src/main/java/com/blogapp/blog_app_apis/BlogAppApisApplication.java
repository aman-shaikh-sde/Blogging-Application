package com.blogapp.blog_app_apis;

import com.blogapp.blog_app_apis.entity.Roles;
import com.blogapp.blog_app_apis.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;



	public static void main(String[] args) {

		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();

	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("xyz"));

		try{
			Roles role=new Roles();
			role.setId(501);
			role.setName("ADMIN_USER");

			Roles role1=new Roles();
			role1.setId(502);
			role1.setName("NORMAL_USER");

			List<Roles> roles=List.of(role,role1);
			List<Roles> result=this.roleRepo.saveAll(roles);
			result.forEach(r->{
				System.out.println(r.getName());
			});
		}catch (Exception e){

		}
	}
}
