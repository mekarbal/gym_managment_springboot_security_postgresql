package com.projects.gym_management;

import com.projects.gym_management.entities.Role;
import com.projects.gym_management.entities.User;
import com.projects.gym_management.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.projects.gym_management.entities.Role.ADMIN;

@SpringBootApplication
@RequiredArgsConstructor
public class GymManagementApplication {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(GymManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner createAdminUser() {
        return args -> {
            Optional<User> userOptional = userRepository.findByEmail("admin@admin.com");

            if (userOptional.isEmpty()) {
                User admin = User.builder()
                        .firstname("Admin")
                        .lastname("User")
                        .email("admin@admin.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(admin);
                System.out.println("Admin user created successfully!");
            }
        };
    }

}
