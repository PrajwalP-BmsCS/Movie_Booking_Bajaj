package com.cinema_package.cinema_project.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public User getMyProfile() {
        return userService.getCurrentUser();
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/me")
    public User updateMyProfile(@RequestBody UserProfileRequest request) {
        return userService.updateCurrentUser(request);
    }
}
