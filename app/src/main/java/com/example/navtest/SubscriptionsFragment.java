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

import com.bumptech.glide.Glide;
import com.example.navtest.model.YouTubeChannel;
import com.example.navtest.utils.YouTubeDataManager;

import java.text.NumberFormat;
import java.util.Locale;

public class SubscriptionsFragment extends Fragment {

    private ProgressBar progressBar;
    private View channelInfoView;
    private ImageView channelImageView;
    private TextView channelNameTextView;
    private TextView subscriberCountTextView;
    private TextView videoCountTextView;
    private TextView viewCountTextView;
    private TextView descriptionTextView;
    
    private YouTubeDataManager dataManager;

    public SubscriptionsFragment() {
        // Required empty public constructor
    }

    public static SubscriptionsFragment newInstance() {
        return new SubscriptionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = new YouTubeDataManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscriptions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViews(view);
        loadChannelInfo();
    }
    
    private void initViews(View view) {
        progressBar = view.findViewById(R.id.progressBar);
        channelInfoView = view.findViewById(R.id.channelInfoView);
        channelImageView = view.findViewById(R.id.channelImageView);
        channelNameTextView = view.findViewById(R.id.channelNameTextView);
        subscriberCountTextView = view.findViewById(R.id.subscriberCountTextView);
        videoCountTextView = view.findViewById(R.id.videoCountTextView);
        viewCountTextView = view.findViewById(R.id.viewCountTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
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
    
    private void displayChannelInfo(YouTubeChannel channel) {
        // Set channel name
        channelNameTextView.setText(channel.getSnippet().getTitle());
        
        // Set description
        String description = channel.getSnippet().getDescription();
        if (description != null && !description.isEmpty()) {
            // Truncate description if too long
            if (description.length() > 200) {
                description = description.substring(0, 200) + "...";
            }
            descriptionTextView.setText(description);
        }
        
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