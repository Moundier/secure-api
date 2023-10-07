package com.example.demo.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.auth.AuthRoute.RegisterDTO;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepo;

import lombok.RequiredArgsConstructor;

import static com.example.demo.auth.AuthRoute.AuthDTO;
import static com.example.demo.auth.AuthRoute.ErrorDTO;
import static com.example.demo.auth.AuthRoute.TokenDTO;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public ResponseEntity<?> signup(RegisterDTO request) {
        
        User alreadyUser = userRepo.findByEmail(request.email());

        if (alreadyUser != null) {
            final String message = "Email address is already in use";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO(message));
        }

        var user = User.builder()
            .firstname(request.firstname())
            .lastname(request.lastname())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .role(Role.USER)
            .build();

        userRepo.save(user);

        var token = jwtService.generateToken(user);

        return ResponseEntity.status(HttpStatus.OK).body(new TokenDTO(token));
    }

    public ResponseEntity<?> authenticate(AuthDTO request) {

        var user = userRepo.findByEmail(request.email());
        
        if (user == null) {
            final String message = "User is not registered. Please sing up";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDTO(message));
        }

        // Email found and posses Password 
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
            )
        );

        var token = jwtService.generateToken(user);

        return ResponseEntity.status(HttpStatus.OK).body(new TokenDTO(token));
    }

    // This Creates an Admin
    public ResponseEntity<?> authorize(RegisterDTO request) {

        var user = User.builder()
            .firstname(request.firstname())
            .lastname(request.lastname())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .role(Role.ADMIN) 
            .build();

        userRepo.save(user);

        var token = jwtService.generateToken(user);

        return ResponseEntity.status(HttpStatus.OK).body(new TokenDTO(token));
    }
    
}
