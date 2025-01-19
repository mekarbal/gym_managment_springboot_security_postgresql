package com.projects.gym_management.controllers;

import com.projects.gym_management.dtos.ChangePasswordRequest;
import com.projects.gym_management.dtos.PaginatedUserResponse;
import com.projects.gym_management.dtos.UpdateRoleRequest;
import com.projects.gym_management.dtos.UserResponse;
import com.projects.gym_management.entities.User;
import com.projects.gym_management.mappers.UserMapper;
import com.projects.gym_management.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PatchMapping
    public ResponseEntity<?> changePassword(
        @Valid @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PaginatedUserResponse> usersList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cin", required = false) String cin,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "perPage", defaultValue = "10") int perPage
    ) {
        Page<User> users = userService.getUsers(name, cin, page, perPage);

        List<UserResponse> userResponses = users.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());

        PaginatedUserResponse response = new PaginatedUserResponse(
                userResponses,
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages()
        );

        return ResponseEntity.ok().body(response);
    }
    @PutMapping("/update-role")
    public ResponseEntity<?> updateUserRole(@Valid @RequestBody UpdateRoleRequest updateRoleRequest ) {
         userService.updateUserRole(updateRoleRequest.getEmail(), updateRoleRequest.getRole());
        return ResponseEntity.ok().build();
    }

}