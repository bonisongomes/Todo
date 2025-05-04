package com.github.bonison.to_do_app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {
    private Current current;

    @Getter
    @Setter
    public class Current{
        @JsonProperty("feelslike")
        private String feelsLike;

        @JsonProperty("humidity")
        private String humidity;
    }
}
