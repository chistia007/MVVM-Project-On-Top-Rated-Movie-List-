package com.example.mvvmproject.Service.Network;

import com.example.mvvmproject.Service.Model.MovieModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {

    @GET("3/movie/top_rated?api_key=76c8e7a12e52987276dc0e9acfc860a1")
    Call<MovieModel> getTopRatedMovieList();
}
