package com.anahit.movieplace.service;

import com.anahit.movieplace.remote.IActorAPI;
import com.anahit.movieplace.remote.ICountryAPI;
import com.anahit.movieplace.remote.RetrofitClient;

public class APIService {
    public static final IActorAPI iActorAPI = RetrofitClient.getInstance().create(IActorAPI.class);
    public static final ICountryAPI iCountryAPI = RetrofitClient.getInstance().create(ICountryAPI.class);
}
