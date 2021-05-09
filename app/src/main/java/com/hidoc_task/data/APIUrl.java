package com.hidoc_task.data;

import com.hidoc_task.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUrl {

    public static final String KEY_NEWS = "952da71b07954070b1fd98d174b01d11";
    public static final String KEY_BOOKS = "AIzaSyBrjOmNg04DxESQa4_c7HULJeNU9q_Sxmo";
    public static Retrofit retrofit = null,retrofitBook = null;
    private static String BASE_URL_NEWS = "http://newsapi.org/v2/";
    private static String BASE_URL_Books = "https://www.googleapis.com/books/v1/";

    public static Retrofit getClientNews() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.MINUTES)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_NEWS)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientBooks() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.MINUTES)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();
        if (retrofitBook == null) {
            retrofitBook = new Retrofit.Builder()
                    .baseUrl(BASE_URL_Books)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitBook;
    }
}
