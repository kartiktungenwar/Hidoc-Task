package com.hidoc_task.data;

import com.hidoc_task.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIService {

    //The news API
    @Headers(
            "Content-Type:application/x-www-form-urlencoded"
    )
    @GET("top-headlines")
    Call<ResponseModel> newsAPI(
            @Query("apiKey") String apiKey,
            @Query("country") String country);

    //The books API
    @Headers(
            "Content-Type:application/x-www-form-urlencoded"
    )
    @GET("volumes")
    Call<ResponseModel> booksAPI(
            @Query("key") String apiKey,
            @Query("q") String search);
}
