package com.anahit.movieplace.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anahit.movieplace.adapters.MyMovieRecyclerViewAdapter;
import com.anahit.movieplace.R;
import com.anahit.movieplace.models.Movie;
import com.anahit.movieplace.remote.IMovieAPI;
import com.anahit.movieplace.remote.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 */
public class MovieFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 3;
    private  Context context;
    private  List<Movie> movies;
    private final String username;
    private static MovieFragment INSTANCE;

    MyMovieRecyclerViewAdapter adapter;
    public static MovieFragment getINSTANCE() {
        return INSTANCE;
    }
    public static void setINSTANCE(MovieFragment INSTANCE) {
        MovieFragment.INSTANCE = INSTANCE;
    }


    public MovieFragment(Context context,List<Movie> movies,String username) {
        this.context=context;
        this.movies=movies;
        this.username=username;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_item_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyMovieRecyclerViewAdapter(movies, context, username);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    public void addMovieIntoListAndNotify(Movie movie){
        movies.add(movie);
        adapter.notifyDataSetChanged();
    }

    public void UpdateMovieList(){
        IMovieAPI iMovieAPI = RetrofitClient.getInstance().create(IMovieAPI.class);
        final Type listTypeMovie = new TypeToken<List<Movie>>() {}.getType();

        Call<String> callMovie= iMovieAPI.getMovies();
        callMovie.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                movies= new Gson().fromJson(response.body(),listTypeMovie);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}