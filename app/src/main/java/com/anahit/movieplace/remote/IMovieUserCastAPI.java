package com.anahit.movieplace.remote;

import com.anahit.movieplace.models.MovieActorCast;
import com.anahit.movieplace.models.MovieUserCast;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMovieUserCastAPI {

    @GET("api/MovieUserCast")
    Call<String> getMovieUserCasts();

    @GET("api/MovieUserCast/{id}")
    Call<String> getMovieUserCast(@Path("id") String id);

    @POST("api/MovieUserCast")
    Call<String> addMovieUserCast(@Body MovieUserCast movieUserCast);

    @DELETE("api/MovieUserCast/{id}/")
    Call<String> deleteMovieUserCast(@Path("id") int id);
}
