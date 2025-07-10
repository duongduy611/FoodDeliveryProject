package com.example.foodappprm.adapter;

import android.text.TextUtils;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Cart> cartItems;
    private CartItemClickListener listener;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
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

    public void updateCartItems(List<Cart> newItems) {
        this.cartItems = newItems;
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameText;
        private TextView priceText;
        private TextView quantityText;
        private TextView mealLabel;
        private TextView includedItems;
        private ImageButton decreaseBtn;
        private ImageButton increaseBtn;
        private ImageButton removeBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cartItemImage);
            nameText = itemView.findViewById(R.id.cartItemName);
            priceText = itemView.findViewById(R.id.cartItemPrice);
            quantityText = itemView.findViewById(R.id.quantity);
            mealLabel = itemView.findViewById(R.id.mealLabel);
            includedItems = itemView.findViewById(R.id.includedItems);
            decreaseBtn = itemView.findViewById(R.id.decreaseQuantity);
            increaseBtn = itemView.findViewById(R.id.increaseQuantity);
            removeBtn = itemView.findViewById(R.id.removeItem);
        }

        public void bind(Cart item, int position) {
            nameText.setText(item.getName());
            priceText.setText(String.format("$%.2f", item.getPrice()));
            quantityText.setText(String.valueOf(item.getQuantity()));

            // Hiển thị thông tin meal nếu là combo
            if (item.isMeal()) {
                mealLabel.setVisibility(View.VISIBLE);
                includedItems.setVisibility(View.VISIBLE);
                String includedText = "Includes: " + TextUtils.join(", ", item.getIncludedProducts());
                includedItems.setText(includedText);
            } else {
                mealLabel.setVisibility(View.GONE);
                includedItems.setVisibility(View.GONE);
            }

            Glide.with(itemView.getContext())
                    .load(item.getImage())
                    .placeholder(R.drawable.ic_menu_gallery)
                    .into(imageView);

            decreaseBtn.setOnClickListener(v -> {
                if (item.getQuantity() > 1) {
                    listener.onQuantityChanged(position, item.getQuantity() - 1);
                }
            });

            increaseBtn.setOnClickListener(v -> {
                listener.onQuantityChanged(position, item.getQuantity() + 1);
            });

            removeBtn.setOnClickListener(v -> {
                listener.onRemoveItem(position);
            });
        }
    }
}
