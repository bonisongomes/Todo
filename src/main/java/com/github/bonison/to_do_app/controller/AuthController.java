package com.github.bonison.to_do_app.controller;

import com.github.bonison.to_do_app.config.JwtUtil;
import com.github.bonison.to_do_app.model.User;
import com.github.bonison.to_do_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user.getUsername(), user.getPassword() , user.getEmail());
        return ResponseEntity.ok("User registered successfully!");
    }
    @PostMapping("/login")
    public String login(@RequestParam String username , @RequestParam String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return "Bearer " + token;
    }
}
