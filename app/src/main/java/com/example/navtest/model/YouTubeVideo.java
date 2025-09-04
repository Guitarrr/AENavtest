package com.example.navtest.model;

import com.google.gson.annotations.SerializedName;

public class YouTubeVideo {
    @SerializedName("id")
    private VideoId id;
    
    @SerializedName("snippet")
    private VideoSnippet snippet;
    
    @SerializedName("statistics")
    private VideoStatistics statistics;

    public VideoId getId() {
        return id;
    }

    public void setId(VideoId id) {
        this.id = id;
    }

    public VideoSnippet getSnippet() {
        return snippet;
    }

    public void setSnippet(VideoSnippet snippet) {
        this.snippet = snippet;
    }

    public VideoStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(VideoStatistics statistics) {
        this.statistics = statistics;
    }

    public static class VideoId {
        @SerializedName("videoId")
        private String videoId;

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }
    }

    public static class VideoSnippet {
        @SerializedName("title")
        private String title;
        
        @SerializedName("description")
        private String description;
        
        @SerializedName("thumbnails")
        private Thumbnails thumbnails;
        
        @SerializedName("publishedAt")
        private String publishedAt;
        
        @SerializedName("channelTitle")
        private String channelTitle;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Thumbnails getThumbnails() {
            return thumbnails;
        }

        public void setThumbnails(Thumbnails thumbnails) {
            this.thumbnails = thumbnails;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getChannelTitle() {
            return channelTitle;
        }

        public void setChannelTitle(String channelTitle) {
            this.channelTitle = channelTitle;
        }
    }

    public static class VideoStatistics {
        @SerializedName("viewCount")
        private String viewCount;
        
        @SerializedName("likeCount")
        private String likeCount;
        
        @SerializedName("commentCount")
        private String commentCount;

        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }

        public String getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(String likeCount) {
            this.likeCount = likeCount;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(String commentCount) {
            this.commentCount = commentCount;
        }
    }

    public static class Thumbnails {
        @SerializedName("default")
        private Thumbnail defaultThumbnail;
        
        @SerializedName("medium")
        private Thumbnail medium;
        
        @SerializedName("high")
        private Thumbnail high;
        
        @SerializedName("standard")
        private Thumbnail standard;
        
        @SerializedName("maxres")
        private Thumbnail maxres;

        public Thumbnail getDefaultThumbnail() {
            return defaultThumbnail;
        }

        public void setDefaultThumbnail(Thumbnail defaultThumbnail) {
            this.defaultThumbnail = defaultThumbnail;
        }

        public Thumbnail getMedium() {
            return medium;
        }

        public void setMedium(Thumbnail medium) {
            this.medium = medium;
        }

        public Thumbnail getHigh() {
            return high;
        }

        public void setHigh(Thumbnail high) {
            this.high = high;
        }

        public Thumbnail getStandard() {
            return standard;
        }

        public void setStandard(Thumbnail standard) {
            this.standard = standard;
        }

        public Thumbnail getMaxres() {
            return maxres;
        }

        public void setMaxres(Thumbnail maxres) {
            this.maxres = maxres;
        }
    }

    public static class Thumbnail {
        @SerializedName("url")
        private String url;
        
        @SerializedName("width")
        private int width;
        
        @SerializedName("height")
        private int height;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
