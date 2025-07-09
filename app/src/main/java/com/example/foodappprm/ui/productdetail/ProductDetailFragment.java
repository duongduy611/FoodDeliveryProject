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
import com.example.foodappprm.model.Product;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
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

        addToCartButton.setOnClickListener(v -> {
            showMessage("Added " + quantity + " item(s) to cart");
        });

        updateQuantityDisplay();
    }

    private void updateQuantityDisplay() {
        if (quantityTextView != null) {
            quantityTextView.setText(String.valueOf(quantity));
        }
    }

    private void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void navigateBack() {
        NavHostFragment.findNavController(this).navigateUp();
    }
}
