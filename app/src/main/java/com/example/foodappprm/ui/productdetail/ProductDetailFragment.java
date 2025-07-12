package com.example.foodappprm.ui.productdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.foodappprm.R;
import com.example.foodappprm.model.Cart;
import com.example.foodappprm.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.text.TextUtils;

public class ProductDetailFragment extends Fragment {
    private FirebaseFirestore db;
    private int quantity = 1;
    private TextView productNameTextView;
    private TextView productPriceTextView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;
    private ImageView productImageView;
    private Button addToCartButton;
    private ImageButton decreaseButton;
    private ImageButton increaseButton;
    private TextView quantityTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                            @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cấu hình Firestore để hỗ trợ offline
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

        // Lấy thông tin sản phẩm từ bundle
        if (getArguments() != null) {
            String productId = getArguments().getString("product_id");
            String name = getArguments().getString("name");
            double price = getArguments().getDouble("price");
            String image = getArguments().getString("image");
            String description = getArguments().getString("description");

            // Hiển thị thông tin sản phẩm
            productNameTextView.setText(name);
            productPriceTextView.setText(String.format("%,.0f₫", price));
            descriptionTextView.setText(description);

            // Lấy resource ID của hình ảnh
            String imageName = image + ".jpg";  // Thêm đuôi .jpg
            int imageResourceId = getResources().getIdentifier(image, "drawable", requireContext().getPackageName());
            if (imageResourceId != 0) {
                productImageView.setImageResource(imageResourceId);
            } else {
                productImageView.setImageResource(R.drawable.error_image);
            }
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
    }

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
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void addToCart() {
        if (getArguments() == null) {
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
        String productId = getArguments().getString("product_id", "");
        String name = getArguments().getString("name", "");
        double price = getArguments().getDouble("price", 0.0);
        String image = getArguments().getString("image", "");

        if (TextUtils.isEmpty(productId)) {
            showError("Invalid product");
            return;
        }

        showLoading(true);

        // Check if product already exists in cart
        db.collection("carts")
            .whereEqualTo("userId", userId)
            .whereEqualTo("productId", productId)
            .get()
            .addOnSuccessListener(queryDocuments -> {
                Cart cartItem;
                if (!queryDocuments.isEmpty()) {
                    // If product exists, update quantity
                    cartItem = queryDocuments.getDocuments().get(0).toObject(Cart.class);
                    if (cartItem != null) {
                        cartItem.setQuantity(cartItem.getQuantity() + quantity);
                    } else {
                        cartItem = new Cart(userId, productId, name, image, price, quantity);
                    }
                } else {
                    // If product doesn't exist, create new cart item
                    cartItem = new Cart(userId, productId, name, image, price, quantity);
                }

                // Save to Firestore
                db.collection("carts")
                    .document(cartItem.getId() != null ? cartItem.getId() : db.collection("carts").document().getId())
                    .set(cartItem)
                    .addOnSuccessListener(aVoid -> {
                        showLoading(false);
                        showSuccess("Added to cart successfully");
                        // Optionally navigate to cart
                        // navigateToCart();
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
