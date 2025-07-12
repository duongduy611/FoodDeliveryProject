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
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Context context;
    private final List<Cart> cartItems;
    private final CartItemListener listener;

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
                .placeholder(R.drawable.error_image)
                .into(holder.cartItemImage);

        holder.cartItemName.setText(item.getName());
        // Hiển thị tổng giá tiền của item (price * quantity)
        holder.cartItemPrice.setText(String.format(Locale.US, "%,.0fđ", item.getTotalPrice()));
        holder.quantityText.setText(String.valueOf(item.getQuantity()));

        // Handle meal-specific UI elements
        if (item.isMeal()) {
            holder.includedItemsText.setVisibility(View.VISIBLE);
            String includedItemsText = "Includes: " + String.join(", ", item.getIncludedProducts());
            holder.includedItemsText.setText(includedItemsText);
        } else {
            holder.includedItemsText.setVisibility(View.GONE);
        }

        // Quantity controls
        holder.decreaseButton.setOnClickListener(v -> {
            int newQuantity = item.getQuantity() - 1;
            if (newQuantity >= 1 && listener != null) {
                // Cập nhật quantity và giá ngay lập tức trên UI
                item.setQuantity(newQuantity);
                holder.quantityText.setText(String.valueOf(newQuantity));
                holder.cartItemPrice.setText(String.format(Locale.US, "%,.0fđ", item.getTotalPrice()));
                listener.onQuantityChanged(item, newQuantity);
            }
        });

        holder.increaseButton.setOnClickListener(v -> {
            if (listener != null) {
                int newQuantity = item.getQuantity() + 1;
                // Cập nhật quantity và giá ngay lập tức trên UI
                item.setQuantity(newQuantity);
                holder.quantityText.setText(String.valueOf(newQuantity));
                holder.cartItemPrice.setText(String.format(Locale.US, "%,.0fđ", item.getTotalPrice()));
                listener.onQuantityChanged(item, newQuantity);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
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
        TextView cartItemName, cartItemPrice, quantityText, includedItemsText;
        ImageButton decreaseButton, increaseButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.cartItemImage);
            cartItemName = itemView.findViewById(R.id.cartItemName);
            cartItemPrice = itemView.findViewById(R.id.cartItemPrice);
            quantityText = itemView.findViewById(R.id.quantityText);
            includedItemsText = itemView.findViewById(R.id.includedItemsText);
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
            increaseButton = itemView.findViewById(R.id.increaseButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
