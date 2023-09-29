package com.example.flavorfinderjava.ui.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.flavorfinderjava.data.model.Meal;

public class MealComparator extends DiffUtil.ItemCallback<Meal> {
    @Override
    public boolean areItemsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
        return newItem.idMeal.equals(oldItem.idMeal);
    }

    @Override
    public boolean areContentsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
        return newItem.strMeal.equals(oldItem.strMeal);
    }
}
