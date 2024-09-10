package com.user.access.management.service;

import com.user.access.management.dto.UserDTO;
import com.user.access.management.model.Users;
import com.user.access.management.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInformationService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userInfo = repository.findByUsername(username);
        return userInfo.map(UserDTO::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));

    }
}
