package com.example.a1lab;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppEx extends Application {
    private TerritoryService apiService;

    public void onCreate() {
        super.onCreate();
        apiService = createApiService();
    }

    private TerritoryService createApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://5amcy60qq3.execute-api.us-east-1.amazonaws.com/dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(TerritoryService.class);
    }

    public TerritoryService getAPIService() {
        return apiService;
    }
}
