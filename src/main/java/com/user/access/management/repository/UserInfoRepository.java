package com.user.access.management.repository;

import com.user.access.management.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);

}
