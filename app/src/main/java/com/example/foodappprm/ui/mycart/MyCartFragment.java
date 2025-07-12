package com.example.foodappprm.ui.mycart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodappprm.R;
import com.example.foodappprm.adapters.CartAdapter;
import com.example.foodappprm.model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyCartFragment extends Fragment implements CartAdapter.CartItemListener {
    private List<Cart> cartItems;
    private CartAdapter adapter;
    private TextView totalPriceTextView;
    private String userId;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Get current user
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Toast.makeText(requireContext(), "Please login to view cart", Toast.LENGTH_SHORT).show();
            return view;
        }
        userId = auth.getCurrentUser().getUid();

        // Initialize views
        RecyclerView recyclerView = view.findViewById(R.id.cartRecyclerView);
        totalPriceTextView = view.findViewById(R.id.totalAmount);
        Button checkoutButton = view.findViewById(R.id.checkoutButton);

        // Setup RecyclerView
        cartItems = new ArrayList<>();
        adapter = new CartAdapter(requireContext(), cartItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        // Load cart items
        loadCartItems();

        // Setup checkout button
        checkoutButton.setOnClickListener(v -> processCheckout());

        return view;
    }

    private void loadCartItems() {
        db.collection("carts")
            .whereEqualTo("userId", userId)
            .addSnapshotListener((value, error) -> {
                if (error != null) {
                    Toast.makeText(requireContext(), "Error loading cart: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value != null) {
                    cartItems.clear();
                    for (var doc : value.getDocuments()) {
                        Cart item = doc.toObject(Cart.class);
                        if (item != null) {
                            item.setId(doc.getId());
                            cartItems.add(item);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    updateTotalPrice();
                }
            });
    }

    @Override
    public void onQuantityChanged(Cart item, int newQuantity) {
        // Cập nhật số lượng trong local list
        int position = cartItems.indexOf(item);
        if (position != -1) {
            cartItems.get(position).setQuantity(newQuantity);
            // Cập nhật tổng tiền ngay lập tức
            updateTotalPrice();
        }

        // Cập nhật lên Firestore
        db.collection("carts")
            .document(item.getId())
            .update(
                "quantity", newQuantity,
                "totalPrice", item.getPrice() * newQuantity
            )
            .addOnFailureListener(e -> Toast.makeText(requireContext(),
                "Failed to update quantity: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void updateTotalPrice() {
        double total = 0;
        for (Cart item : cartItems) {
            total += (item.getPrice() * item.getQuantity());
        }
        totalPriceTextView.setText(String.format(Locale.US, "Tổng cộng: %,.0fđ", total));
    }

    @Override
    public void onRemoveItem(Cart item) {
        int position = cartItems.indexOf(item);
        if (position != -1) {
            cartItems.remove(position);
            adapter.notifyItemRemoved(position);
            // Cập nhật tổng tiền sau khi xóa
            updateTotalPrice();
        }

        db.collection("carts")
            .document(item.getId())
            .delete()
            .addOnFailureListener(e -> Toast.makeText(requireContext(),
                "Failed to remove item: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void processCheckout() {
        if (cartItems.isEmpty()) {
            Toast.makeText(requireContext(), "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
            return;
        }
        // Navigate to checkout fragment
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_nav_my_cart_to_checkout);
    }
}