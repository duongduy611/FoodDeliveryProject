package com.example.foodappprm.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodappprm.R;
import com.example.foodappprm.adapters.CartAdapter;
import com.example.foodappprm.model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment implements CartAdapter.CartItemListener {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Cart> cartItems;
    private TextView totalPriceTextView;
    private DatabaseReference cartRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        recyclerView = view.findViewById(R.id.cartRecyclerView);
        totalPriceTextView = view.findViewById(R.id.totalAmount);

        cartItems = new ArrayList<>();
        cartAdapter = new CartAdapter(getContext(), cartItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cartAdapter);

        // Initialize Firebase references
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        cartRef = FirebaseDatabase.getInstance().getReference("userCarts").child(userId);

        loadCartItems();

        return view;
    }

    private void loadCartItems() {
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartItems.clear();
                double total = 0;

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Cart cartItem = itemSnapshot.getValue(Cart.class);
                    if (cartItem != null) {
                        cartItems.add(cartItem);
                        total += cartItem.getTotalPrice();
                    }
                }

                cartAdapter.notifyDataSetChanged();
                totalPriceTextView.setText(String.format("Total: $%.2f", total));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    @Override
    public void onQuantityChanged(Cart item, int newQuantity) {
        if (newQuantity <= 0) {
            // Remove item if quantity is 0
            cartRef.child(item.getItemId()).removeValue();
        } else {
            // Update quantity
            item.setQuantity(newQuantity);
            cartRef.child(item.getItemId()).setValue(item);
        }
    }

    @Override
    public void onRemoveItem(Cart item) {
        cartRef.child(item.getItemId()).removeValue();
    }
}
