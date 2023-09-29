package com.example.flavorfinderjava.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.example.flavorfinderjava.data.model.Meal;
import com.example.flavorfinderjava.databinding.ListItemRvBinding;

public class MealAdapter extends ListAdapter<Meal, MealViewHolder> {
    private final OnItemClickListener onItemClickListener;

    public MealAdapter(OnItemClickListener onItemClickListener, MealComparator comparator) {
        super(comparator);
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MealViewHolder(ListItemRvBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false),
                onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal item = getItem(position);
        holder.bind(item);
    }

    public Meal getItemAt(int position){
        return getItem(position);
    }
}
