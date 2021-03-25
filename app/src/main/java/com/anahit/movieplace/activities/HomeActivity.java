package com.anahit.movieplace.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.GoalRow;

import com.anahit.movieplace.R;
import com.anahit.movieplace.adapters.MyMovieActorAdapter;
import com.anahit.movieplace.fragments.ActorsFragment;
import com.anahit.movieplace.fragments.MovieFragment;
import com.anahit.movieplace.fragments.MyPageFragment;
import com.anahit.movieplace.fragments.SetupFragment;
import com.anahit.movieplace.models.Actor;
import com.anahit.movieplace.models.Movie;
import com.anahit.movieplace.models.tbIUser;
import com.anahit.movieplace.remote.IActorAPI;
import com.anahit.movieplace.remote.IMovieAPI;
import com.anahit.movieplace.remote.IMovieUserCastAPI;
import com.anahit.movieplace.remote.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private List<Movie> movies;
    private List<Movie> favoriteMovies;
    MovieFragment movieFragment;
    MovieFragment favoriteMovieFragment;
    tbIUser user;
    String userStr;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        initValues();
        getMovies();

        findViewById(R.id.navigation_movies).setOnClickListener(v -> {
            if(HomeActivity.this.user.getRole()==1){
                findViewById(R.id.add_movie).setVisibility(View.VISIBLE);
            }
            bottomNavigationView.setSelectedItemId(R.id.navigation_movies);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, movieFragment).commit();
        });

        findViewById(R.id.navigation_setup).setOnClickListener(v -> {
            findViewById(R.id.add_movie).setVisibility(View.GONE);
            bottomNavigationView.setSelectedItemId(R.id.navigation_setup);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new SetupFragment(HomeActivity.this, HomeActivity.this.user)).commit();
        });

        findViewById(R.id.navigation_favorites).setOnClickListener(v -> {
            findViewById(R.id.add_movie).setVisibility(View.GONE);
            bottomNavigationView.setSelectedItemId(R.id.navigation_favorites);
            getFavoriteMovies();

        });

        findViewById(R.id.navigation_my_page).setOnClickListener(v -> {
            findViewById(R.id.add_movie).setVisibility(View.GONE);
            bottomNavigationView.setSelectedItemId(R.id.navigation_my_page);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyPageFragment(HomeActivity.this.user,HomeActivity.this)).commit();
        });

        findViewById(R.id.add_movie).setOnClickListener(v -> {
            Intent intent1 = new Intent(HomeActivity.this, AddMovieActivity.class);
            intent1.putExtra("user",userStr);
            startActivity(intent1);
        });
    }

    private void getMovies(){

        IMovieAPI iMovieAPI = RetrofitClient.getInstance().create(IMovieAPI.class);
        final Type listTypeMovie = new TypeToken<List<Movie>>() {}.getType();

        Call<String> callMovie= iMovieAPI.getMovies();
        callMovie.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                movies= new Gson().fromJson(response.body(),listTypeMovie);
                movieFragment = new MovieFragment(HomeActivity.this, movies,user.getUsername());
                MovieFragment.setINSTANCE(movieFragment);
                bottomNavigationView.setSelectedItemId(R.id.navigation_movies);
                getSupportFragmentManager().beginTransaction().add(R.id.container,movieFragment).commit();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(HomeActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getFavoriteMovies(){

        IMovieUserCastAPI iMovieUserCastAPI = RetrofitClient.getInstance().create(IMovieUserCastAPI.class);
        final Type listTypeMovieUser = new TypeToken<List<Movie>>() {}.getType();

        Call<String> callMovieUser= iMovieUserCastAPI.getMovieUserCast(user.getUsername());
        callMovieUser.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                favoriteMovies= new Gson().fromJson(response.body(),listTypeMovieUser);
                favoriteMovieFragment = new MovieFragment(HomeActivity.this, favoriteMovies,user.getUsername());
                getSupportFragmentManager().beginTransaction().replace(R.id.container,favoriteMovieFragment).commit();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(HomeActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initValues(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        Intent intent = getIntent();
        userStr = intent.getStringExtra("user");
        user= new Gson().fromJson(userStr, tbIUser.class);

        if(user.getRole()==2){
            findViewById(R.id.add_movie).setVisibility(View.GONE);
            bottomNavigationView.getMenu().findItem(R.id.navigation_setup).setVisible(false);
        }
        SharedPreferences preferences = getSharedPreferences("myPref",MODE_PRIVATE);
        boolean isCommited = preferences.edit().putString("user", new Gson().toJson(this.user)).commit();
    }

}
