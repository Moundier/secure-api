package com.example.demo.user;

import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepo userRepo;

    public ResponseEntity<?> save(User user) {
        return created201(userRepo.save(user));
    }

    public User edit(Integer id, User newer) {
        // Check if the user with the given ID exists
        User older = userRepo.findById(id).orElseThrow(() -> notFound404("User not found!"));
        System.out.println();
        
        // Get the existing user
        
        older.setFirstname(newer.getFirstname());
        older.setLastname(newer.getLastname());
        older.setEmail(newer.getEmail());
        older.setPassword(newer.getPassword());
        older.setRole(Role.USER);
        
        // Save the updated user back to the database
        userRepo.save(older);
        return newer;
        
    }

    public ResponseEntity<?> wipe(Integer integer) {
        userRepo.deleteById(integer);
        return deleted204("User has been deleted!");
    }

    public <T> ResponseEntity<T> updated200(T body) {
        return ResponseEntity
            .status(OK)
            .body(body);
    }

    public <T> ResponseEntity<T> created201(T body) {
        return ResponseEntity
            .status(CREATED)
            .body(body);
    }

    public ResponseEntity<?> deleted204(String message) {
        return ResponseEntity
           .status(NO_CONTENT)
           .body(message);
    }

    // It can be general
    public RuntimeException badRequest400(String message) {
        return new RuntimeException(message + " was not found in database!");
    }
    
    public RuntimeException unauthorized401(String message) {
        return new RuntimeException(message + " was not found in database!");
    }

    public RuntimeException paymentRequired402(String message) {
        return new RuntimeException(message + " was not found in database!");
    }
    
    public RuntimeException badRequest403(String message) {
        return new RuntimeException(message + " was not found in database!");
    }

    public RuntimeException notFound404(String message) {
        return new RuntimeException(message + " was not found in database!");
    }

}
