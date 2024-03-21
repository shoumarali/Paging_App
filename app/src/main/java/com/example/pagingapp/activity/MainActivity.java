package com.example.pagingapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.RequestManager;
import com.example.pagingapp.R;
import com.example.pagingapp.adapter.MovieLoadStateAdapter;
import com.example.pagingapp.adapter.MoviesAdapter;
import com.example.pagingapp.databinding.ActivityMainBinding;
import com.example.pagingapp.util.GridSpace;
import com.example.pagingapp.util.MovieComparator;
import com.example.pagingapp.util.Utils;
import com.example.pagingapp.viewmodel.MovieViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    MovieViewModel movieViewModel;
    ActivityMainBinding activityMainBinding;
    MoviesAdapter moviesAdapter;

    @Inject
    RequestManager requestManager;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        if (Utils.API_KEY == null || Utils.API_KEY.isEmpty()){
            Toast.makeText(this, "Error in API Key", Toast.LENGTH_SHORT).show();
        }

        moviesAdapter = new MoviesAdapter(new MovieComparator(), requestManager);
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        initRecyclerviewAndAdapter();

        movieViewModel.moviePagingDataFlow.subscribe(moviePagingData -> {
            moviesAdapter.submitData(getLifecycle(), moviePagingData);
        });
    }

    private void initRecyclerviewAndAdapter() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        activityMainBinding.recyclerView.setLayoutManager(gridLayoutManager);


        activityMainBinding.recyclerView.addItemDecoration(new GridSpace(2, 20, true));

        activityMainBinding.recyclerView.setAdapter(
                moviesAdapter.withLoadStateFooter(
                        new MovieLoadStateAdapter(view -> {
                            moviesAdapter.retry();
                        })
                )
        );

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return moviesAdapter.getItemViewType(position) == MoviesAdapter.LOADING_ITEM ? 1 : 2;
            }
        });
    }
}