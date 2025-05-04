package com.github.bonison.to_do_app.controller;

import com.github.bonison.to_do_app.config.JwtUtil;
import com.github.bonison.to_do_app.model.User;
import com.github.bonison.to_do_app.model.WeatherResponse;
import com.github.bonison.to_do_app.service.UserService;
import com.github.bonison.to_do_app.service.WeatherService;
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

    @Autowired
    private WeatherService weatherService;

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

    @GetMapping("/weather")
    public String getWeather(@RequestParam String city){
        String feelsLike ="";
        String humidity = "";
        WeatherResponse weatherResp = weatherService.getWeather(city);
        if(weatherResp != null){
            feelsLike = weatherResp.getCurrent().getFeelsLike();
            humidity = weatherResp.getCurrent().getHumidity();
        }
        return "Weather for city " + city + " feels like " + feelsLike + " and humidity is " + humidity;
    }
}
