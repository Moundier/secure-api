package com.example.demo.user;

public enum Role {
    USER, /* Have complete access, but cannot modify */
    ADMIN, /* Have complete access and can modify */
    VISITANT /* Have limited access */
}
