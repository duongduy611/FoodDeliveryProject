package com.example.foodappprm.ui.productlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodappprm.R
import com.example.foodappprm.adapters.CategoryAdapter
import com.example.foodappprm.adapters.ProductAdapter
import com.example.foodappprm.model.Category
import com.example.foodappprm.model.Product
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductListFragment : Fragment() {
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var productsRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter
    private lateinit var allProductButton: MaterialButton
    private val products = mutableListOf<Product>()
    private val allProducts = mutableListOf<Product>()
    private val categories = mutableListOf<Category>()
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)
        setupRecyclerViews(view)
        loadSampleData()
        return view
    }

    private fun setupRecyclerViews(view: View) {
        // Setup All Products button
        allProductButton = view.findViewById(R.id.allProductButton)
        allProductButton.setOnClickListener {
            showAllProducts()
        }

        // Setup Categories RecyclerView
        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView)
        categoryAdapter = CategoryAdapter(categories) { category ->
            filterProductsByCategory(category.category_id)
        }
        categoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        // Setup Products RecyclerView
        productsRecyclerView = view.findViewById(R.id.productsRecyclerView)
        productAdapter = ProductAdapter(products) { product ->
            navigateToProductDetail(product)
        }
        productsRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = productAdapter
        }
    }

    private fun loadSampleData() {
        // Sample Categories
        categories.addAll(listOf(
            Category("cat_1", "Hamburgers", "hamburger"),
            Category("cat_2", "Pizza", "pizza"),
            Category("cat_3", "Sandwiches", "sandwich"),
            Category("cat_4", "Ice Cream", "ice_cream"),
            Category("cat_5", "French Fries", "fried_potatoes")
        ))
        categoryAdapter.notifyDataSetChanged()

        // Sample Products with all available images
        val allProducts = mutableListOf<Product>()

        // Burgers
        allProducts.addAll(listOf(
            Product(
                "burger2", "Classic Burger", 65000.0, "burger2.jpg",
                "cat_1", 4.5f, true,
                "Classic beef burger",
                "Our signature burger with 100% beef patty, fresh lettuce, tomatoes, and special sauce",
                listOf("Beef patty", "Lettuce", "Tomato", "Special sauce", "Sesame bun"),
                mapOf("calories" to "550 kcal", "protein" to "25g")
            ),
            Product(
                "burger4", "Cheese Burger", 75000.0, "burger4.jpg",
                "cat_1", 4.7f, true,
                "Deluxe cheese burger",
                "Premium burger with melted cheese, bacon, and our special sauce",
                listOf("Beef patty", "Cheddar cheese", "Bacon", "Lettuce", "Special sauce"),
                mapOf("calories" to "650 kcal", "protein" to "30g")
            )
        ))

        // Pizzas
        allProducts.addAll(listOf(
            Product(
                "pizza1", "Margherita Pizza", 120000.0, "pizza1.jpg",
                "cat_2", 4.8f, true,
                "Classic Italian pizza",
                "Traditional Italian pizza with tomatoes, mozzarella, and fresh basil",
                listOf("Tomato sauce", "Mozzarella", "Fresh basil", "Olive oil"),
                mapOf("calories" to "800 kcal", "protein" to "24g")
            ),
            Product(
                "pizza2", "Pepperoni Pizza", 135000.0, "pizza2.jpg",
                "cat_2", 4.6f, true,
                "Spicy pepperoni pizza",
                "Pizza topped with spicy pepperoni slices and melted cheese",
                listOf("Tomato sauce", "Mozzarella", "Pepperoni", "Oregano"),
                mapOf("calories" to "900 kcal", "protein" to "28g")
            ),
            Product(
                "pizza3", "Vegetarian Pizza", 125000.0, "pizza3.jpg",
                "cat_2", 4.4f, true,
                "Fresh veggie pizza",
                "Loaded with fresh vegetables and mushrooms",
                listOf("Tomato sauce", "Mozzarella", "Bell peppers", "Mushrooms", "Olives"),
                mapOf("calories" to "750 kcal", "protein" to "22g")
            ),
            Product(
                "pizza4", "Supreme Pizza", 145000.0, "pizza4.jpg",
                "cat_2", 4.9f, true,
                "Supreme combination",
                "The ultimate pizza with all premium toppings",
                listOf("Tomato sauce", "Mozzarella", "Pepperoni", "Sausage", "Vegetables"),
                mapOf("calories" to "950 kcal", "protein" to "32g")
            )
        ))

        // Sandwiches
        allProducts.addAll(listOf(
            Product(
                "sandwich1", "Club Sandwich", 55000.0, "sandwich1.jpg",
                "cat_3", 4.3f, true,
                "Classic club sandwich",
                "Triple-decker sandwich with chicken, bacon, and fresh vegetables",
                listOf("Chicken", "Bacon", "Lettuce", "Tomato", "Mayo"),
                mapOf("calories" to "450 kcal", "protein" to "22g")
            ),
            Product(
                "sandwich2", "Veggie Sandwich", 45000.0, "sandwich2.jpg",
                "cat_3", 4.2f, true,
                "Fresh vegetarian sandwich",
                "Healthy sandwich packed with fresh vegetables",
                listOf("Cucumber", "Lettuce", "Tomato", "Cheese", "Mayo"),
                mapOf("calories" to "350 kcal", "protein" to "15g")
            ),
            Product(
                "sandwich3", "Grilled Chicken", 60000.0, "sandwich3.jpg",
                "cat_3", 4.5f, true,
                "Grilled chicken sandwich",
                "Tender grilled chicken with fresh vegetables",
                listOf("Grilled chicken", "Lettuce", "Tomato", "Mayo"),
                mapOf("calories" to "400 kcal", "protein" to "25g")
            ),
            Product(
                "sandwich4", "BLT Sandwich", 50000.0, "sandwich4.jpg",
                "cat_3", 4.4f, true,
                "Bacon Lettuce Tomato",
                "Classic BLT sandwich with crispy bacon",
                listOf("Bacon", "Lettuce", "Tomato", "Mayo"),
                mapOf("calories" to "380 kcal", "protein" to "18g")
            )
        ))

        // Ice Cream
        allProducts.addAll(listOf(
            Product(
                "icecream1", "Vanilla Sundae", 35000.0, "icecream1.jpg",
                "cat_4", 4.6f, true,
                "Classic vanilla sundae",
                "Creamy vanilla ice cream with chocolate sauce",
                listOf("Vanilla ice cream", "Chocolate sauce", "Whipped cream", "Cherry"),
                mapOf("calories" to "280 kcal", "protein" to "4g")
            ),
            Product(
                "icecream2", "Chocolate Delight", 40000.0, "icecream2.jpg",
                "cat_4", 4.7f, true,
                "Rich chocolate ice cream",
                "Double chocolate ice cream with chocolate chips",
                listOf("Chocolate ice cream", "Chocolate chips", "Chocolate sauce"),
                mapOf("calories" to "320 kcal", "protein" to "5g")
            ),
            Product(
                "icecream3", "Berry Mix", 45000.0, "icecream3.jpg",
                "cat_4", 4.5f, true,
                "Mixed berry ice cream",
                "Ice cream with fresh mixed berries",
                listOf("Vanilla ice cream", "Mixed berries", "Berry sauce"),
                mapOf("calories" to "250 kcal", "protein" to "3g")
            ),
            Product(
                "icecream4", "Caramel Crunch", 42000.0, "icecream4.jpg",
                "cat_4", 4.8f, true,
                "Caramel ice cream",
                "Caramel ice cream with crunchy nuts",
                listOf("Caramel ice cream", "Nuts", "Caramel sauce"),
                mapOf("calories" to "300 kcal", "protein" to "6g")
            )
        ))

        // French Fries
        allProducts.addAll(listOf(
            Product(
                "fries1", "Classic Fries", 35000.0, "fries1.jpg",
                "cat_5", 4.5f, true,
                "Crispy classic fries",
                "Golden crispy French fries with sea salt",
                listOf("Potatoes", "Sea salt", "Vegetable oil"),
                mapOf("calories" to "320 kcal", "protein" to "4g")
            ),
            Product(
                "fries2", "Cheese Fries", 45000.0, "fries2.jpg",
                "cat_5", 4.7f, true,
                "Cheesy fries",
                "Crispy fries topped with melted cheese",
                listOf("Potatoes", "Cheese sauce", "Sea salt"),
                mapOf("calories" to "420 kcal", "protein" to "8g")
            ),
            Product(
                "fries3", "Spicy Fries", 40000.0, "fries3.jpg",
                "cat_5", 4.6f, true,
                "Spicy seasoned fries",
                "Crispy fries with spicy seasoning",
                listOf("Potatoes", "Spicy seasoning", "Sea salt"),
                mapOf("calories" to "340 kcal", "protein" to "4g")
            ),
            Product(
                "fries4", "Garlic Fries", 42000.0, "fries4.jpg",
                "cat_5", 4.4f, true,
                "Garlic parmesan fries",
                "Fries tossed with garlic and parmesan",
                listOf("Potatoes", "Garlic", "Parmesan", "Sea salt"),
                mapOf("calories" to "360 kcal", "protein" to "6g")
            )
        ))

        // Store all products in allProducts list
        this.allProducts.clear()
        this.allProducts.addAll(allProducts)

        // Show all products initially
        showAllProducts()
    }

    private fun navigateToProductDetail(product: Product) {
        val bundle = Bundle().apply {
            putString("productId", product.product_id)
        }
        findNavController().navigate(
            R.id.action_nav_product_list_to_productDetailFragment,
            bundle
        )
    }

    private fun showAllProducts() {
        products.clear()
        products.addAll(allProducts)
        productAdapter.notifyDataSetChanged()
        // Reset category selection if needed
        categoryAdapter.clearSelection()
    }

    private fun filterProductsByCategory(categoryId: String) {
        products.clear()
        products.addAll(allProducts.filter { it.category_id == categoryId })
        productAdapter.notifyDataSetChanged()
    }
}
