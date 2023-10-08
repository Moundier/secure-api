package com.example.demo.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepo userRepo;

    public ResponseEntity<?> edit(Integer id, User user) {

        var older = userRepo.findById(id).orElseThrow();
        
        older = User.builder()
            .id(id)
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .email(user.getEmail())
            .password(user.getPassword())
            .role(Role.USER)
            .build();
        

        return ResponseEntity.status(HttpStatus.OK).body(userRepo.save(older));
    }

    public ResponseEntity<?> wipe(Integer id) {
        userRepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
