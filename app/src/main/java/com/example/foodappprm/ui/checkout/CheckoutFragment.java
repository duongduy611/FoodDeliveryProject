package com.example.foodappprm.ui.checkout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.foodappprm.R;
import com.example.foodappprm.model.Cart;
import com.example.foodappprm.model.Order;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CheckoutFragment extends Fragment {
    private static final double SHIPPING_FEE = 15000;

    private TextInputEditText nameEditText;
    private TextInputEditText phoneEditText;
    private TextInputEditText addressEditText;
    private TextView itemCountText;
    private TextView subtotalText;
    private TextView shippingFeeText;
    private TextView totalText;
    private Button confirmButton;

    private FirebaseFirestore db;
    private String userId;
    private List<Cart> cartItems;
    private double subtotal = 0;
    private boolean isProcessing = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        initializeViews(view);
        setupFirebase();
        loadCartItems();

        return view;
    }

    private void initializeViews(View view) {
        nameEditText = view.findViewById(R.id.nameEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        addressEditText = view.findViewById(R.id.addressEditText);
        itemCountText = view.findViewById(R.id.itemCountText);
        subtotalText = view.findViewById(R.id.subtotalText);
        shippingFeeText = view.findViewById(R.id.shippingFeeText);
        totalText = view.findViewById(R.id.totalText);
        confirmButton = view.findViewById(R.id.confirmButton);

        shippingFeeText.setText(String.format(Locale.US, "Phí giao hàng: %,.0fđ", SHIPPING_FEE));
        confirmButton.setOnClickListener(v -> processOrder());
    }

    private void setupFirebase() {
        db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            userId = auth.getCurrentUser().getUid();
        } else {
            Toast.makeText(requireContext(), "Vui lòng đăng nhập để tiếp tục", Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(this).popBackStack();
        }
    }

    private void loadCartItems() {
        cartItems = new ArrayList<>();
        db.collection("carts")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                subtotal = 0;
                for (var doc : queryDocumentSnapshots) {
                    Cart item = doc.toObject(Cart.class);
                    if (item != null) {
                        item.setId(doc.getId());
                        cartItems.add(item);
                        subtotal += item.getTotalPrice();
                    }
                }
                updateOrderSummary();
            })
            .addOnFailureListener(e -> {
                Toast.makeText(requireContext(),
                    "Lỗi khi tải giỏ hàng: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(this).popBackStack();
            });
    }

    private void updateOrderSummary() {
        int itemCount = 0;
        for (Cart item : cartItems) {
            itemCount += item.getQuantity();
        }

        itemCountText.setText(String.format("Số lượng sản phẩm: %d", itemCount));
        subtotalText.setText(String.format(Locale.US, "Tạm tính: %,.0fđ", subtotal));
        totalText.setText(String.format(Locale.US, "Tổng cộng: %,.0fđ", subtotal + SHIPPING_FEE));
    }

    private void processOrder() {
        if (isProcessing) {
            return; // Prevent double submission
        }

        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Vui lòng nhập họ tên");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            phoneEditText.setError("Vui lòng nhập số điện thoại");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            addressEditText.setError("Vui lòng nhập địa chỉ");
            return;
        }

        if (cartItems.isEmpty()) {
            Toast.makeText(requireContext(), "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
            return;
        }

        // Disable button and show loading state
        isProcessing = true;
        confirmButton.setEnabled(false);
        confirmButton.setText("Đang xử lý...");

        // Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setItemsFromCart(cartItems);
        order.setRecipientName(name);
        order.setRecipientPhone(phone);
        order.setShippingAddress(address);
        order.setSubtotal(subtotal);
        order.setShippingFee(SHIPPING_FEE);
        order.setTotal(subtotal + SHIPPING_FEE);
        order.setPaymentMethod("COD");
        order.setStatus("Pending");
        order.setOrderDate(new Date());

        // Save to Firestore
        db.collection("orders")
            .add(order)
            .addOnSuccessListener(documentReference -> {
                clearCart(() -> {
                    Toast.makeText(requireContext(), "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(this).popBackStack();
                });
            })
            .addOnFailureListener(e -> {
                isProcessing = false;
                confirmButton.setEnabled(true);
                confirmButton.setText("Xác nhận đặt hàng");
                Toast.makeText(requireContext(),
                    "Lỗi khi đặt hàng: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
    }

    private void clearCart(Runnable onComplete) {
        if (cartItems.isEmpty()) {
            onComplete.run();
            return;
        }

        int[] completedCount = {0};
        int totalCount = cartItems.size();

        for (Cart item : cartItems) {
            db.collection("carts")
                .document(item.getId())
                .delete()
                .addOnCompleteListener(task -> {
                    completedCount[0]++;
                    if (completedCount[0] >= totalCount) {
                        onComplete.run();
                    }
                });
        }
    }
}
