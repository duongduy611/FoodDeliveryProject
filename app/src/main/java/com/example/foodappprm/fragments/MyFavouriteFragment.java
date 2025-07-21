package com.example.foodappprm.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodappprm.R;
import com.example.foodappprm.adapters.FeaturedVerAdapter;
import com.example.foodappprm.Models.FeaturedVerModel;
import com.example.foodappprm.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MyFavouriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private FeaturedVerAdapter featuredVerAdapter;
    private List<FeaturedVerModel> featuredVerModelList;
    private List<Product> favoriteProducts;

    public MyFavouriteFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_favourite_list1, container, false);
        // Chỉ thiết lập RecyclerView và Adapter một lần duy nhất khi view được tạo
        setupRecyclerView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Tải lại và hiển thị dữ liệu mỗi khi Fragment này quay trở lại màn hình
        loadAndDisplayFavorites();
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.featured_ver_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        featuredVerModelList = new ArrayList<>();
        favoriteProducts = new ArrayList<>();

        featuredVerAdapter = new FeaturedVerAdapter(featuredVerModelList, position -> {
            if (favoriteProducts != null && position < favoriteProducts.size()) {
                Product clickedProduct = favoriteProducts.get(position);
                navigateToProductDetail(clickedProduct);
            }
        });
        recyclerView.setAdapter(featuredVerAdapter);
    }

    private void loadAndDisplayFavorites() {
        this.favoriteProducts = getFavoriteProductsFromManager();
        this.featuredVerModelList.clear();

        for (Product product : this.favoriteProducts) {
            int imageId = getResourceIdByName(getContext(), product.getImage());
            this.featuredVerModelList.add(new FeaturedVerModel(
                    imageId,
                    product.getName(),
                    product.getDescription_long(),
                    String.valueOf(product.getRating()),
                    "10:00 - 22:00" // Giờ ví dụ
            ));
        }
        featuredVerAdapter.notifyDataSetChanged();
    }

    private List<Product> getFavoriteProductsFromManager() {
        Set<String> favoriteIds = FavoritesManager.getFavoriteIds();

        List<Product> allProducts = getAllSampleProducts();
        List<Product> result = new ArrayList<>();
        for (Product product : allProducts) {
            if (favoriteIds.contains(product.getProduct_id())) {
                result.add(product);
            }
        }
        return result;
    }

    private void navigateToProductDetail(Product product) {
        Bundle bundle = new Bundle();
        bundle.putString("product_id", product.getProduct_id());
        bundle.putString("name", product.getName());
        bundle.putDouble("price", product.getPrice());
        bundle.putString("image", product.getImage());
        bundle.putString("description", product.getDescription_long());

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_my_favorite_to_productDetailFragment, bundle);
    }

    private int getResourceIdByName(Context context, String resourceName) {
        if (context == null || resourceName == null) return 0;
        return context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
    }

    private List<Product> getAllSampleProducts() {
        return Arrays.asList(
                new Product("burger4", "Cheese Burger", 75000.0, "burger4", "cat_1", 4.7f, true, "Deluxe cheese burger", "Premium burger with melted cheese, bacon, and our special sauce", null, null),
                new Product("pizza3", "Supreme Pizza", 150000.0, "pizza3", "cat_2", 4.9f, true, "Supreme Special", "Loaded with vegetables and meats", null, null),
                new Product("fries3", "Cheese Fries", 40000.0, "fries3", "cat_5", 4.8f, true, "Loaded Cheese Fries", "French fries topped with melted cheese", null, null),
                new Product("sandwich1", "Club Sandwich", 55000.0, "sandwich1", "cat_3", 4.5f, true, "Classic Club", "Triple-decker sandwich with turkey, bacon, and fresh vegetables", null, null),
                new Product("pizza2", "Pepperoni Pizza", 135000.0, "pizza2", "cat_2", 4.6f, true, "Spicy Pepperoni", "Classic pizza topped with spicy pepperoni slices", null, null)
        );
    }
}