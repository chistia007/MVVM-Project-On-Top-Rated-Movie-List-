package com.example.mvvmproject.VIew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mvvmproject.R;
import com.example.mvvmproject.Service.Model.Result;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder>{
    private Context context;
    private List<Result> mList;

    public MovieListAdapter(Context context, List<Result> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_single_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(mList.get(position).getTitle());
        holder.rating.setText(mList.get(position).getVoteAverage().toString());
        holder.release.setText(mList.get(position).getReleaseDate());

        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+mList.get(position).getPosterPath()).into(holder.imageVIew);

    }

    @Override
    public int getItemCount() {
        if (this.mList!=null){
            return mList.size();
        }
        else return 0;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageVIew;
        TextView title, rating, release;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.txtTitle);
            rating=itemView.findViewById(R.id.txtRating);
            release=itemView.findViewById(R.id.txtReleaseDate);
            imageVIew=itemView.findViewById(R.id.imageView);
        }
    }

}
