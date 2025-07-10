package com.example.foodappprm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodappprm.R;
import com.example.foodappprm.model.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<Cart> cartItems;
    private CartItemListener listener;

    public interface CartItemListener {
        void onQuantityChanged(Cart item, int newQuantity);
        void onRemoveItem(Cart item);
    }

    public CartAdapter(Context context, List<Cart> cartItems, CartItemListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart item = cartItems.get(position);

        // Load image using Glide
        Glide.with(context)
                .load(item.getImage())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.cartItemImage);

        holder.cartItemName.setText(item.getName());
        holder.cartItemPrice.setText(String.format("$%.2f", item.getPrice()));
        holder.quantity.setText(String.valueOf(item.getQuantity()));

        // Handle meal-specific UI elements
        if (item.isMeal()) {
            holder.mealLabel.setVisibility(View.VISIBLE);
            holder.includedItems.setVisibility(View.VISIBLE);
            // Join the included products with commas
            String includedItemsText = "Includes: " + String.join(", ", item.getIncludedProducts());
            holder.includedItems.setText(includedItemsText);
        } else {
            holder.mealLabel.setVisibility(View.GONE);
            holder.includedItems.setVisibility(View.GONE);
        }

        // Quantity controls
        holder.decreaseQuantity.setOnClickListener(v -> {
            int newQuantity = item.getQuantity() - 1;
            if (listener != null) {
                listener.onQuantityChanged(item, newQuantity);
            }
        });

        holder.increaseQuantity.setOnClickListener(v -> {
            int newQuantity = item.getQuantity() + 1;
            if (listener != null) {
                listener.onQuantityChanged(item, newQuantity);
            }
        });

        holder.removeItem.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRemoveItem(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cartItemImage;
        TextView cartItemName, cartItemPrice, quantity, mealLabel, includedItems;
        ImageButton decreaseQuantity, increaseQuantity, removeItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.cartItemImage);
            cartItemName = itemView.findViewById(R.id.cartItemName);
            cartItemPrice = itemView.findViewById(R.id.cartItemPrice);
            quantity = itemView.findViewById(R.id.quantity);
            mealLabel = itemView.findViewById(R.id.mealLabel);
            includedItems = itemView.findViewById(R.id.includedItems);
            decreaseQuantity = itemView.findViewById(R.id.decreaseQuantity);
            increaseQuantity = itemView.findViewById(R.id.increaseQuantity);
            removeItem = itemView.findViewById(R.id.removeItem);
        }
    }
}
