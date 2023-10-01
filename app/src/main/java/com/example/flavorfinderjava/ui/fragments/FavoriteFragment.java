package com.example.flavorfinderjava.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flavorfinderjava.R;
import com.example.flavorfinderjava.databinding.FragmentFavoriteBinding;
import com.example.flavorfinderjava.ui.adapters.MealAdapter;
import com.example.flavorfinderjava.ui.adapters.MealComparator;
import com.example.flavorfinderjava.ui.adapters.OnItemClickListener;
import com.example.flavorfinderjava.viewmodel.MealViewModel;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private MealViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MealViewModel.class);
        viewModel.getLocalMeals();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MealAdapter adapter = new MealAdapter(
                this::onItemClick,
            new MealComparator()
        );
        setupRecyclerView(adapter);

        viewModel.getFavorites().observe(getViewLifecycleOwner(), adapter::submitList);

        swipeToDelete(adapter);
    }

    private void setupRecyclerView(MealAdapter adapter){
        binding.favoriteRV.setAdapter(adapter);
        binding.favoriteRV.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void swipeToDelete(MealAdapter adapter) {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getItemAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(binding.favoriteRV);
    }

    private void onItemClick(String mealId) {
        viewModel.getMealById(mealId);
        NavDirections action = FavoriteFragmentDirections
                .actionFavoriteFragmentToDetailFragment();
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }
}