package com.github.bonison.to_do_app.service;


import com.github.bonison.to_do_app.model.Role;
import com.github.bonison.to_do_app.model.User;
import com.github.bonison.to_do_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Hash password
        user.setEmail(email);
        user.setRole(Role.USER);
        return userRepository.save(user);
    }
}

