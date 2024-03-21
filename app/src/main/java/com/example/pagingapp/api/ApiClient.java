package com.example.pagingapp.api;

import static com.example.pagingapp.util.Utils.API_KEY;
import static com.example.pagingapp.util.Utils.BASE_URL;

import com.example.pagingapp.model.MovieResult;
import com.example.pagingapp.util.Utils;

import io.reactivex.rxjava3.core.Single;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiClient {

    static ApiInterface apiInterface;
    public static ApiInterface getApiInterface(){

        if(apiInterface == null){
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.addInterceptor(chain -> {
                HttpUrl url = chain.request()
                       .url()
                       .newBuilder()
                       .addQueryParameter("api_key",API_KEY)
                       .build();
                Request request = chain.request().newBuilder().url(url).build();
                return chain.proceed(request);
            });
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            apiInterface = retrofit.create(ApiInterface.class);
        }

        return apiInterface;
    }

    public interface ApiInterface{
        @GET("movie/popular")
        Single<MovieResult> getMoviesByPage(@Query("page") int page);
    }
}
