package com.example.foodappprm.ui.productdetail;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodappprm.R;
import com.example.foodappprm.model.Cart;
import com.example.foodappprm.fragments.FavoritesManager; // THÊM IMPORT NÀY
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class ProductDetailFragment extends Fragment {
    private FirebaseFirestore db;
    private int quantity = 1;

    // Các biến giao diện
    private TextView productNameTextView, productPriceTextView, descriptionTextView, ingredientsTextView, quantityTextView;
    private ImageView productImageView;
    private Button addToCartButton;
    private ImageButton decreaseButton, increaseButton;

    // BIẾN MỚI CHO CHỨC NĂNG YÊU THÍCH
    private ImageButton favoriteButton;
    private String currentProductId;
    private boolean isCurrentlyFavorite;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Cấu hình Firestore
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        db.setFirestoreSettings(settings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupBackButton();
        setupQuantityControls();

        if (getArguments() != null) {
            // Lấy thông tin sản phẩm
            currentProductId = getArguments().getString("product_id"); // Gán vào biến thành viên
            String name = getArguments().getString("name");
            double price = getArguments().getDouble("price");
            String image = getArguments().getString("image");
            String description = getArguments().getString("description");

            // Hiển thị thông tin
            productNameTextView.setText(name);
            productPriceTextView.setText(String.format("%,.0f₫", price));
            descriptionTextView.setText(description);

            int imageResourceId = getResources().getIdentifier(image, "drawable", requireContext().getPackageName());
            productImageView.setImageResource(imageResourceId != 0 ? imageResourceId : R.drawable.error_image);

            // THIẾT LẬP LOGIC CHO NÚT YÊU THÍCH
            setupFavoriteButton();

        } else {
            showError("Product not found");
            navigateBack();
        }
    }

    private void initViews(View view) {
        productNameTextView = view.findViewById(R.id.productNameTextView);
        productPriceTextView = view.findViewById(R.id.productPriceTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        ingredientsTextView = view.findViewById(R.id.ingredientsTextView);
        productImageView = view.findViewById(R.id.productImageView);
        addToCartButton = view.findViewById(R.id.addToCartButton);
        decreaseButton = view.findViewById(R.id.decreaseButton);
        increaseButton = view.findViewById(R.id.increaseButton);
        quantityTextView = view.findViewById(R.id.quantityTextView);
        // THÊM: Tìm view cho nút yêu thích
        favoriteButton = view.findViewById(R.id.favoriteButton);
    }

    // --- CÁC PHƯƠNG THỨC MỚI CHO CHỨC NĂNG YÊU THÍCH ---

    private void setupFavoriteButton() {
        if (currentProductId == null) return;
        updateFavoriteButtonState(); // Cập nhật trạng thái ban đầu
        favoriteButton.setOnClickListener(v -> {
            if (isCurrentlyFavorite) {
                // SỬA Ở ĐÂY: Xóa getContext()
                FavoritesManager.removeFavorite(currentProductId);
                Toast.makeText(getContext(), "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
            } else {
                // SỬA Ở ĐÂY: Xóa getContext()
                FavoritesManager.addFavorite(currentProductId);
                Toast.makeText(getContext(), "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
            }
            updateFavoriteButtonState(); // Cập nhật lại giao diện
        });
    }

    private void updateFavoriteButtonState() {
        if (currentProductId == null) return;
        // SỬA Ở ĐÂY: Xóa getContext()
        isCurrentlyFavorite = FavoritesManager.isFavorite(currentProductId);
        if (isCurrentlyFavorite) {
            favoriteButton.setImageResource(R.drawable.heart_icon_full);
        } else {
            favoriteButton.setImageResource(R.drawable.heart_icon);
        }
    }

    // --- CÁC PHƯƠNG THỨC CŨ GIỮ NGUYÊN ---

    private void setupBackButton() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        navigateBack();
                    }
                }
        );
    }

    private void setupQuantityControls() {
        updateQuantityDisplay();
        decreaseButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateQuantityDisplay();
            }
        });
        increaseButton.setOnClickListener(v -> {
            quantity++;
            updateQuantityDisplay();
        });
        addToCartButton.setOnClickListener(v -> addToCart());
    }

    private void updateQuantityDisplay() {
        quantityTextView.setText(String.valueOf(quantity));
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void addToCart() {
        if (currentProductId == null) {
            showError("Product information not available");
            return;
        }
        if (!isNetworkAvailable()) {
            showError("No internet connection. Please check your network and try again.");
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            showError("Please login to add items to cart");
            return;
        }
        String userId = auth.getCurrentUser().getUid();
        String name = getArguments().getString("name", "");
        double price = getArguments().getDouble("price", 0.0);
        String image = getArguments().getString("image", "");
        showLoading(true);
        db.collection("carts")
                .whereEqualTo("userId", userId)
                .whereEqualTo("productId", currentProductId)
                .get()
                .addOnSuccessListener(queryDocuments -> {
                    Cart cartItem;
                    if (!queryDocuments.isEmpty()) {
                        cartItem = queryDocuments.getDocuments().get(0).toObject(Cart.class);
                        if (cartItem != null) {
                            cartItem.setQuantity(cartItem.getQuantity() + quantity);
                        } else {
                            cartItem = new Cart(userId, currentProductId, name, image, price, quantity);
                        }
                    } else {
                        cartItem = new Cart(userId, currentProductId, name, image, price, quantity);
                    }
                    db.collection("carts")
                            .document(cartItem.getId() != null ? cartItem.getId() : db.collection("carts").document().getId())
                            .set(cartItem)
                            .addOnSuccessListener(aVoid -> {
                                showLoading(false);
                                showSuccess("Added to cart successfully");
                            })
                            .addOnFailureListener(e -> {
                                showLoading(false);
                                showError("Failed to add to cart: " + e.getMessage());
                            });
                })
                .addOnFailureListener(e -> {
                    showLoading(false);
                    showError("Failed to check cart: " + e.getMessage());
                });
    }

    private void showSuccess(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showLoading(boolean show) {
        if (addToCartButton != null) {
            addToCartButton.setEnabled(!show);
        }
    }

    private void navigateToCart() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_productDetailFragment_to_myCartFragment);
    }

    private void navigateBack() {
        NavHostFragment.findNavController(this).popBackStack();
    }
}