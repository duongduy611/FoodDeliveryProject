package com.example.foodappprm.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodappprm.Models.DetailedDailyMeal;
import com.example.foodappprm.R;
import com.example.foodappprm.adapters.DetailedDailyAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailDailyMealActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<DetailedDailyMeal> detailDailyList;
    DetailedDailyAdapter detailDailyAdapter;
    ImageView imageView; // <-- Bạn đã dùng imageView nhưng chưa khai báo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_daily_meal);

        // Ánh xạ view
        recyclerView = findViewById(R.id.detailed_rec);
        imageView = findViewById(R.id.detailed_img);

        // Thiết lập RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailDailyList = new ArrayList<>();

        detailDailyAdapter = new DetailedDailyAdapter(this, detailDailyList);
        recyclerView.setAdapter(detailDailyAdapter);

        String type = getIntent().getStringExtra("type");
        if (type != null && type.equalsIgnoreCase("Breakfast")) {
            detailDailyList.add(new DetailedDailyMeal(R.drawable.fav1, "Breakfast 1", "Delicious breakfast with fresh ingredients", "4.5", "$10", "30 mins"));
            detailDailyList.add(new DetailedDailyMeal(R.drawable.fav2, "Breakfast 2", "Delicious breakfast with fresh ingredients", "4.5", "$10", "30 mins"));
            detailDailyList.add(new DetailedDailyMeal(R.drawable.fav3, "Breakfast 3", "Delicious breakfast with fresh ingredients", "4.5", "$10", "30 mins"));
            detailDailyAdapter.notifyDataSetChanged();
        }

        if (type != null && type.equalsIgnoreCase("Sweets")) {
            imageView.setImageResource(R.drawable.sweets); // Set the image for sweets
            detailDailyList.add(new DetailedDailyMeal(R.drawable.s1, "Sweets 1", "Delicious breakfast with fresh ingredients", "4.5", "$10", "30 mins"));
            detailDailyList.add(new DetailedDailyMeal(R.drawable.s2, "Sweets 2", "Delicious breakfast with fresh ingredients", "4.5", "$10", "30 mins"));
            detailDailyList.add(new DetailedDailyMeal(R.drawable.s3, "Sweets 3", "Delicious breakfast with fresh ingredients", "4.5", "$10", "30 mins"));
            detailDailyList.add(new DetailedDailyMeal(R.drawable.s4, "Sweets 4", "Delicious breakfast with fresh ingredients", "4.5", "$10", "30 mins"));
            detailDailyAdapter.notifyDataSetChanged();
        }
    }
}