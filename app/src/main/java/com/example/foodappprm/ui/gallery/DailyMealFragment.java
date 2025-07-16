package com.example.foodappprm.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodappprm.R;

import java.util.ArrayList;
import java.util.List;
import com.example.foodappprm.Models.DailyMeal;
import com.example.foodappprm.adapters.DailyMealAdapter;

public class DailyMealFragment extends Fragment {

    RecyclerView recyclerView;
    List<DailyMeal> dailyMealModels;
    DailyMealAdapter dailyMealAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_daily_meal, container, false);

        recyclerView = root.findViewById(R.id.daily_meal_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dailyMealModels = new ArrayList<>();

        dailyMealModels.add(new DailyMeal(R.drawable.breakfast, "Breakfast", "20% off", "Breakfast", "Delicious pizza with fresh ingredients"));
        dailyMealModels.add(new DailyMeal(R.drawable.lunch, "Lunch", "30% off", "Lunch", "Delicious pizza with fresh"));
        dailyMealModels.add(new DailyMeal(R.drawable.dinner, "Dinner", "25% off", "Dinner", "Delicious pizza with"));
        dailyMealModels.add(new DailyMeal(R.drawable.sweets, "Sweets", "49% off", "Sweets", "Delicious pizza"));
        dailyMealModels.add(new DailyMeal(R.drawable.coffe, "Coffee", "20% off", "Drinks", "Delicious pizza with fresh ingredients"));

        dailyMealAdapter = new DailyMealAdapter(getContext(),dailyMealModels);
        recyclerView.setAdapter(dailyMealAdapter);
        dailyMealAdapter.notifyDataSetChanged();
        return root;
    }
}