package org.example.webbangiay.service.acccount_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webbangiay.dto.response.UserResponse;
import org.example.webbangiay.dto.request.UserCreationRequest;
import org.example.webbangiay.dto.request.UserUpdateRequest;
import org.example.webbangiay.entity.User;
import org.example.webbangiay.enums.RoleEnums;
import org.example.webbangiay.entity.Role;
import org.example.webbangiay.exception.AppException;
import org.example.webbangiay.exception.ErrorCode;
//import org.example.identityservice.mapper.UserMapper;
import org.example.webbangiay.repository.RoleRepository;
import org.example.webbangiay.repository.UserRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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
        roles.add(RoleEnums.USER.name());
//        user.setRoles(roles);
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

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        Set<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userRepository.save(user);

        return UserResponse.updateUser(user, request, roles);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return UserResponse.fromEntity(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers() {
        log.info("Get all users");
        return userRepository.findAll();
    }

//    public UserResponse getUser(String userId) {
//        User user = new User();
//        System.out.println("User entity: " +user);
//
//        return userMapper.toUserResponse(userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found")));
//    }
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id) {
        log.info("Get user with by id");
        User user = userRepository.findById(id).orElse(null);
        return UserResponse.fromEntity(user);
    }
}
