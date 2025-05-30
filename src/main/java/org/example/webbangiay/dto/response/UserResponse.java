package org.example.webbangiay.dto.response;

import lombok.*;
import org.example.webbangiay.dto.request.UserCreationRequest;
import org.example.webbangiay.dto.request.UserUpdateRequest;
import org.example.webbangiay.entity.User;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String dob;
    private Set<String> roles;

    public static UserResponse fromEntity(User user) {
        UserResponse res = new UserResponse();

        res.id = user.getId();
        res.username = user.getUsername();
        res.password = user.getPassword();
        res.firstName = user.getFirstName();
        res.lastName = user.getLastName();
        res.dob = user.getDob();
        res.roles = user.getRoles();
        return res;
    }

    public static User toEntity (UserCreationRequest request) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());
        return user;
    }

    public static  UserResponse updateUser(User user, UserUpdateRequest request){
        UserResponse res = new UserResponse();

        res.id = user.getId();
        res.username = user.getUsername();
        res.password = request.getPassword();
        res.firstName = request.getFirstName();
        res.lastName = request.getLastName();
        res.dob = request.getDob();
        return res;
    }

}
