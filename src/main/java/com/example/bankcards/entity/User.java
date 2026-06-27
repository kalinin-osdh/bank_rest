package com.example.bankcards.entity;

import com.example.bankcards.entity.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be null")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20")
    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @NotBlank(message = "Password cannot be null")
    @Size(min = 8, max = 255, message = "Username must be at least 8")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "Role cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    @Builder.Default
    private Role role = Role.USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Card> cards = new ArrayList<>();
}
