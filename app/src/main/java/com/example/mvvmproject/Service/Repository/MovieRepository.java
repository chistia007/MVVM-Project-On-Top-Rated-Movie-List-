package com.example.mvvmproject.Service.Repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.mvvmproject.Service.Model.MovieModel;
import com.example.mvvmproject.Service.Model.Result;
import com.example.mvvmproject.Service.Network.ApiServices;
import com.example.mvvmproject.Service.Network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

// will select from where data will come
public class MovieRepository {
    private static Context mcontext;
    private MovieModel movieModel;
    private List<Result> mResult;
    private MutableLiveData mutableLiveData;
    private static  MovieRepository instance;
    public static MovieRepository getInstance(Context context){
        if (instance==null){
            mcontext=context;
            instance=new MovieRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Result>>  getTopRatedMovieLists(){
        if(mutableLiveData==null){
            mutableLiveData=new MutableLiveData();
        }

        ApiServices apiServices= RetrofitInstance.getRetrofitInstance().create(ApiServices.class);
        Call<MovieModel> call= apiServices.getTopRatedMovieList();
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                movieModel=response.body();
                mResult=movieModel.getResults();
                mutableLiveData.postValue(mResult);
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }



}
