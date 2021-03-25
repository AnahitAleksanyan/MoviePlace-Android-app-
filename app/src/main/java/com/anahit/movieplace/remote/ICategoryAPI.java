package com.anahit.movieplace.remote;

import com.anahit.movieplace.models.Category;
import com.anahit.movieplace.models.Movie;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ICategoryAPI {

    @GET("api/Category")
    Call<String> getCategory();

    @GET("api/Category/{id}")
    Call<String> getCategory(@Path("id") int id);

    @POST("api/Category")
    Call<String> addCategory(@Body Category category);

    @DELETE("api/Category/{id}")
    Call<String> deleteCategory(@Path("id") int id);
}
