package com.example.flavorfinderjava.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private RetrofitInstance(){}

    private static volatile MealApi api;
    private static final String baseUrl = "https://www.themealdb.com/";

    public static MealApi getApi() {
        if (api == null) {
            synchronized (RetrofitInstance.class) {
                if (api == null) {
                    api = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(MealApi.class);
                }
            }
        }
        return api;
    }
}
