package com.example.flavorfinderjava.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flavorfinderjava.data.db.MealDao;
import com.example.flavorfinderjava.data.db.MealDatabase;
import com.example.flavorfinderjava.data.model.Meal;
import com.example.flavorfinderjava.data.model.MealResponse;
import com.example.flavorfinderjava.data.repository.MealRepository;
import com.example.flavorfinderjava.util.AppExecutors;
import com.example.flavorfinderjava.util.Resource;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MealViewModel extends AndroidViewModel {

    private final String TAG = "MealViewModel";

    private final MealRepository repository;

    private final MutableLiveData<Resource<MealResponse>> meals = new MutableLiveData<>();
    private final MutableLiveData<Resource<MealResponse>> meal = new MutableLiveData<>();
    private LiveData<List<Meal>> favorites;

    public MealViewModel(@NonNull Application application) {
        super(application);
        repository = new MealRepository(application);
    }

    public LiveData<Resource<MealResponse>> getMeals() {
        return meals;
    }

    public LiveData<Resource<MealResponse>> getMeal() {
        return meal;
    }

    public LiveData<List<Meal>> getFavorites() {
        return favorites;
    }

    public void getMealById(String id){
        Call<MealResponse> responseCall = repository.getMealById(id);

        responseCall.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                if (response.isSuccessful()){
                    meal.postValue(Resource.loading(null));
                    MealResponse mealResponse = response.body();
                    if (mealResponse != null)
                        meal.postValue(Resource.success(mealResponse));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable t) {
                meal.postValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
    }

    public void getMealsFromApi() {
        Call<MealResponse> responseCall = repository.getMealFromApi();

        responseCall.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                meals.postValue(Resource.loading(null));
                if (response.isSuccessful()){
                    MealResponse mealsResponse = response.body();
                    if (mealsResponse != null)
                        meals.postValue(Resource.success(mealsResponse));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable t) {
                meals.postValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
    }

    public void getLocalMeals(){
        favorites = repository.getAllMeals();
    }

    public void insert(Meal meal){
        AppExecutors.getInstance().diskIO().execute(() -> repository.insert(meal));
    }

    public void delete(Meal meal){
        AppExecutors.getInstance().diskIO().execute(() -> repository.delete(meal));
    }
}
