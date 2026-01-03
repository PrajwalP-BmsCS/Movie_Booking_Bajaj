package com.cinema_package.cinema_project.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinema_package.cinema_project.enums.Role;
import com.cinema_package.cinema_project.security.JwtUtil;
import com.cinema_package.cinema_project.user.User;
import com.cinema_package.cinema_project.user.UserRepository;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public AuthController(UserRepository userRepository,
                          BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setRole(user.getRole());
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        

        return "User registered successfully";
    }
    
    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(
                loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return JwtUtil.generateToken(user.getEmail());
    }

}
