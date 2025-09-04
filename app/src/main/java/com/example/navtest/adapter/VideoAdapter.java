package com.example.navtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.navtest.R;
import com.example.navtest.model.YouTubeVideo;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<YouTubeVideo> videos;
    private Context context;
    
    public VideoAdapter(List<YouTubeVideo> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }
    
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        if (videos != null && position < videos.size()) {
            YouTubeVideo video = videos.get(position);
            holder.bind(video);
        }
    }
    
    @Override
    public int getItemCount() {
        return videos != null ? videos.size() : 0;
    }
    
    public void updateVideos(List<YouTubeVideo> newVideos) {
        this.videos = newVideos;
        notifyDataSetChanged();
    }
    
    class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnailImageView;
        private TextView titleTextView;
        private TextView channelTextView;
        private TextView viewsTextView;
        private TextView publishedAtTextView;
        
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            channelTextView = itemView.findViewById(R.id.channelTextView);
            viewsTextView = itemView.findViewById(R.id.viewsTextView);
            publishedAtTextView = itemView.findViewById(R.id.publishedAtTextView);
        }
        
        public void bind(YouTubeVideo video) {
            // Set title
            titleTextView.setText(video.getSnippet().getTitle());
            
            // Set channel name
            channelTextView.setText(video.getSnippet().getChannelTitle());
            
            // Set views count
            if (video.getStatistics() != null && video.getStatistics().getViewCount() != null) {
                long viewCount = Long.parseLong(video.getStatistics().getViewCount());
                String formattedViews = formatNumber(viewCount) + " views";
                viewsTextView.setText(formattedViews);
            } else {
                viewsTextView.setText("Views not available");
            }
            
            // Set published date
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                Date publishedDate = inputFormat.parse(video.getSnippet().getPublishedAt());
                publishedAtTextView.setText(outputFormat.format(publishedDate));
            } catch (Exception e) {
                publishedAtTextView.setText("Date not available");
            }
            
            // Load thumbnail
            String thumbnailUrl = getBestThumbnailUrl(video);
            if (thumbnailUrl != null) {
                Glide.with(context)
                        .load(thumbnailUrl)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(thumbnailImageView);
            }
            
            // Set click listener to open video in YouTube app
            itemView.setOnClickListener(v -> {
                String videoId = video.getId().getVideoId();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
                if (intent.resolveActivity(context.getPackageManager()) == null) {
                    // Fallback to web browser if YouTube app is not installed
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoId));
                }
                context.startActivity(intent);
            });
        }
        
        private String getBestThumbnailUrl(YouTubeVideo video) {
            YouTubeVideo.Thumbnails thumbnails = video.getSnippet().getThumbnails();
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
}
