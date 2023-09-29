package com.example.flavorfinderjava.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.flavorfinderjava.R;
import com.example.flavorfinderjava.data.model.Meal;
import com.example.flavorfinderjava.databinding.FragmentFoodListBinding;
import com.example.flavorfinderjava.ui.adapters.MealAdapter;
import com.example.flavorfinderjava.ui.adapters.MealComparator;
import com.example.flavorfinderjava.ui.adapters.OnItemClickListener;
import com.example.flavorfinderjava.util.Status;
import com.example.flavorfinderjava.viewmodel.MealViewModel;

import java.util.List;


public class FoodListFragment extends Fragment {

    private FragmentFoodListBinding binding;
    private MealViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFoodListBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MealViewModel.class);
        viewModel.getMealsFromApi();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MealAdapter adapter = new MealAdapter(
                this::onItemClick,
                new MealComparator());

        setupRecyclerView(adapter);

        viewModel.getMeals().observe(getViewLifecycleOwner(), mealResponse -> {
            if (mealResponse.status == Status.SUCCESS) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                if (mealResponse.data != null) {
                    List<Meal> data = mealResponse.data.meals;
                        adapter.submitList(data);
                    }
                } else if (mealResponse.status == Status.ERROR) {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(requireContext(), mealResponse.message, Toast.LENGTH_SHORT).show();
                } else if (mealResponse.status == Status.LOADING) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show();
                }
        });
    }

    private void onItemClick(String mealId) {
        viewModel.getMealById(mealId);
        NavDirections action = FoodListFragmentDirections
            .actionFoodListFragmentToDetailFragment();
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    private void setupRecyclerView(MealAdapter adapter){
        binding.foodRV.setAdapter(adapter);
        binding.foodRV.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
}