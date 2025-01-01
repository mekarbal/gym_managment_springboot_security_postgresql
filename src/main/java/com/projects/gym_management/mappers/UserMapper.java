package com.projects.gym_management.mappers;

import com.projects.gym_management.dtos.UserResponse;
import com.projects.gym_management.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserResponse toResponse(User user) {
        return UserResponse.builder().id(user.getId()).firstname(user.getFirstname()).lastname(user.getLastname())
                .email(user.getEmail()).role(String.valueOf(user.getRole())).build();

    }
    public List<UserResponse> toDtoList(List<User> users) {
        return users.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
