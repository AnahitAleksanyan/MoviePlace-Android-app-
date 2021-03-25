package com.anahit.movieplace.remote;

import com.anahit.movieplace.models.Movie;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IMovieAPI {

    @GET("api/Movie")
    Call<String> getMovies();

    @GET("api/Movie/{id}")
    Call<String> getMovie(@Path("id") int id);

    @POST("api/Movie")
    Call<String> addMovie(@Body Movie movie);

    @DELETE("api/Movie/{id}")
    Call<String> deleteMovie(@Path("id") int id);
}
