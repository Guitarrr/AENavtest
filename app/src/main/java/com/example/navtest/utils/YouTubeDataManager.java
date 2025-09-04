package com.example.navtest.utils;

import android.util.Log;

import com.example.navtest.api.YouTubeApiClient;
import com.example.navtest.api.YouTubeApiService;
import com.example.navtest.model.YouTubeChannel;
import com.example.navtest.model.YouTubeResponse;
import com.example.navtest.model.YouTubeVideo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YouTubeDataManager {
    private static final String TAG = "YouTubeDataManager";
    private static final String AESPA_CHANNEL_ID = "UCuJcl0Nu9IHrFFi65GEqvKQ"; // Aespa's official channel ID
    
    private YouTubeApiService apiService;
    
    public YouTubeDataManager() {
        apiService = YouTubeApiClient.getInstance().getApiService();
    }
    
    public interface ChannelCallback {
        void onSuccess(YouTubeChannel channel);
        void onError(String error);
    }
    
    public interface VideosCallback {
        void onSuccess(List<YouTubeVideo> videos);
        void onError(String error);
    }
    
    public void getAespaChannelInfo(ChannelCallback callback) {
        Call<YouTubeResponse<YouTubeChannel>> call = apiService.getChannelInfo(
                "snippet,statistics",
                AESPA_CHANNEL_ID,
                YouTubeApiClient.getApiKey()
        );
        
        call.enqueue(new Callback<YouTubeResponse<YouTubeChannel>>() {
            @Override
            public void onResponse(Call<YouTubeResponse<YouTubeChannel>> call, Response<YouTubeResponse<YouTubeChannel>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().getItems().isEmpty()) {
                    YouTubeChannel channel = response.body().getItems().get(0);
                    callback.onSuccess(channel);
                } else {
                    callback.onError("Failed to fetch channel info: " + response.message());
                }
            }
            
            @Override
            public void onFailure(Call<YouTubeResponse<YouTubeChannel>> call, Throwable t) {
                Log.e(TAG, "Error fetching channel info", t);
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    
    public void getAespaVideos(VideosCallback callback) {
        Call<YouTubeResponse<YouTubeVideo>> call = apiService.searchVideos(
                "snippet",
                AESPA_CHANNEL_ID,
                "video",
                "date",
                20, // Get 20 most recent videos
                YouTubeApiClient.getApiKey()
        );
        
        call.enqueue(new Callback<YouTubeResponse<YouTubeVideo>>() {
            @Override
            public void onResponse(Call<YouTubeResponse<YouTubeVideo>> call, Response<YouTubeResponse<YouTubeVideo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<YouTubeVideo> videos = response.body().getItems();
                    // Get detailed video statistics
                    getVideoStatistics(videos, callback);
                } else {
                    callback.onError("Failed to fetch videos: " + response.message());
                }
            }
            
            @Override
            public void onFailure(Call<YouTubeResponse<YouTubeVideo>> call, Throwable t) {
                Log.e(TAG, "Error fetching videos", t);
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    
    private void getVideoStatistics(List<YouTubeVideo> videos, VideosCallback callback) {
        if (videos.isEmpty()) {
            callback.onSuccess(videos);
            return;
        }
        
        // Build comma-separated list of video IDs
        StringBuilder videoIds = new StringBuilder();
        for (int i = 0; i < videos.size(); i++) {
            if (i > 0) videoIds.append(",");
            videoIds.append(videos.get(i).getId().getVideoId());
        }
        
        Call<YouTubeResponse<YouTubeVideo>> call = apiService.getVideoDetails(
                "statistics",
                videoIds.toString(),
                YouTubeApiClient.getApiKey()
        );
        
        call.enqueue(new Callback<YouTubeResponse<YouTubeVideo>>() {
            @Override
            public void onResponse(Call<YouTubeResponse<YouTubeVideo>> call, Response<YouTubeResponse<YouTubeVideo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<YouTubeVideo> detailedVideos = response.body().getItems();
                    
                    // Merge statistics with original videos
                    for (YouTubeVideo originalVideo : videos) {
                        for (YouTubeVideo detailedVideo : detailedVideos) {
                            if (originalVideo.getId().getVideoId().equals(detailedVideo.getId().getVideoId())) {
                                originalVideo.setStatistics(detailedVideo.getStatistics());
                                break;
                            }
                        }
                    }
                    
                    callback.onSuccess(videos);
                } else {
                    // Return videos without statistics if detailed fetch fails
                    callback.onSuccess(videos);
                }
            }
            
            @Override
            public void onFailure(Call<YouTubeResponse<YouTubeVideo>> call, Throwable t) {
                Log.e(TAG, "Error fetching video statistics", t);
                // Return videos without statistics if detailed fetch fails
                callback.onSuccess(videos);
            }
        });
    }
}
