package com.example.bankcards.service;

import com.example.bankcards.dto.mapper.CardMapper;
import com.example.bankcards.dto.mapper.UserMapper;
import com.example.bankcards.dto.request.RoleRequest;
import com.example.bankcards.dto.request.UserRequest;
import com.example.bankcards.dto.response.AuthResponse;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.Role;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse signUp(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new IllegalArgumentException("Username already exists");

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        User saved = userRepository.save(user);

        String token = jwtService.generateToken(userMapper.userToUserDetails(user));

        return userMapper.toAuthResponse(saved, token);
    }

    @Transactional(readOnly = true)
    public AuthResponse signIn(UserRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = getUserEntity(request.getUsername());
        String token = jwtService.generateToken(userMapper.userToUserDetails(user));

        return userMapper.toAuthResponse(user, token);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userMapper.toResponse(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        return userMapper.toResponse(getUserEntity(id));
    }

    @Transactional(readOnly = true)
    public List<CardResponse> getCardsByUser(Long id) {
        User user = getUserEntity(id);
        return userMapper.toResponse(user).getCards();
    }

    public UserResponse updateRole(Long id, RoleRequest request) {
        User user = getUserEntity(id);
        user.setRole(request.getRole());
        return userMapper.toResponse(userRepository.save(user));
    }

    public void deleteUserById(Long id) {
        User user = getUserEntity(id);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public User getUserEntity(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User entity not found: " + username)
        );
    }

    @Transactional(readOnly = true)
    public User getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User entity not found: " + id)
        );
    }
}
