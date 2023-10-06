package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.auth.AuthService;
import com.example.demo.auth.AuthRoute.RegisterDTO;
import com.example.demo.user.User;

@SpringBootApplication
public class Main {

	@Autowired
	private AuthService authService;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
    CommandLineRunner runner() {
		
		return args -> {
            
			List<User> USERS = List.of(
				new User(null, "nami", "namizo", "nami@gmail.com", "1234", null),
				new User(null, "usop", "sogeking", "usop@gmail.com", "1234", null)
			);

			List<User> ADMINS = List.of(
				new User(null, "zoro", "roronoa", "zoro@gmail.com", "1234", null),
				new User(null, "luff", "monkey", "luff@gmail.com", "1234", null)
			);

			for (User user : USERS) {

				RegisterDTO response = new RegisterDTO(
					user.getFirstname(),
					user.getLastname(), 
					user.getEmail(),
					user.getPassword()
				);

				authService.signup(response);
			}

			for (User admin : ADMINS) {

				RegisterDTO response = new RegisterDTO(
					admin.getFirstname(), 
					admin.getLastname(), 
					admin.getEmail(),
					admin.getPassword());

				authService.authorize(response);
			}

        };
    }

}
