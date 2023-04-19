package com.mintusharma.newsapp;


import com.mintusharma.newsapp.models.NewsListResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("top-headlines")
    Call<NewsListResponseModel> getNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey
    );

}
