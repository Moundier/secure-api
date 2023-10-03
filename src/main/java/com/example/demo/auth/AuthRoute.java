package com.example.demo.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRoute {

    private final AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO request) {
        return authService.register(request);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthDTO request) {
        return authService.authenticate(request);
    }

    @PostMapping("/authorize")
    public ResponseEntity<?> authorize(@RequestBody RegisterDTO request) {
        return authService.authorize(request);
    }

    // Todo: make the following private

    public static record AuthDTO(
        String email, 
        String password
    ) { }
    
    public static record RegisterDTO(
        String firstname,
        String lastname,
        String email,
        String password
    ) {  }

    public static record ErrorDTO (
        String message
    ) { }

    public static record TokenDTO(
        String token
    ) { }
}
