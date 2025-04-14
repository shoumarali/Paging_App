package com.example.pagingapp.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.example.pagingapp.api.ApiClient;
import com.example.pagingapp.model.Movie;
import com.example.pagingapp.model.MovieResult;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoviePagingSource extends RxPagingSource<Integer, Movie> {


    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Movie> pagingState) {
        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, Movie>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        try{
            int page = loadParams.getKey() != null ? loadParams.getKey() : 1;
            return ApiClient
                    .getApiInterface()
                    .getMoviesByPage(page)
                    .subscribeOn(Schedulers.io())
                    .map(MovieResult::getResults)
                    .map(movies ->  toLoadResult(movies,page))
                    .onErrorReturn(LoadResult.Error::new);
        } catch (Exception error){
            return Single.just(new LoadResult.Error(error));
        }
    }
    private LoadResult<Integer, Movie> toLoadResult(List<Movie> movies, int page){
        return new LoadResult.Page(movies, page == 1? null: page -1, page +1);
    }
}
