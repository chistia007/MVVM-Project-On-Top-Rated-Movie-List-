package com.example.mvvmproject.VIew.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.mvvmproject.R;
import com.example.mvvmproject.Service.Model.Result;
import com.example.mvvmproject.VIew.Adapter.MovieListAdapter;
import com.example.mvvmproject.VIewModel.MovieListViewModel;
import com.example.mvvmproject.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private MovieListViewModel movieListViewModel;
    private RecyclerView recyclerView;
    private MovieListAdapter movieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView=findViewById(R.id.recView);

        GridLayoutManager LayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(LayoutManager);

        movieListViewModel=new ViewModelProvider(this).get(MovieListViewModel.class);
        movieListViewModel.getTopRatedMovieLists().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                movieListAdapter=new MovieListAdapter(MainActivity.this,results);
                recyclerView.setAdapter(movieListAdapter);
            }
        });


    }
}