package com.projects.gym_management.services;

import com.projects.gym_management.dtos.ChangePasswordRequest;
import com.projects.gym_management.entities.Role;
import com.projects.gym_management.entities.User;
import com.projects.gym_management.exceptions.RoleNotFoundException;
import com.projects.gym_management.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        repository.save(user);
    }

    public Page<User> getUsers(String name, String cin, int page, int perPage) {
        try {
            if ((name == null || name.isEmpty()) && (cin == null || cin.isEmpty())) {
                return repository.findAll(PageRequest.of(page, perPage));
            }
            return repository.findBySearchCriteria(name, cin, PageRequest.of(page, perPage));
        } catch (Exception e) {
            throw new RuntimeException("Error fetching users: " + e.getCause().getMessage(), e);
        }
    }
    @Transactional
    public void updateUserRole(String email, String roleName) {
        Role role;

        try {
            role = Role.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RoleNotFoundException("Role " + roleName + " not found.");
        }

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found."));

        user.setRole(role);
        repository.save(user);
    }
}
