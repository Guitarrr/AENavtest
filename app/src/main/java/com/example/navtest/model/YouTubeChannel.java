package com.example.navtest.model;

import com.google.gson.annotations.SerializedName;

public class YouTubeChannel {
    @SerializedName("id")
    private String id;
    
    @SerializedName("snippet")
    private ChannelSnippet snippet;
    
    @SerializedName("statistics")
    private ChannelStatistics statistics;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChannelSnippet getSnippet() {
        return snippet;
    }

    public void setSnippet(ChannelSnippet snippet) {
        this.snippet = snippet;
    }

    public ChannelStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(ChannelStatistics statistics) {
        this.statistics = statistics;
    }

    public static class ChannelSnippet {
        @SerializedName("title")
        private String title;
        
        @SerializedName("description")
        private String description;
        
        @SerializedName("thumbnails")
        private Thumbnails thumbnails;
        
        @SerializedName("publishedAt")
        private String publishedAt;

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
    }

    public static class ChannelStatistics {
        @SerializedName("viewCount")
        private String viewCount;
        
        @SerializedName("subscriberCount")
        private String subscriberCount;
        
        @SerializedName("videoCount")
        private String videoCount;

        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }

        public String getSubscriberCount() {
            return subscriberCount;
        }

        public void setSubscriberCount(String subscriberCount) {
            this.subscriberCount = subscriberCount;
        }

        public String getVideoCount() {
            return videoCount;
        }

        public void setVideoCount(String videoCount) {
            this.videoCount = videoCount;
        }
    }

    public static class Thumbnails {
        @SerializedName("default")
        private Thumbnail defaultThumbnail;
        
        @SerializedName("medium")
        private Thumbnail medium;
        
        @SerializedName("high")
        private Thumbnail high;

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
