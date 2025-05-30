package org.example.webbangiay.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.webbangiay.dto.response.UserResponse;
import org.example.webbangiay.dto.request.ApiResponse;
import org.example.webbangiay.dto.request.UserCreationRequest;
import org.example.webbangiay.dto.request.UserUpdateRequest;
import org.example.webbangiay.entity.User;
import org.example.webbangiay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.creationUser(request));
        return apiResponse;
    }

    @GetMapping("/users")
    public List<UserResponse> getUsers(User user) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        log.info("Roles: {}", user.getRoles());
        authentication.getAuthorities()
                .forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return userService.getUsers()
                .stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }
    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId,request);
    }
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return "user delete has been";
    }
}
