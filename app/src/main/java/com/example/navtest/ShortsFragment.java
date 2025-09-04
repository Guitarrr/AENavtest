package com.example.navtest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navtest.adapter.VideoAdapter;
import com.example.navtest.model.YouTubeVideo;
import com.example.navtest.utils.YouTubeDataManager;

import java.util.ArrayList;
import java.util.List;

public class ShortsFragment extends Fragment {

    private RecyclerView shortsRecyclerView;
    private VideoAdapter shortsAdapter;
    private ProgressBar progressBar;
    private TextView titleTextView;
    
    private YouTubeDataManager dataManager;

    public ShortsFragment() {
        // Required empty public constructor
    }

    public static ShortsFragment newInstance() {
        return new ShortsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = new YouTubeDataManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shorts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViews(view);
        setupRecyclerView();
        loadShorts();
    }
    
    private void initViews(View view) {
        shortsRecyclerView = view.findViewById(R.id.shortsRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        titleTextView = view.findViewById(R.id.titleTextView);
    }
    
    private void setupRecyclerView() {
        shortsAdapter = new VideoAdapter(new ArrayList<>(), getContext());
        shortsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shortsRecyclerView.setAdapter(shortsAdapter);
    }
    
    private void loadShorts() {
        // For now, we'll load regular videos as "shorts"
        // In a real implementation, you might want to filter for actual YouTube Shorts
        dataManager.getAespaVideos(new YouTubeDataManager.VideosCallback() {
            @Override
            public void onSuccess(List<YouTubeVideo> videos) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        shortsAdapter.updateVideos(videos);
                        progressBar.setVisibility(View.GONE);
                        titleTextView.setText("Aespa Shorts and Videos");
                    });
                }
            }

            @Override
            public void onError(String error) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Error loading shorts: " + error, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    });
                }
            }
        });
    }
}