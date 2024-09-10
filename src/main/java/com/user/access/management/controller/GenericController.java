package com.user.access.management.controller;

import com.user.access.management.dto.AuthRequest;
import com.user.access.management.model.Users;
import com.user.access.management.service.JwtService;
import com.user.access.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system")
public class GenericController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/greet")
    public String welcome() {
        return "Welcome user access management system";
    }

    @PostMapping("/authenticate")
    public String authenticat(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid User!");
        }
    }

    @PostMapping("/addAdmin")
    public String addFirstAdmin(@RequestBody Users user){
        return service.add(user);
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addNewUser(@RequestBody Users user){
        return service.add(user);
    }

    @GetMapping("/fetchAll")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Users> getAllUsers() {
        return service.getUsers();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteUser(@PathVariable("id") int id) {
        return service.deleteUser(id);
    }
}
