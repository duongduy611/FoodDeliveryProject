package com.example.foodappprm.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodappprm.Models.FeaturedModel;
import com.example.foodappprm.Models.FeaturedVerModel;
import com.example.foodappprm.R;
import com.example.foodappprm.adapters.FeaturedAdapter;
import com.example.foodappprm.adapters.FeaturedVerAdapter;
import com.example.foodappprm.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirstFragment extends Fragment {

    // UI Components
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private FeaturedAdapter featuredAdapter;
    private FeaturedVerAdapter featuredVerAdapter;

    // Data Lists
    private List<FeaturedModel> featuredModelList;
    private List<FeaturedVerModel> featuredVerModelList;
    private List<Product> allProducts;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        // Load sample product data
        allProducts = getSampleProducts();

        // --- Setup Horizontal RecyclerView (Featured Products) ---
        setupHorizontalRecyclerView(view);

        // --- Setup Vertical RecyclerView (All Products) ---
        setupVerticalRecyclerView(view);

        return view;
    }

    private void setupHorizontalRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.featured_hor_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        featuredModelList = new ArrayList<>();

        // Take the first 3 products as "featured"
        for (int i = 0; i < 3 && i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            int imageId = getResourceIdByName(getContext(), p.getImage());
            featuredModelList.add(new FeaturedModel(imageId, p.getName(), p.getDescription_short()));
        }

        // Initialize adapter with a click listener
        featuredAdapter = new FeaturedAdapter(featuredModelList, position -> {
            // Get the correct product from the list based on click position
            Product clickedProduct = allProducts.get(position);
            navigateToProductDetail(clickedProduct);
        });
        recyclerView.setAdapter(featuredAdapter);
    }

    private void setupVerticalRecyclerView(View view) {
        recyclerView2 = view.findViewById(R.id.featured_ver_rec);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        featuredVerModelList = new ArrayList<>();

        // Display all products in the vertical list
        for (Product product : allProducts) {
            int imageId = getResourceIdByName(getContext(), product.getImage());
            featuredVerModelList.add(new FeaturedVerModel(
                    imageId,
                    product.getName(),
                    product.getDescription_long(),
                    String.valueOf(product.getRating()),
                    "10:00 - 22:00" // Example timing
            ));
        }

        // Initialize adapter with a click listener
        featuredVerAdapter = new FeaturedVerAdapter(featuredVerModelList, position -> {
            // Get the correct product from the list based on click position
            Product clickedProduct = allProducts.get(position);
            navigateToProductDetail(clickedProduct);
        });
        recyclerView2.setAdapter(featuredVerAdapter);
    }

    /**
     * Handles navigation to the product detail screen.
     * @param product The product object to display details for.
     */
    private void navigateToProductDetail(Product product) {
        Bundle bundle = new Bundle();
        bundle.putString("product_id", product.getProduct_id());
        bundle.putString("name", product.getName());
        bundle.putDouble("price", product.getPrice());
        bundle.putString("image", product.getImage());
        bundle.putString("description", product.getDescription_long());

        // Use NavController to navigate. Ensure the action ID is correct in your nav_graph.
        // Replace 'action_firstFragment_to_productDetailFragment' with your actual action ID.
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_favorite_to_productDetailFragment, bundle);
    }

    /**
     * Gets a drawable resource ID from a string resource name.
     */
    private int getResourceIdByName(Context context, String resourceName) {
        if (context == null || resourceName == null) return 0;
        return context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
    }

    /**
     * Provides a sample list of products.
     */
    private List<Product> getSampleProducts() {
        return Arrays.asList(
                new Product("pizza2", "Pepperoni Pizza", 135000.0, "pizza2", "cat_2", 4.6f, true, "Spicy Pepperoni", "Classic pizza topped with spicy pepperoni slices", null, null),
                new Product("burger4", "Cheese Burger", 75000.0, "burger4", "cat_1", 4.7f, true, "Deluxe cheese burger", "Premium burger with melted cheese, bacon, and our special sauce", null, null),
                new Product("icecream2", "Chocolate Delight", 35000.0, "icecream2", "cat_4", 4.7f, true, "Rich Chocolate", "Rich chocolate ice cream with chocolate chips", null, null),
                new Product("burger2", "Classic Burger", 65000.0, "burger2", "cat_1", 4.5f, true, "Classic beef burger", "Our signature burger with 100% beef patty, fresh lettuce, tomatoes, and special sauce", null, null),
                new Product("pizza1", "Margherita Pizza", 120000.0, "pizza1", "cat_2", 4.8f, true, "Classic Margherita", "Traditional Italian pizza with fresh tomatoes, mozzarella, and basil", null, null),
                new Product("sandwich1", "Club Sandwich", 55000.0, "sandwich1", "cat_3", 4.5f, true, "Classic Club", "Triple-decker sandwich with turkey, bacon, and fresh vegetables", null, null),
                new Product("icecream1", "Vanilla Supreme", 35000.0, "icecream1", "cat_4", 4.5f, true, "Classic Vanilla", "Premium vanilla ice cream with natural vanilla beans", null, null),
                new Product("fries1", "Classic Fries", 30000.0, "fries1", "cat_5", 4.5f, true, "Classic French Fries", "Crispy golden french fries with sea salt", null, null)
        );
    }
}