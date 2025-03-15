package com.github.bonison.to_do_app.service;


import com.github.bonison.to_do_app.model.Role;
import com.github.bonison.to_do_app.model.User;
import com.github.bonison.to_do_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private User user;

    public User registerUser(String username, String password, String email) {
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Hash password
        user.setEmail(email);
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public User registerAdminUser(String username, String password, String email){
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}

