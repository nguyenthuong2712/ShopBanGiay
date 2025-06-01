package org.example.webbangiay.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.webbangiay.dto.response.UserResponse;
import org.example.webbangiay.dto.response.ApiResponse;
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
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.creationUser(request));
        return apiResponse;
    }

    @GetMapping("/users")
    public List<UserResponse> getUsers(User user) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities()
                .forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return userService.getUsers()
                .stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{userId}")
    public ApiResponse <UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse <UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    public ApiResponse <UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.updateUser(userId, request))
                .build();
    }
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return "user delete has been";
    }
}
