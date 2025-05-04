package com.github.bonison.to_do_app.service;

import com.github.bonison.to_do_app.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Autowired
    RestTemplate restTemplate;

    private static final String token = "e405d7281df094e5dcc9da9b08176644";
    private static final String url = "http://api.weatherstack.com/current?access_key=";

    public WeatherResponse getWeather(String city){
        String completeURL = url + token + "&query=" + city;
        ResponseEntity<WeatherResponse> response =
                restTemplate.exchange(completeURL, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();

    }
}
