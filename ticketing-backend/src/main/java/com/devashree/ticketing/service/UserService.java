package com.devashree.ticketing.service;

import com.devashree.ticketing.entity.Role;
import com.devashree.ticketing.entity.User;
import com.devashree.ticketing.repository.UserRepository;
import com.devashree.ticketing.dto.UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }
    public User createUser(UserRequest request){
        User user=new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole()));

        return userRepository.save(user);

    }
}
