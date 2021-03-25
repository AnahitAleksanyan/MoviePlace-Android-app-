package com.anahit.movieplace.remote;

import com.anahit.movieplace.models.tbIUser;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IMovieAuthAPI {
    @POST("api/register")
    Call<String> registerUser(@Body tbIUser user);

    @POST("api/login")
    Call<String> loginUser(@Body tbIUser user);

}
