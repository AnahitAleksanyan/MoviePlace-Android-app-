package com.anahit.movieplace.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anahit.movieplace.R;
import com.anahit.movieplace.activities.MovieIndividualPageActivity;
import com.anahit.movieplace.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyMovieRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder> {

    private final List<Movie> movies;
    private final Context context;
    private final String username;

    public MyMovieRecyclerViewAdapter(List<Movie> movies, Context context, String username) {
        this.movies = movies;
        this.context=context;
        this.username = username;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = movies.get(position);
        holder.mContentView.setText(movies.get(position).getName());
        Picasso.get().load(movies.get(position).getImageURL()!=null?movies.get(position).getImageURL():"https://image.freepik.com/free-vector/seamless-various-film-cinema-graphics-light-blue-background-design_1284-42060.jpg").into(holder.movieItemImage);
        holder.movieItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieIndividualPageActivity.class);
            intent.putExtra("movieId",movies.get(position).getId());
            intent.putExtra("username",username);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return  movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final View movieItem;
        public final ImageView movieItemImage;
        public Movie mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            movieItem=view.findViewById(R.id.movie_item);
            movieItemImage=view.findViewById(R.id.movie_item_image);
            mContentView = view.findViewById(R.id.content);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}