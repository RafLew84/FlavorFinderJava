package com.example.flavorfinderjava.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.flavorfinderjava.data.model.Meal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Meal.class}, version = 1, exportSchema = false)
public abstract class MealDatabase extends RoomDatabase {
    public abstract MealDao mealDao();

    private static volatile MealDatabase INSTANCE;

    public static MealDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MealDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MealDatabase.class, "meal_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
