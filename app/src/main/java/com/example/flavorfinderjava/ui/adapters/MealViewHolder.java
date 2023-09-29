package com.example.flavorfinderjava.ui.adapters;


import android.content.Context;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flavorfinderjava.data.model.Meal;
import com.example.flavorfinderjava.databinding.ListItemRvBinding;
import com.example.flavorfinderjava.ui.fragments.FoodListFragmentDirections;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class MealViewHolder extends RecyclerView.ViewHolder {
    private final ListItemRvBinding binding;
    private final OnItemClickListener onItemClickListener;
    public MealViewHolder(ListItemRvBinding binding, OnItemClickListener onItemClickListener) {
        super(binding.getRoot());
        this.binding = binding;
        this.onItemClickListener = onItemClickListener;
    }

    public void bind(Meal item){
        binding.name.setText(item.strMeal);

        Context context = binding.getRoot().getContext();
        ImageLoader imageLoader = Coil.imageLoader(context);
        ImageRequest request = new ImageRequest.Builder(context)
                .data(item.strMealThumb)
                .target(binding.image)
                .build();
        imageLoader.enqueue(request);

        binding.getRoot().setOnClickListener(v -> onItemClickListener.onItemClick(item.idMeal));

//        binding.getRoot().setOnClickListener(view -> {
//            NavDirections action = FoodListFragmentDirections
//                    .actionFoodListFragmentToDetailFragment();
//            Navigation.findNavController(binding.getRoot()).navigate(action);
//        });
    }
}
