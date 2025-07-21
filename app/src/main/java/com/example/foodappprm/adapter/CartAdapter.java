package com.example.foodappprm.adapter;

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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private final List<Cart> cartItems;
    private final CartItemClickListener listener;

    public interface CartItemClickListener {
        void onQuantityChanged(int position, int newQuantity);
        void onRemoveItem(int position);
    }

    public CartAdapter(List<Cart> cartItems, CartItemClickListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart item = cartItems.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView nameText;
        private final TextView priceText;
        private final TextView quantityText;
        private final TextView includedItemsText;
        private final ImageButton decreaseButton;
        private final ImageButton increaseButton;
        private final ImageButton deleteButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cartItemImage);
            nameText = itemView.findViewById(R.id.cartItemName);
            priceText = itemView.findViewById(R.id.cartItemPrice);
            quantityText = itemView.findViewById(R.id.quantityText);
            includedItemsText = itemView.findViewById(R.id.includedItemsText);
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
            increaseButton = itemView.findViewById(R.id.increaseButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(Cart item, int position) {
            nameText.setText(item.getName());
            priceText.setText(String.format(Locale.US, "$%.2f", item.getPrice()));
            quantityText.setText(String.valueOf(item.getQuantity()));

            // Handle meal combos
            if (item.isMeal()) {
                includedItemsText.setVisibility(View.VISIBLE);
                String includedText = "Includes: " + String.join(", ", item.getIncludedProducts());
                includedItemsText.setText(includedText);
            } else {
                includedItemsText.setVisibility(View.GONE);
            }

            // Load image using Glide
            String imageUrl = item.getImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.error_image)
                    .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.error_image);
            }

            // Setup click listeners
            decreaseButton.setOnClickListener(v -> {
                int newQuantity = item.getQuantity() - 1;
                if (newQuantity >= 1) {
                    listener.onQuantityChanged(position, newQuantity);
                }
            });

            increaseButton.setOnClickListener(v -> {
                int newQuantity = item.getQuantity() + 1;
                listener.onQuantityChanged(position, newQuantity);
            });

            deleteButton.setOnClickListener(v -> listener.onRemoveItem(position));
        }
    }
}
