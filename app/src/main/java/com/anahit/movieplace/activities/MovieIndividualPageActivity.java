package com.anahit.movieplace.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.anahit.movieplace.R;
import com.anahit.movieplace.adapters.MyMovieActorAdapter;
import com.anahit.movieplace.fragments.MovieFragment;
import com.anahit.movieplace.models.Actor;
import com.anahit.movieplace.models.Actor2;
import com.anahit.movieplace.models.Movie;
import com.anahit.movieplace.models.MovieUserCast;
import com.anahit.movieplace.models.tbIUser;
import com.anahit.movieplace.remote.IMovieAPI;
import com.anahit.movieplace.remote.IMovieActorCastAPI;
import com.anahit.movieplace.remote.IMovieUserCastAPI;
import com.anahit.movieplace.remote.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieIndividualPageActivity extends AppCompatActivity {

    Movie movie;
    TextView name;
    TextView createdDate;
    TextView startDate;
    TextView budget;
    TextView length;
    TextView category;
    TextView country;
    TextView company;
    TextView producer;
    TextView description;
    ListView actorsList;
    List<Actor2> actors;
    ImageView favoriteMakeBtn;
    ImageView favoriteMakedBtn;
    ImageView movieImage;
    int movieUserCastId;
    View deleteMovie;
    int movieId;
    int[] stars = new int[]{R.id.star_1, R.id.star_2, R.id.star_3, R.id.star_4, R.id.star_5};
    String username;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_individual_page);

        initValues();


        checkAndChangeItemsVisibility();


        deleteMovie.setOnClickListener(v -> Toast.makeText(MovieIndividualPageActivity.this, "To delete movie press longer.", Toast.LENGTH_SHORT).show());
        deleteMovie.setOnLongClickListener(v -> {
            deleteMovie();
            return true;
        });

        getMovieDetails();
    }

    public void deleteMovie() {
        IMovieAPI iMovieAPI = RetrofitClient.getInstance().create(IMovieAPI.class);

        Call<String> callMovie = iMovieAPI.deleteMovie(movieId);
        callMovie.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(MovieIndividualPageActivity.this, "Movie was been deleted.", Toast.LENGTH_LONG).show();
                MovieFragment.getINSTANCE().UpdateMovieList();
                Intent intent1 = new Intent(MovieIndividualPageActivity.this, HomeActivity.class);
                SharedPreferences preferences = getSharedPreferences("myPref", MODE_PRIVATE);
                String user = preferences.getString("user", "");
                intent1.putExtra("user", user);
                startActivity(intent1);
                closeActivity();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void closeActivity() {
        this.finish();
    }

    private void initValues() {
        Intent intent = getIntent();
        movieId = intent.getIntExtra("movieId", 0);
        username = intent.getStringExtra("username");

        //View init
        favoriteMakeBtn = findViewById(R.id.movie_individual_make_favorite_btn);
        favoriteMakedBtn = findViewById(R.id.movie_individual_maked_favorite_btn);
        name = findViewById(R.id.movie_individual_name);
        createdDate = findViewById(R.id.movie_individual_created_date);
        startDate = findViewById(R.id.movie_individual_start_date);
        budget = findViewById(R.id.movie_individual_budget);
        length = findViewById(R.id.movie_individual_movie_length);
        category = findViewById(R.id.movie_individual_category);
        country = findViewById(R.id.movie_individual_country);
        company = findViewById(R.id.movie_individual_company);
        producer = findViewById(R.id.movie_individual_producer);
        description = findViewById(R.id.movie_individual_description);
        deleteMovie = findViewById(R.id.delete_movie_btn);
        movieImage = findViewById(R.id.movie_individual_image);

    }

    private void checkAndChangeItemsVisibility() {
        SharedPreferences myPref = getSharedPreferences("myPref", MODE_PRIVATE);
        String user = myPref.getString("user", "");
        tbIUser tbIUser = new Gson().fromJson(user, tbIUser.class);

        if (tbIUser.getRole() == 2) {
            deleteMovie.setVisibility(View.GONE);
        }
    }

    private void getMovieDetails() {
        IMovieAPI iMovieAPI = RetrofitClient.getInstance().create(IMovieAPI.class);

        Call<String> callMovie = iMovieAPI.getMovie(movieId);
        callMovie.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                movie = new Gson().fromJson(response.body(), Movie.class);
                name.setText(movie.getName());
                createdDate.setText(movie.getCreatedDate().substring(0, 10));
                startDate.setText(movie.getStartDate().substring(0, 10));
                budget.setText(String.valueOf(movie.getBudget()));
                length.setText(String.valueOf(movie.getLength()));
                category.setText(movie.getCategory().getName());
                company.setText(movie.getCompany().getName());
                country.setText(movie.getCountry().getName());
                producer.setText(movie.getProducerName());
                description.setText(movie.getDescription());
                actorsList = findViewById(R.id.movie_individual_actors_list);
                Picasso.get().load(movie != null ? movie.getImageURL() : "https://image.freepik.com/free-vector/seamless-various-film-cinema-graphics-light-blue-background-design_1284-42060.jpg").into(movieImage);
                for (int i = 1; i < movie.getRating() + 1; i++) {
                    findViewById(stars[i - 1]).setVisibility(View.VISIBLE);
                }

                IMovieActorCastAPI iMovieActorCastAPI = RetrofitClient.getInstance().create(IMovieActorCastAPI.class);


                final Type listType = new TypeToken<List<Actor2>>() {
                }.getType();
                Call<String> callMovieActor = iMovieActorCastAPI.getMovieActorCast(movieId);
                callMovieActor.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        actors = new Gson().fromJson(response.body(), listType);
                        List<Actor> actorsAdapter = new ArrayList<>(actors.size());
                        for (int i = 0; i < actors.size(); i++) {
                            actorsAdapter.add(actors.get(i).getActor());
                        }
                        actorsList.setAdapter(new MyMovieActorAdapter(actorsAdapter, MovieIndividualPageActivity.this));
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MovieIndividualPageActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MovieIndividualPageActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

        final Type listTypeMovieUser = new TypeToken<List<Movie>>() {
        }.getType();

        IMovieUserCastAPI iMovieUserCastAPI = RetrofitClient.getInstance().create(IMovieUserCastAPI.class);
        Call<String> callMovieUser = iMovieUserCastAPI.getMovieUserCast(username);
        callMovieUser.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                List<Movie> movies;
                movies = new Gson().fromJson(response.body(), listTypeMovieUser);
                boolean isFavorite = false;
                for (int i = 0; i < movies.size(); i++) {
                    if (movies.get(i).getId() == movieId) {
                        isFavorite = true;
                        movieUserCastId = movies.get(i).getMovieUserCasts().getId();
                        break;
                    }
                }
                if (isFavorite) {
                    favoriteMakeBtn.setVisibility(View.GONE);
                    favoriteMakedBtn.setVisibility(View.VISIBLE);
                    favoriteMakedBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            IMovieUserCastAPI iMovieUserCastAPI = RetrofitClient.getInstance().create(IMovieUserCastAPI.class);
                            Call<String> callMovieUser = iMovieUserCastAPI.deleteMovieUserCast(movieUserCastId);
                            callMovieUser.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    favoriteMakeBtn.setVisibility(View.VISIBLE);
                                    favoriteMakedBtn.setVisibility(View.GONE);
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });
                        }
                    });
                } else {
                    favoriteMakeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            IMovieUserCastAPI iMovieUserCastAPI = RetrofitClient.getInstance().create(IMovieUserCastAPI.class);

                            MovieUserCast movieUserCast = new MovieUserCast();
                            movieUserCast.setMovieId(movieId);
                            movieUserCast.setUsername(username);
                            Call<String> callMovieUser = iMovieUserCastAPI.addMovieUserCast(movieUserCast);
                            callMovieUser.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    favoriteMakeBtn.setVisibility(View.GONE);
                                    favoriteMakedBtn.setVisibility(View.VISIBLE);

                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
}