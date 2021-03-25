package com.anahit.movieplace.remote;

import com.anahit.movieplace.models.Category;
import com.anahit.movieplace.models.Company;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ICompanyAPI {

    @GET("api/Company")
    Call<String> getCompany();

    @GET("api/Company/{id}")
    Call<String> getCompany(@Path("id") String id);

    @POST("api/Company")
    Call<String> addCompany(@Body Company company);

    @DELETE("api/Company/{id}")
    Call<String> deleteCompany(@Path("id") int id);
}
