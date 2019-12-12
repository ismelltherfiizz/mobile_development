package com.example.a1lab;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TerritoryService {
    @GET("territories")
    Call<List<Territory>> getTerritories();

    @POST("territories")
    Call<Territory> createTerritory(@Body Territory territory);
}
