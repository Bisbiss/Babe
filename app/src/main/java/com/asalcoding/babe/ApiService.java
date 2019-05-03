package com.asalcoding.babe;

import com.asalcoding.babe.response.ResponseBerita;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("tampil_berita.php")
    Call<ResponseBerita>request_show_all_berita();
}
