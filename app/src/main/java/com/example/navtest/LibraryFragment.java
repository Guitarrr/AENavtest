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

import java.util.List;

public class LibraryFragment extends Fragment {

    private RecyclerView libraryRecyclerView;
    private VideoAdapter libraryAdapter;
    private ProgressBar progressBar;
    private TextView titleTextView;
    
    private YouTubeDataManager dataManager;

    public LibraryFragment() {
        // Required empty public constructor
    }

    public static LibraryFragment newInstance() {
        return new LibraryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = new YouTubeDataManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViews(view);
        setupRecyclerView();
        loadLibraryContent();
    }
    
    private void initViews(View view) {
        libraryRecyclerView = view.findViewById(R.id.libraryRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        titleTextView = view.findViewById(R.id.titleTextView);
    }
    
    private void setupRecyclerView() {
        libraryAdapter = new VideoAdapter(null, getContext());
        libraryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        libraryRecyclerView.setAdapter(libraryAdapter);
    }
    
    private void loadLibraryContent() {
        // Load Aespa videos for the library section
        dataManager.getAespaVideos(new YouTubeDataManager.VideosCallback() {
            @Override
            public void onSuccess(List<YouTubeVideo> videos) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        libraryAdapter.updateVideos(videos);
                        progressBar.setVisibility(View.GONE);
                        titleTextView.setText("Aespa Library");
                    });
                }
            }

            @Override
            public void onError(String error) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Error loading library content: " + error, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    });
                }
            }
        });
    }
}