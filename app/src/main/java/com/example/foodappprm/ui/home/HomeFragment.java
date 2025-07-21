package com.example.foodappprm.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.foodappprm.adapters.ProductAdapter;
import com.example.foodappprm.model.Product;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodappprm.R;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView homeProductRecyclerView = root.findViewById(R.id.homeProductRecyclerView);
        List<Product> allProducts = new ArrayList<>(Arrays.asList(
                // Burgers
                new Product("burger2", "Classic Burger", 65000.0, "burger2", "cat_1", 4.5f, true, "Classic beef burger", "Our signature burger with 100% beef patty, fresh lettuce, tomatoes, and special sauce", Arrays.asList("Beef patty", "Lettuce", "Tomato", "Special sauce", "Sesame bun"), new HashMap<>()),
                new Product("burger4", "Cheese Burger", 75000.0, "burger4", "cat_1", 4.7f, true, "Deluxe cheese burger", "Premium burger with melted cheese, bacon, and our special sauce", Arrays.asList("Beef patty", "Cheddar cheese", "Bacon", "Lettuce", "Special sauce"), new HashMap<>()),
                // Pizzas
                new Product("pizza1", "Margherita Pizza", 120000.0, "pizza1", "cat_2", 4.8f, true, "Classic Margherita", "Traditional Italian pizza with fresh tomatoes, mozzarella, and basil", Arrays.asList("Tomato sauce", "Mozzarella", "Fresh basil", "Olive oil"), new HashMap<>()),
                new Product("pizza2", "Pepperoni Pizza", 135000.0, "pizza2", "cat_2", 4.6f, true, "Spicy Pepperoni", "Classic pizza topped with spicy pepperoni slices", Arrays.asList("Tomato sauce", "Mozzarella", "Pepperoni", "Oregano"), new HashMap<>()),
                new Product("pizza3", "Supreme Pizza", 150000.0, "pizza3", "cat_2", 4.9f, true, "Supreme Special", "Loaded with vegetables and meats", Arrays.asList("Tomato sauce", "Mozzarella", "Pepperoni", "Bell peppers", "Mushrooms", "Onions"), new HashMap<>()),
                new Product("pizza4", "BBQ Chicken Pizza", 145000.0, "pizza4", "cat_2", 4.7f, true, "BBQ Chicken Special", "Topped with grilled chicken and BBQ sauce", Arrays.asList("BBQ sauce", "Mozzarella", "Grilled chicken", "Red onions", "Cilantro"), new HashMap<>()),
                // Sandwiches
                new Product("sandwich1", "Club Sandwich", 55000.0, "sandwich1", "cat_3", 4.5f, true, "Classic Club", "Triple-decker sandwich with turkey, bacon, and fresh vegetables", Arrays.asList("Turkey", "Bacon", "Lettuce", "Tomato", "Mayo"), new HashMap<>()),
                new Product("sandwich2", "Grilled Cheese", 45000.0, "sandwich2", "cat_3", 4.3f, true, "Ultimate Grilled Cheese", "Melted blend of premium cheeses", Arrays.asList("Cheddar", "Mozzarella", "Butter", "Sourdough bread"), new HashMap<>()),
                new Product("sandwich3", "Veggie Delight", 50000.0, "sandwich3", "cat_3", 4.4f, true, "Fresh Veggie Sandwich", "Loaded with fresh vegetables and hummus", Arrays.asList("Hummus", "Cucumber", "Tomato", "Lettuce", "Bell peppers"), new HashMap<>()),
                new Product("sandwich4", "Chicken Sub", 60000.0, "sandwich4", "cat_3", 4.6f, true, "Grilled Chicken Sub", "Grilled chicken with fresh vegetables and special sauce", Arrays.asList("Grilled chicken", "Lettuce", "Tomato", "Onions", "Special sauce"), new HashMap<>()),
                // Ice Cream
                new Product("icecream1", "Vanilla Supreme", 35000.0, "icecream1", "cat_4", 4.5f, true, "Classic Vanilla", "Premium vanilla ice cream with natural vanilla beans", Arrays.asList("Vanilla beans", "Cream", "Sugar"), new HashMap<>()),
                new Product("icecream2", "Chocolate Delight", 35000.0, "icecream2", "cat_4", 4.7f, true, "Rich Chocolate", "Rich chocolate ice cream with chocolate chips", Arrays.asList("Chocolate", "Chocolate chips", "Cream"), new HashMap<>()),
                new Product("icecream3", "Strawberry Swirl", 35000.0, "icecream3", "cat_4", 4.6f, true, "Fresh Strawberry", "Strawberry ice cream with real fruit swirls", Arrays.asList("Strawberries", "Cream", "Sugar"), new HashMap<>()),
                new Product("icecream4", "Mint Chip", 35000.0, "icecream4", "cat_4", 4.4f, true, "Mint Chocolate Chip", "Cool mint ice cream with chocolate chips", Arrays.asList("Mint", "Chocolate chips", "Cream"), new HashMap<>()),
                // French Fries
                new Product("fries1", "Classic Fries", 30000.0, "fries1", "cat_5", 4.5f, true, "Classic French Fries", "Crispy golden french fries with sea salt", Arrays.asList("Potatoes", "Sea salt", "Vegetable oil"), new HashMap<>()),
                new Product("fries2", "Curly Fries", 35000.0, "fries2", "cat_5", 4.6f, true, "Seasoned Curly Fries", "Spiral cut fries with special seasoning", Arrays.asList("Potatoes", "Special seasoning", "Vegetable oil"), new HashMap<>()),
                new Product("fries3", "Cheese Fries", 40000.0, "fries3", "cat_5", 4.8f, true, "Loaded Cheese Fries", "French fries topped with melted cheese", Arrays.asList("Potatoes", "Cheese sauce", "Green onions"), new HashMap<>()),
                new Product("fries4", "Spicy Fries", 35000.0, "fries4", "cat_5", 4.7f, true, "Spicy Season Fries", "French fries with spicy seasoning blend", Arrays.asList("Potatoes", "Spicy seasoning", "Vegetable oil"), new HashMap<>())
        ));
        ProductAdapter productAdapter = new ProductAdapter(allProducts, product -> {
            Bundle bundle = new Bundle();
            bundle.putString("product_id", product.getProduct_id());
            bundle.putString("name", product.getName());
            bundle.putDouble("price", product.getPrice());
            bundle.putString("image", product.getImage());
            bundle.putString("description", product.getDescription_long());

            NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_home_to_productDetailFragment, bundle);
        });
        homeProductRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        homeProductRecyclerView.setAdapter(productAdapter);

        return root;
    }
}