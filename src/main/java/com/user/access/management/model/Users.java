package com.user.access.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
    private String roles;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
