package com.user.access.management.service;

import com.user.access.management.model.Users;
import com.user.access.management.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.user.access.management.repository.UserCombinatorValidationService.ValidationResult;
import com.user.access.management.repository.UserCombinatorValidationService.ValidationResult.*;

import static com.user.access.management.repository.UserCombinatorValidationService.isEmailValid;
import static com.user.access.management.repository.UserCombinatorValidationService.isPhoneNumberValid;

@Service
public class UserService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Users> getUsers() {
        return repository.findAll();
    }

    public String add(Users user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            ValidationResult result = isEmailValid()
                    .and(isPhoneNumberValid()).apply(user);
            Optional.ofNullable(result)
                    .ifPresent(value -> {
                        if(value != ValidationResult.SUCCESSFULLY_VALID){
                            throw new IllegalStateException(result.name());
                        }
                    });
            repository.save(user);
            return "User Added to the System";
        } catch (IllegalStateException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String deleteUser(int id) {
        repository.deleteById(id);
        return "Successfully deleted user with id " + String.valueOf(id);
    }
}
