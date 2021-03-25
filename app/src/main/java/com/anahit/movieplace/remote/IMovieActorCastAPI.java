package com.anahit.movieplace.remote;

import com.anahit.movieplace.models.Movie;
import com.anahit.movieplace.models.MovieActorCast;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IMovieActorCastAPI {

    @GET("api/MovieActorCast")
    Call<String> getMovieActorCasts();

    @GET("api/MovieActorCast/{id}")
    Call<String> getMovieActorCast(@Path("id") int id);

    @POST("api/MovieActorCast")
    Call<String> addMovieActorCast(@Body MovieActorCast movieActorCast);

}
