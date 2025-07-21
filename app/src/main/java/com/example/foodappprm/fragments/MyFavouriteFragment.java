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
import java.util.HashMap;
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
                new Product("burger2", "Classic Burger", 65000.0, "burger2", "cat_1", 4.5f, true, "Classic beef burger", "Our signature burger with 100% beef patty, fresh lettuce, tomatoes, and special sauce", Arrays.asList("Beef patty", "Lettuce", "Tomato", "Special sauce", "Sesame bun"), new HashMap<String, String>() {{put("calories", "550 kcal");put("protein", "25g");}}),
                new Product("burger4", "Cheese Burger", 75000.0, "burger4", "cat_1", 4.7f, true, "Deluxe cheese burger", "Premium burger with melted cheese, bacon, and our special sauce", Arrays.asList("Beef patty", "Cheddar cheese", "Bacon", "Lettuce", "Special sauce"), new HashMap<String, String>() {{put("calories", "650 kcal");put("protein", "30g");}}),

                // Pizzas
                new Product("pizza1", "Margherita Pizza", 120000.0, "pizza1", "cat_2", 4.8f, true, "Classic Margherita", "Traditional Italian pizza with fresh tomatoes, mozzarella, and basil", Arrays.asList("Tomato sauce", "Mozzarella", "Fresh basil", "Olive oil"), new HashMap<String, String>() {{put("calories", "800 kcal");put("serving", "8 slices");}}),
                new Product("pizza2", "Pepperoni Pizza", 135000.0, "pizza2", "cat_2", 4.6f, true, "Spicy Pepperoni", "Classic pizza topped with spicy pepperoni slices", Arrays.asList("Tomato sauce", "Mozzarella", "Pepperoni", "Oregano"), new HashMap<String, String>() {{put("calories", "900 kcal");put("serving", "8 slices");}}),
                new Product("pizza3", "Supreme Pizza", 150000.0, "pizza3", "cat_2", 4.9f, true, "Supreme Special", "Loaded with vegetables and meats", Arrays.asList("Tomato sauce", "Mozzarella", "Pepperoni", "Bell peppers", "Mushrooms", "Onions"), new HashMap<String, String>() {{put("calories", "950 kcal");put("serving", "8 slices");}}),
                new Product("pizza4", "BBQ Chicken Pizza", 145000.0, "pizza4", "cat_2", 4.7f, true, "BBQ Chicken Special", "Topped with grilled chicken and BBQ sauce", Arrays.asList("BBQ sauce", "Mozzarella", "Grilled chicken", "Red onions", "Cilantro"), new HashMap<String, String>() {{put("calories", "850 kcal");put("serving", "8 slices");}}),

                // Sandwiches
                new Product("sandwich1", "Club Sandwich", 55000.0, "sandwich1", "cat_3", 4.5f, true, "Classic Club", "Triple-decker sandwich with turkey, bacon, and fresh vegetables", Arrays.asList("Turkey", "Bacon", "Lettuce", "Tomato", "Mayo"), new HashMap<String, String>() {{put("calories", "450 kcal");put("protein", "28g");}}),
                new Product("sandwich2", "Grilled Cheese", 45000.0, "sandwich2", "cat_3", 4.3f, true, "Ultimate Grilled Cheese", "Melted blend of premium cheeses", Arrays.asList("Cheddar", "Mozzarella", "Butter", "Sourdough bread"), new HashMap<String, String>() {{put("calories", "400 kcal");put("protein", "15g");}}),
                new Product("sandwich3", "Veggie Delight", 50000.0, "sandwich3", "cat_3", 4.4f, true, "Fresh Veggie Sandwich", "Loaded with fresh vegetables and hummus", Arrays.asList("Hummus", "Cucumber", "Tomato", "Lettuce", "Bell peppers"), new HashMap<String, String>() {{put("calories", "350 kcal");put("protein", "12g");}}),
                new Product("sandwich4", "Chicken Sub", 60000.0, "sandwich4", "cat_3", 4.6f, true, "Grilled Chicken Sub", "Grilled chicken with fresh vegetables and special sauce", Arrays.asList("Grilled chicken", "Lettuce", "Tomato", "Onions", "Special sauce"), new HashMap<String, String>() {{put("calories", "500 kcal");put("protein", "32g");}}),
                // Ice Cream
                new Product("icecream1", "Vanilla Supreme", 35000.0, "icecream1", "cat_4", 4.5f, true, "Classic Vanilla", "Premium vanilla ice cream with natural vanilla beans", Arrays.asList("Vanilla beans", "Cream", "Sugar"), new HashMap<String, String>() {{put("calories", "250 kcal");put("serving", "2 scoops");}}),
                new Product("icecream2", "Chocolate Delight", 35000.0, "icecream2", "cat_4", 4.7f, true, "Rich Chocolate", "Rich chocolate ice cream with chocolate chips", Arrays.asList("Chocolate", "Chocolate chips", "Cream"), new HashMap<String, String>() {{put("calories", "280 kcal");put("serving", "2 scoops");}}),
                new Product("icecream3", "Strawberry Swirl", 35000.0, "icecream3", "cat_4", 4.6f, true, "Fresh Strawberry", "Strawberry ice cream with real fruit swirls", Arrays.asList("Strawberries", "Cream", "Sugar"), new HashMap<String, String>() {{put("calories", "230 kcal");put("serving", "2 scoops");}}),
                new Product("icecream4", "Mint Chip", 35000.0, "icecream4", "cat_4", 4.4f, true, "Mint Chocolate Chip", "Cool mint ice cream with chocolate chips", Arrays.asList("Mint", "Chocolate chips", "Cream"), new HashMap<String, String>() {{put("calories", "260 kcal");put("serving", "2 scoops");}}),
                // French Fries
                new Product("fries1", "Classic Fries", 30000.0, "fries1", "cat_5", 4.5f, true, "Classic French Fries", "Crispy golden french fries with sea salt", Arrays.asList("Potatoes", "Sea salt", "Vegetable oil"), new HashMap<String, String>() {{put("calories", "320 kcal");put("serving", "Regular size");}}),
                new Product("fries2", "Curly Fries", 35000.0, "fries2", "cat_5", 4.6f, true, "Seasoned Curly Fries", "Spiral cut fries with special seasoning", Arrays.asList("Potatoes", "Special seasoning", "Vegetable oil"), new HashMap<String, String>() {{put("calories", "350 kcal");put("serving", "Regular size");}}),
                new Product("fries3", "Cheese Fries", 40000.0, "fries3", "cat_5", 4.8f, true, "Loaded Cheese Fries", "French fries topped with melted cheese", Arrays.asList("Potatoes", "Cheese sauce", "Green onions"), new HashMap<String, String>() {{put("calories", "450 kcal");put("serving", "Regular size");}}),
                new Product("fries4", "Spicy Fries", 35000.0, "fries4", "cat_5", 4.7f, true, "Spicy Season Fries", "French fries with spicy seasoning blend", Arrays.asList("Potatoes", "Spicy seasoning", "Vegetable oil"), new HashMap<String, String>() {{put("calories", "330 kcal");put("serving", "Regular size");}})
        );
    }
}