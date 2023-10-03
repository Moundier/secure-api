package com.example.demo.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.auth.AuthRoute.AuthRequest;
import com.example.demo.auth.AuthRoute.RegisterRequest;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepo;

import lombok.RequiredArgsConstructor;

import static com.example.demo.auth.AuthRoute.AuthResponse;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthResponse register(RegisterRequest request) {
        
        var user = User.builder()
            .firstname(request.firstname())
            .lastname(request.lastname())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .role(Role.USER)
            .build();

        userRepo.save(user);

        var token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse authenticate(AuthRequest request) {
        
        // Authentication for Login
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
            )
        );

        var user = userRepo.findByEmail(request.email());

        var token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }

    // This Creates an Admin
    public AuthResponse authorize(RegisterRequest request) {

        var user = User.builder()
            .firstname(request.firstname())
            .lastname(request.lastname())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .role(Role.ADMIN) 
            .build();

        userRepo.save(user);

        var token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
    
}
