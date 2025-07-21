package com.example.foodappprm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodappprm.Models.FeaturedVerModel;
import com.example.foodappprm.R;

import java.util.List;

public class FeaturedVerAdapter extends RecyclerView.Adapter<FeaturedVerAdapter.ViewHolder> {

    // 1. Định nghĩa interface để lắng nghe sự kiện
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final List<FeaturedVerModel> list;
    private final OnItemClickListener listener;

    // 2. Cập nhật constructor để nhận listener
    public FeaturedVerAdapter(List<FeaturedVerModel> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_ver_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeaturedVerModel model = list.get(position);
        holder.imageView.setImageResource(model.getImage());
        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.rating.setText(model.getRating());
        holder.timing.setText(model.getTiming());

        // 3. Gán sự kiện click cho mỗi item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, description, rating, timing;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.detailed_img);
            name = itemView.findViewById(R.id.detailed_name);
            description = itemView.findViewById(R.id.detailed_description);
            rating = itemView.findViewById(R.id.detailed_rating);
            timing = itemView.findViewById(R.id.detailed_timing);
        }
    }
}