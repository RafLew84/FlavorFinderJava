package com.example.flavorfinderjava.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.flavorfinderjava.R;
import com.example.flavorfinderjava.data.model.Meal;
import com.example.flavorfinderjava.databinding.FragmentDetailBinding;
import com.example.flavorfinderjava.viewmodel.MealViewModel;

import java.util.Optional;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class DetailFragment extends Fragment {
    private FragmentDetailBinding binding;
    private MealViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MealViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getMeal().observe(getViewLifecycleOwner(), mealResponse -> {
            if (mealResponse.data != null){
                Optional<Meal> mealOptional = mealResponse
                        .data
                        .meals
                        .stream()
                        .findFirst();

                mealOptional.ifPresent(this::inflate);

                binding.favoriteButton.setOnClickListener(v -> viewModel.insert(
                        mealOptional.orElse(null)
                ));
            }
        });
    }

    private void inflate(Meal item){

        Context context = requireContext();
        ImageLoader imageLoader = Coil.imageLoader(context);
        ImageRequest request = new ImageRequest.Builder(context)
                .data(item.strMealThumb)
                .target(binding.foodImage)
                .build();
        imageLoader.enqueue(request);

        binding.category.setText(item.strCategory);
        binding.title.setText(item.strMeal);
        binding.instructions.setText(item.strInstructions);
    }
}