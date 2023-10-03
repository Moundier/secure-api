package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.auth.AuthService;
import com.example.demo.auth.AuthRoute.RegisterRequest;
import com.example.demo.user.Role;
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
				new User(null, "nami", "namizo", "nami@gmail.com", "1234", Role.USER),
				new User(null, "usop", "sogeking", "usop@gmail.com", "1234", Role.ADMIN)
			);

			List<User> ADMINS = List.of(
				new User(null, "zoro", "roronoa", "zoro@gmail.com", "1234", Role.USER),
				new User(null, "luff", "monkey", "luff@gmail.com", "1234", Role.ADMIN)
			);

			for (User user : USERS) {

				RegisterRequest response = new RegisterRequest(
					user.getFirstname(),
					user.getLastname(), 
					user.getEmail(),
					user.getPassword()
				);

				authService.register(response);
			}

			for (User admin : ADMINS) {

				RegisterRequest response = new RegisterRequest(
					admin.getFirstname(), 
					admin.getLastname(), 
					admin.getEmail(),
					admin.getPassword());

				authService.authorize(response);
			}

        };
    }

}
