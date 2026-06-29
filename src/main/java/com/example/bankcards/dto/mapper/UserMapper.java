package com.example.bankcards.dto.mapper;

import com.example.bankcards.dto.request.UserRequest;
import com.example.bankcards.dto.response.AuthResponse;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private final CardMapper cardMapper;

    @Autowired
    public UserMapper(CardMapper cardMapper) {
        this.cardMapper = cardMapper;
    }

    public UserResponse toResponse(User user){
        if(user==null) return null;
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().toString())
                .cards(cardMapper.toResponse(user.getCards()))
                .build();
    }

    public User toEntity(UserRequest request){
        if(request==null) return null;

        return User.builder()
                .username(request.getUsername())
                .password(request.getUsername())
                .build();
    }

    public AuthResponse toAuthResponse(User user, String token){
        if(user==null || token==null) return null;

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public List<UserResponse> toResponse(List<User> users){
        return users.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public UserDetails userToUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString()))
        );
    }
}
