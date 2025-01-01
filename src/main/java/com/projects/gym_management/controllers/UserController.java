package com.projects.gym_management.controllers;

import com.projects.gym_management.dtos.ChangePasswordRequest;
import com.projects.gym_management.dtos.UserResponse;
import com.projects.gym_management.entities.User;
import com.projects.gym_management.mappers.UserMapper;
import com.projects.gym_management.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserService userService;
    private final UserMapper userMapper;

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> usersList(){
        List<User> users= userService.getUsers();
        return ResponseEntity.ok().body(userMapper.toDtoList(users));
    }
}