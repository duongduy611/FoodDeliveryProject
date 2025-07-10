package com.example.foodappprm.ui.mycart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodappprm.R;
import com.example.foodappprm.adapter.CartAdapter;
import com.example.foodappprm.model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment implements CartAdapter.CartItemClickListener {
    private RecyclerView recyclerView;
    private TextView totalPriceTextView;
    private Button checkoutButton;
    private List<Cart> cartItems;
    private CartAdapter adapter;
    private FirebaseFirestore db;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.cartRecyclerView);
        totalPriceTextView = view.findViewById(R.id.totalAmount);
        checkoutButton = view.findViewById(R.id.checkoutButton);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Setup RecyclerView
        cartItems = new ArrayList<>();
        adapter = new CartAdapter(cartItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Load cart items
        loadCartItems();

        // Setup checkout button
        checkoutButton.setOnClickListener(v -> processCheckout());

        return view;
    }

    private void loadCartItems() {
        db.collection("users").document(userId)
            .collection("cart")
            .addSnapshotListener((value, error) -> {
                if (error != null) {
                    // Handle error
                    return;
                }

                cartItems.clear();
                if (value != null) {
                    for (DocumentSnapshot doc : value.getDocuments()) {
                        Cart item = doc.toObject(Cart.class);
                        if (item != null) {
                            cartItems.add(item);
                        }
                    }
                }

                adapter.updateCartItems(cartItems);
                updateTotalPrice();
            });
    }

    private void updateTotalPrice() {
        double total = 0;
        for (Cart item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        totalPriceTextView.setText(String.format("Total: $%.2f", total));
    }

    @Override
    public void onQuantityChanged(int position, int newQuantity) {
        Cart item = cartItems.get(position);
        db.collection("users").document(userId)
            .collection("cart")
            .document(item.getItemId())
            .update("quantity", newQuantity);
    }

    @Override
    public void onRemoveItem(int position) {
        Cart item = cartItems.get(position);
        db.collection("users").document(userId)
            .collection("cart")
            .document(item.getItemId())
            .delete();
    }

    private void processCheckout() {
        // Implement checkout logic here
        // This could involve creating an order, clearing the cart, etc.
    }
}