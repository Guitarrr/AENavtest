package com.example.navtest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.navtest.adapter.VideoAdapter;
import com.example.navtest.model.YouTubeChannel;
import com.example.navtest.model.YouTubeVideo;
import com.example.navtest.utils.YouTubeDataManager;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private RecyclerView videosRecyclerView;
    private VideoAdapter videoAdapter;
    private ProgressBar progressBar;
    private View channelInfoView;
    private ImageView channelImageView;
    private TextView channelNameTextView;
    private TextView subscriberCountTextView;
    private TextView videoCountTextView;
    private TextView viewCountTextView;
    
    private YouTubeDataManager dataManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = new YouTubeDataManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViews(view);
        setupRecyclerView();
        loadChannelInfo();
        loadVideos();
    }
    
    private void initViews(View view) {
        videosRecyclerView = view.findViewById(R.id.videosRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        channelInfoView = view.findViewById(R.id.channelInfoView);
        channelImageView = view.findViewById(R.id.channelImageView);
        channelNameTextView = view.findViewById(R.id.channelNameTextView);
        subscriberCountTextView = view.findViewById(R.id.subscriberCountTextView);
        videoCountTextView = view.findViewById(R.id.videoCountTextView);
        viewCountTextView = view.findViewById(R.id.viewCountTextView);
    }
    
    private void setupRecyclerView() {
        videoAdapter = new VideoAdapter(null, getContext());
        videosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        videosRecyclerView.setAdapter(videoAdapter);
    }
    
    private void loadChannelInfo() {
        dataManager.getAespaChannelInfo(new YouTubeDataManager.ChannelCallback() {
            @Override
            public void onSuccess(YouTubeChannel channel) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        displayChannelInfo(channel);
                        progressBar.setVisibility(View.GONE);
                    });
                }
            }

            @Override
            public void onError(String error) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Error loading channel info: " + error, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    });
                }
            }
        });
    }
    
    private void loadVideos() {
        dataManager.getAespaVideos(new YouTubeDataManager.VideosCallback() {
            @Override
            public void onSuccess(List<YouTubeVideo> videos) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        videoAdapter.updateVideos(videos);
                    });
                }
            }

            @Override
            public void onError(String error) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Error loading videos: " + error, Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }
    
    private void displayChannelInfo(YouTubeChannel channel) {
        // Set channel name
        channelNameTextView.setText(channel.getSnippet().getTitle());
        
        // Set subscriber count
        if (channel.getStatistics() != null && channel.getStatistics().getSubscriberCount() != null) {
            long subscriberCount = Long.parseLong(channel.getStatistics().getSubscriberCount());
            String formattedSubscribers = formatNumber(subscriberCount) + " subscribers";
            subscriberCountTextView.setText(formattedSubscribers);
        }
        
        // Set video count
        if (channel.getStatistics() != null && channel.getStatistics().getVideoCount() != null) {
            long videoCount = Long.parseLong(channel.getStatistics().getVideoCount());
            String formattedVideos = NumberFormat.getInstance().format(videoCount) + " videos";
            videoCountTextView.setText(formattedVideos);
        }
        
        // Set view count
        if (channel.getStatistics() != null && channel.getStatistics().getViewCount() != null) {
            long viewCount = Long.parseLong(channel.getStatistics().getViewCount());
            String formattedViews = formatNumber(viewCount) + " total views";
            viewCountTextView.setText(formattedViews);
        }
        
        // Load channel thumbnail
        String thumbnailUrl = getBestThumbnailUrl(channel);
        if (thumbnailUrl != null) {
            Glide.with(this)
                    .load(thumbnailUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(channelImageView);
        }
        
        channelInfoView.setVisibility(View.VISIBLE);
    }
    
    private String getBestThumbnailUrl(YouTubeChannel channel) {
        YouTubeChannel.Thumbnails thumbnails = channel.getSnippet().getThumbnails();
        if (thumbnails.getHigh() != null) {
            return thumbnails.getHigh().getUrl();
        } else if (thumbnails.getMedium() != null) {
            return thumbnails.getMedium().getUrl();
        } else if (thumbnails.getDefaultThumbnail() != null) {
            return thumbnails.getDefaultThumbnail().getUrl();
        }
        return null;
    }
    
    private String formatNumber(long number) {
        if (number >= 1000000000) {
            return String.format(Locale.getDefault(), "%.1fB", number / 1000000000.0);
        } else if (number >= 1000000) {
            return String.format(Locale.getDefault(), "%.1fM", number / 1000000.0);
        } else if (number >= 1000) {
            return String.format(Locale.getDefault(), "%.1fK", number / 1000.0);
        } else {
            return NumberFormat.getInstance().format(number);
        }
    }
}