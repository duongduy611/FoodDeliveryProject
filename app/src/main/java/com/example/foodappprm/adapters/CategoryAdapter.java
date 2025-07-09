package com.example.foodappprm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodappprm.R;
import com.example.foodappprm.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private final List<Category> categories;
    private final OnCategoryClickListener onCategoryClickListener;
    private int selectedPosition = -1; // -1 means no selection

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    public CategoryAdapter(List<Category> categories, OnCategoryClickListener listener) {
        this.categories = categories;
        this.onCategoryClickListener = listener;
    }

    public void clearSelection() {
        int oldPosition = selectedPosition;
        selectedPosition = -1;
        notifyItemChanged(oldPosition);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView iconImageView;
        private TextView nameTextView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.categoryIconImageView);
            nameTextView = itemView.findViewById(R.id.categoryNameTextView);
        }

        public void bind(final Category category, boolean isSelected) {
            nameTextView.setText(category.getName());

            int resourceId = itemView.getContext().getResources().getIdentifier(
                category.getIconName(),
                "drawable",
                itemView.getContext().getPackageName()
            );

            if (resourceId != 0) {
                iconImageView.setImageResource(resourceId);
            } else {
                iconImageView.setImageResource(R.drawable.placeholder_image);
            }

            itemView.setBackgroundResource(
                isSelected ? R.drawable.bg_category_selected : R.drawable.bg_category_normal
            );
        }
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category, position == selectedPosition);

        holder.itemView.setOnClickListener(v -> {
            int previousSelected = selectedPosition;
            selectedPosition = position;
            notifyItemChanged(previousSelected);
            notifyItemChanged(selectedPosition);
            onCategoryClickListener.onCategoryClick(category);
        });
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }
}
