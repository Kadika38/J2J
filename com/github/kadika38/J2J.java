package com.github.kadika38;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class J2J {
    
    public J2J() {
        
    }

    public static void convert(String json) {
        System.out.println(json);
        try {
            Bucketv1 bucket = new Bucketv1(json);
        bucket.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public String getJson() {
        String url = "https://api.nasa.gov/insight_weather/?api_key=DEMO_KEY&feedtype=json&ver=1.0";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .header("Content-Type", "application/json")
            .uri(URI.create(url))
            .build(); 
        
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{}";
        }
        
    }
}