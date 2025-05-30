package org.example.webbangiay.service;

import org.example.webbangiay.dto.response.UserResponse;
import org.example.webbangiay.dto.request.UserCreationRequest;
import org.example.webbangiay.dto.request.UserUpdateRequest;
import org.example.webbangiay.entity.User;
import org.example.webbangiay.enums.Role;
import org.example.webbangiay.exception.AppException;
import org.example.webbangiay.exception.ErrorCode;
//import org.example.identityservice.mapper.UserMapper;
import org.example.webbangiay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private UserMapper userMapper;

//    public User creatUser(UserCreationRequest request) {
//        if (userRepository.existsByUsername(request.getUsername())) {
//            throw new AppException(ErrorCode.USER_EXISTED);
//        }
//        User user = userMapper.toUser(request);
//
//        return userRepository.save(user);
//    }

    public UserResponse creationUser (UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = UserResponse.toEntity(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        userRepository.save(user);
        return UserResponse.fromEntity(user);
    }

//    public UserResponse upateUser(String userId, UserUpdateRequest request) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        userMapper.updateUser(user, request);
//        return userMapper.toUserResponse(userRepository.save(user));
//    }


    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElse(null);
        return UserResponse.updateUser(user,request);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

//    public UserResponse getUser(String userId) {
//        User user = new User();
//        System.out.println("User entity: " +user);
//
//        return userMapper.toUserResponse(userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found")));
//    }

    public UserResponse getUser(String id) {
        User user = userRepository.findById(id).orElse(null);
        return UserResponse.fromEntity(user);
    }
}
