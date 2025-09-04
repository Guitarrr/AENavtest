package com.example.navtest.api;

import com.example.navtest.model.YouTubeChannel;
import com.example.navtest.model.YouTubeResponse;
import com.example.navtest.model.YouTubeVideo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeApiService {
    
    @GET("channels")
    Call<YouTubeResponse<YouTubeChannel>> getChannelInfo(
            @Query("part") String part,
            @Query("id") String channelId,
            @Query("key") String apiKey
    );
    
    @GET("search")
    Call<YouTubeResponse<YouTubeVideo>> searchVideos(
            @Query("part") String part,
            @Query("channelId") String channelId,
            @Query("type") String type,
            @Query("order") String order,
            @Query("maxResults") int maxResults,
            @Query("key") String apiKey
    );
    
    @GET("videos")
    Call<YouTubeResponse<YouTubeVideo>> getVideoDetails(
            @Query("part") String part,
            @Query("id") String videoIds,
            @Query("key") String apiKey
    );
}
