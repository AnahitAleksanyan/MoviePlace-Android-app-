package com.anahit.movieplace.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anahit.movieplace.R;
import com.anahit.movieplace.adapters.MyMovieActorAdapter;
import com.anahit.movieplace.fragments.MovieFragment;
import com.anahit.movieplace.models.Actor;
import com.anahit.movieplace.models.Category;
import com.anahit.movieplace.models.Company;
import com.anahit.movieplace.models.Country;
import com.anahit.movieplace.models.Movie;
import com.anahit.movieplace.models.MovieActorCast;
import com.anahit.movieplace.models.tbIUser;
import com.anahit.movieplace.remote.IActorAPI;
import com.anahit.movieplace.remote.ICategoryAPI;
import com.anahit.movieplace.remote.ICompanyAPI;
import com.anahit.movieplace.remote.ICountryAPI;
import com.anahit.movieplace.remote.IMovieAPI;
import com.anahit.movieplace.remote.IMovieActorCastAPI;
import com.anahit.movieplace.remote.IRatingAPI;
import com.anahit.movieplace.remote.RetrofitClient;
import com.anahit.movieplace.service.APIService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMovieActivity extends AppCompatActivity {

    View actorRow;
    List<Actor> actors;
    List<Country> countries;
    List<Company> companies;
    List<Category> categories;
    List<String> ratings;
    List<Actor> adapterActors = new LinkedList<>();
    Spinner actorSpinner;
    Spinner countrySpinner;
    Spinner categorySpinner;
    Spinner companySpinner;
    Spinner ratingSpinner;
    ListView actorsListView;
    MyMovieActorAdapter adapter;
    tbIUser user;
    String userStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movie_activity);

        initValues();

        getDates();

        findViewById(R.id.add_movie_add_actor_btn).setOnClickListener(v -> actorRow.setVisibility(View.VISIBLE));

        findViewById(R.id.add_movie_add_btn).setOnClickListener(v -> addMovieOnClick());

        findViewById(R.id.save_movie_btn).setOnClickListener(v -> saveMovieOnClick());

    }

    private void getDates() {
        //Actor
        IActorAPI iActorAPI = APIService.iActorAPI;
        final Type listTypeActor = new TypeToken<List<Actor>>() {
        }.getType();

        Call<String> callActor = iActorAPI.getActor();
        callActor.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                actors = new Gson().fromJson(response.body(), listTypeActor);
                ArrayAdapter<Actor> dataAdapter = new ArrayAdapter<Actor>(AddMovieActivity.this,
                        android.R.layout.simple_spinner_item, actors);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                actorSpinner.setAdapter(dataAdapter);

                adapter = new MyMovieActorAdapter(adapterActors, AddMovieActivity.this);
                actorsListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AddMovieActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

        //Country
        ICountryAPI iCountryAPI = APIService.iCountryAPI;
        final Type listTypeCountry = new TypeToken<List<Country>>() {
        }.getType();

        Call<String> callCountry = iCountryAPI.getCountry();
        callCountry.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                countries = new Gson().fromJson(response.body(), listTypeCountry);
                ArrayAdapter<Country> dataAdapter = new ArrayAdapter<Country>(AddMovieActivity.this,
                        android.R.layout.simple_spinner_item, countries);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                countrySpinner.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AddMovieActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });


        //Category
        ICategoryAPI iCategoryAPI = RetrofitClient.getInstance().create(ICategoryAPI.class);
        final Type listTypeCategory = new TypeToken<List<Category>>() {
        }.getType();

        Call<String> callCategory = iCategoryAPI.getCategory();
        callCategory.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                categories = new Gson().fromJson(response.body(), listTypeCategory);
                ArrayAdapter<Category> dataAdapter = new ArrayAdapter<Category>(AddMovieActivity.this,
                        android.R.layout.simple_spinner_item, categories);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorySpinner.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AddMovieActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });


        //Company
        ICompanyAPI iCompanyAPI = RetrofitClient.getInstance().create(ICompanyAPI.class);
        final Type listTypeCompany = new TypeToken<List<Company>>() {
        }.getType();

        Call<String> callCompany = iCompanyAPI.getCompany();
        callCompany.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                companies = new Gson().fromJson(response.body(), listTypeCompany);
                ArrayAdapter<Company> dataAdapter = new ArrayAdapter<Company>(AddMovieActivity.this,
                        android.R.layout.simple_spinner_item, companies);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                companySpinner.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AddMovieActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

        //Rating
        IRatingAPI iRatingAPI = RetrofitClient.getInstance().create(IRatingAPI.class);
        final Type listTypeRating = new TypeToken<List<String>>() {
        }.getType();

        Call<String> callRating = iRatingAPI.getRatings();
        callRating.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ratings = new Gson().fromJson(response.body(), listTypeRating);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddMovieActivity.this,
                        android.R.layout.simple_spinner_item, ratings);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ratingSpinner.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AddMovieActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void closeActivity() {
        this.finish();
    }

    private void initValues() {
        actorRow = findViewById(R.id.add_movie_add_actor_row);
        actorSpinner = findViewById(R.id.add_movie_add_actor_spinner);
        countrySpinner = findViewById(R.id.add_movie_Country);
        categorySpinner = findViewById(R.id.add_movie_Category);
        companySpinner = findViewById(R.id.add_movie_Company);
        ratingSpinner = findViewById(R.id.add_movie_Rating);
        actorsListView = findViewById(R.id.add_movie_actors_list);

        Intent intent = getIntent();
        userStr = intent.getStringExtra("user");
        user = new Gson().fromJson(userStr, tbIUser.class);
    }

    private void addMovieOnClick() {
        Actor selectedActor = (Actor) actorSpinner.getSelectedItem();
        adapterActors.add(selectedActor);
        boolean a = actors.remove(selectedActor);
        ArrayAdapter<Actor> dataAdapter = new ArrayAdapter<Actor>(AddMovieActivity.this,
                android.R.layout.simple_spinner_item, actors);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actorSpinner.setAdapter(dataAdapter);
        actorRow.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
        if (actors.isEmpty()) {
            findViewById(R.id.add_movie_add_actor_btn).setVisibility(View.GONE);
        }
    }

    private void saveMovieOnClick() {
        String movieName = ((EditText) findViewById(R.id.add_movie_name)).getText().toString();
        String movieCreatedDate = ((EditText) findViewById(R.id.add_movie_created_date)).getText().toString();
        String movieStartDate = ((EditText) findViewById(R.id.add_movie_start_date)).getText().toString();
        String movieLength = ((EditText) findViewById(R.id.add_movie_length)).getText().toString();
        String movieProducerName = ((EditText) findViewById(R.id.add_movie_producer)).getText().toString();
        String movieBudget = ((EditText) findViewById(R.id.add_movie_budget)).getText().toString();
        String movieDescription = ((EditText) findViewById(R.id.add_movie_description)).getText().toString();
        String movieImageURL = ((EditText) findViewById(R.id.add_movie_imageURL)).getText().toString();
        Category category = (Category) categorySpinner.getSelectedItem();
        Country country = (Country) countrySpinner.getSelectedItem();
        Company company = (Company) companySpinner.getSelectedItem();
        String rating = (String) ratingSpinner.getSelectedItem();

        if (movieName.equals("") ||
                movieCreatedDate.equals("") ||
                movieStartDate.equals("") ||
                movieLength.equals("") ||
                movieProducerName.equals("") ||
                movieBudget.equals("") ||
                movieDescription.equals("") ||
                category == null ||
                country == null ||
                company == null ||
                rating.equals("")

        ) {
            Toast.makeText(AddMovieActivity.this, "All movie data are required.", Toast.LENGTH_LONG).show();
        } else if (!Pattern.matches("^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$", movieCreatedDate) ||
                !Pattern.matches("^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$", movieStartDate)) {
            Toast.makeText(AddMovieActivity.this, "Date format must be dd/mm/yyyy", Toast.LENGTH_LONG).show();
        } else {
            Movie movie = new Movie();
            movie.setName(movieName);
            movie.setCreatedDate(movieCreatedDate);
            movie.setStartDate(movieStartDate);
            movie.setLength(Integer.parseInt(movieLength));
            movie.setProducerName(movieProducerName);
            movie.setBudget(Long.parseLong(movieBudget));
            movie.setDescription(movieDescription);
            movie.setCategoryId(category.getId());
            movie.setCountryId(country.getId());
            movie.setCompanyId(company.getId());
            movie.setImageURL(movieImageURL);


            if (rating.equals("Nice")) {
                movie.setRating(5);
            } else if (rating.equals("Good")) {
                movie.setRating(4);
            } else if (rating.equals("Middle")) {
                movie.setRating(3);
            } else if (rating.equals("Bad")) {
                movie.setRating(2);
            } else if (rating.equals("VeryBad")) {
                movie.setRating(1);
            }

            final Movie[] savedMovie = new Movie[1];

            IMovieAPI iMovieAPI = RetrofitClient.getInstance().create(IMovieAPI.class);
            Call<String> callMovie = iMovieAPI.addMovie(movie);
            callMovie.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    savedMovie[0] = new Gson().fromJson(response.body(), Movie.class);
                    if (savedMovie[0].getId() != 0) {
                        for (int i = 0; i < adapterActors.size(); i++) {
                            MovieActorCast movieActorCast = new MovieActorCast(savedMovie[0].getId(), adapterActors.get(i).getId());
                            IMovieActorCastAPI iMovieActorCastAPI = RetrofitClient.getInstance().create(IMovieActorCastAPI.class);
                            Call<String> callMovieActor = iMovieActorCastAPI.addMovieActorCast(movieActorCast);
                            callMovieActor.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Toast.makeText(AddMovieActivity.this, response.body(), Toast.LENGTH_LONG).show();
                                    movie.setId(savedMovie[0].getId());
                                    movie.setCategory(category);
                                    movie.setCompany(company);
                                    movie.setCountry(country);
                                    MovieFragment.getINSTANCE().addMovieIntoListAndNotify(movie);
                                    closeActivity();
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(AddMovieActivity.this, "Error", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        Intent intent1 = new Intent(AddMovieActivity.this, HomeActivity.class);
                        intent1.putExtra("user", userStr);
                        startActivity(intent1);
                    } else {
                        Toast.makeText(AddMovieActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(AddMovieActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}