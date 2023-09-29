package com.example.flavorfinderjava.data.db;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.flavorfinderjava.data.model.Meal;

import java.util.List;

@Dao
public interface MealDao {
    @Insert(onConflict = REPLACE)
    void insert(Meal meal);

    @Delete
    void delete(Meal meal);

    @Query("SELECT * FROM meals")
    LiveData<List<Meal>> getAllMeals();
}
