package com.example.navtest.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YouTubeApiClient {
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    private static final String API_KEY = "AIzaSyD8l39hIjjiluJYFWBuzHxfPTqdh3tmSeU"; // Replace with your actual API key
    
    private static YouTubeApiClient instance;
    private YouTubeApiService apiService;
    
    private YouTubeApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        apiService = retrofit.create(YouTubeApiService.class);
    }
    
    public static synchronized YouTubeApiClient getInstance() {
        if (instance == null) {
            instance = new YouTubeApiClient();
        }
        return instance;
    }
    
    public YouTubeApiService getApiService() {
        return apiService;
    }
    
    public static String getApiKey() {
        return API_KEY;
    }
}
