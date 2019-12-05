package com.example.a1lab;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TerritoryService {
    @GET("territories")
    Call<List<Territory>> getTerritories();
}
