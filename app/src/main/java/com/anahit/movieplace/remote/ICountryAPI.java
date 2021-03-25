package com.anahit.movieplace.remote;

import com.anahit.movieplace.models.Category;
import com.anahit.movieplace.models.Country;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ICountryAPI {

    @GET("api/Country")
    Call<String> getCountry();

    @GET("api/Country/{id}")
    Call<String> getCountry(@Path("id") String id);

    @POST("api/Country")
    Call<String> addCountry(@Body Country country);
}
