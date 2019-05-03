package com.asalcoding.babe;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitRetrofit {
    public static String API_URL = "http://asalcoding.com/babe/";

    public static Retrofit setInit() {
        return new Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getInstance() {
        return setInit().create(ApiService.class);
    }
}