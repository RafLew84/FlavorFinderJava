package com.example.flavorfinderjava.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.flavorfinderjava.data.api.RetrofitInstance;
import com.example.flavorfinderjava.data.db.MealDao;
import com.example.flavorfinderjava.data.db.MealDatabase;
import com.example.flavorfinderjava.data.model.Meal;
import com.example.flavorfinderjava.data.model.MealResponse;

import java.util.List;

import retrofit2.Call;

public class MealRepository {

    private final MealDao mealDao;

    public MealRepository(Application application) {
        MealDatabase db = MealDatabase.getDatabase(application);
        mealDao = db.mealDao();
    }

    public Call<MealResponse> getMealFromApi(){
        return RetrofitInstance.getApi().getMealFromApi();
    }

    public Call<MealResponse> getMealById(String id){
        return RetrofitInstance.getApi().getMealById(id);
    }

    public void insert(Meal meal){
        mealDao.insert(meal);
    }

    public void delete(Meal meal){
        mealDao.delete(meal);
    }

    public LiveData<List<Meal>> getAllMeals(){
        return mealDao.getAllMeals();
    }
}
