package com.anahit.movieplace.remote;

import com.anahit.movieplace.models.Category;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRatingAPI {

    @GET("api/Rating")
    Call<String> getRatings();

}
