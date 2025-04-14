package com.example.pagingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pagingapp.R;
import com.example.pagingapp.databinding.LoadStateItemBinding;

public class MovieLoadStateAdapter extends LoadStateAdapter <MovieLoadStateAdapter.LoadStateViewHolder>{

    private View.OnClickListener retryCallBack;

    public MovieLoadStateAdapter(View.OnClickListener retryCallBack){
        this.retryCallBack = retryCallBack;
    }

    @Override
    public void onBindViewHolder(@NonNull LoadStateViewHolder loadStateViewHolder, @NonNull LoadState loadState) {
        loadStateViewHolder.bind(loadState);
    }

    @NonNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull LoadState loadState) {
        return new LoadStateViewHolder(viewGroup, retryCallBack);
    }

    public static class LoadStateViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        private TextView errorMessage;
        private Button retry;

        LoadStateViewHolder(@NonNull ViewGroup parent, @NonNull View.OnClickListener retryCallBack){
            super(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.load_state_item, parent, false));
            LoadStateItemBinding binding = LoadStateItemBinding.bind(itemView);
            progressBar = binding.progressBar;
            errorMessage = binding.errorMsg;
            retry = binding.retryBtn;
            retry.setOnClickListener(retryCallBack);
        }
        public void bind(LoadState loadState){
            if(loadState instanceof LoadState.Error){
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                errorMessage.setText(loadStateError.getError().getLocalizedMessage());
            }
            progressBar.setVisibility(loadState instanceof LoadState.Loading
                    ? View.VISIBLE
                    : View.GONE
            );
            retry.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE
                    : View.GONE
            );
            errorMessage.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE
                    : View.GONE
            );
        }
    }
}
