package com.anahit.movieplace.remote;

import com.anahit.movieplace.models.Actor;
import com.anahit.movieplace.models.Category;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IActorAPI {

    @GET("api/Actor")
    Call<String> getActor();

    @GET("api/Actor/{id}")
    Call<String> getActor(@Path("id") String id);

    @POST("api/Actor")
    Call<String> addActor(@Body Actor actor);

    @DELETE("api/Actor/{id}")
    Call<String> deleteActor(@Path("id") int id);
}
