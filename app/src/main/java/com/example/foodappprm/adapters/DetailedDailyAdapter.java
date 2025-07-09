package com.example.foodappprm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.foodappprm.Models.DetailedDailyMeal;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodappprm.R;

import java.util.List;
import android.content.Context;

public class DetailedDailyAdapter extends RecyclerView.Adapter<DetailedDailyAdapter.ViewHolder> {

    // Context and list of DetailedDailyMeal objects
    Context context;
    List<DetailedDailyMeal> list;

    public DetailedDailyAdapter(Context context, List<DetailedDailyMeal> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_daily_meal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailedDailyMeal meal = list.get(position);
        holder.imageView.setImageResource(meal.getImage());
        holder.name.setText(meal.getName());
        holder.description.setText(meal.getDescription());
        holder.rating.setText(meal.getRating());
        holder.price.setText(meal.getPrice());
        holder.timing.setText(meal.getTiming());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, description, rating, price, timing;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.detailed_img);
            name = itemView.findViewById(R.id.detailed_name);
            description = itemView.findViewById(R.id.detailed_description);
            rating = itemView.findViewById(R.id.detailed_rating);
            price = itemView.findViewById(R.id.detailed_price);
            timing = itemView.findViewById(R.id.detailed_timing);
        }
    }
}
