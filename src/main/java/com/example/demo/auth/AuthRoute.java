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
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/authorize")
    public ResponseEntity<AuthResponse> authorize(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.authorize(request));
    }

    // Todo: Make the following records

    public static record AuthRequest(
        String email, 
        String password
    ) { }

    public static record AuthResponse(
        String token
    ) { }
    
    public static record RegisterRequest(
        String firstname,
        String lastname,
        String email,
        String password
    ) {  }
    
}
