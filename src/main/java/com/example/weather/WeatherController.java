package com.example.weather;

import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.net.*;

@RestController
public class WeatherController {

    private static final String API_KEY = "89a87b5198a60e38fc56e98d08e1e0d6";

    @GetMapping("/weather")
    public String getWeather(@RequestParam("city") String city) {

        try {
            String encodedCity = URLEncoder.encode(city, "UTF-8");
            String urlString =
            "https://api.openweathermap.org/data/2.5/weather?q="
            + encodedCity + "&appid=" + API_KEY + "&units=metric";

            URL url = new URL(urlString);

            HttpURLConnection conn =
            (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            
            if (responseCode == 200) {
                BufferedReader reader =
                new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                return response.toString();
            } else {
                return "{\"error\": \"City not found or API error\", \"status\": " + responseCode + "}";
            }

        } catch (Exception e) {
            return "{\"error\": \"Error fetching weather data: " + e.getMessage() + "\"}";
        }
    }
}