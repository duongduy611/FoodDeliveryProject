package com.example.foodappprm.ui.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.foodappprm.R
import com.example.foodappprm.model.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductDetailFragment : Fragment() {
    private val db = Firebase.firestore
    private var quantity = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle back button
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        )

        val productId = arguments?.getString("productId")
        if (productId != null) {
            loadProduct(productId, view)
        } else {
            Toast.makeText(context, "Product not found", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        setupQuantityControls(view)
    }

    private fun setupQuantityControls(view: View) {
        val decreaseButton = view.findViewById<ImageButton>(R.id.decreaseButton)
        val increaseButton = view.findViewById<ImageButton>(R.id.increaseButton)
        val quantityText = view.findViewById<TextView>(R.id.quantityTextView)
        val addToCartButton = view.findViewById<Button>(R.id.addToCartButton)

        decreaseButton.setOnClickListener {
            if (quantity > 1) {
                quantity--
                quantityText.text = quantity.toString()
            }
        }

        increaseButton.setOnClickListener {
            quantity++
            quantityText.text = quantity.toString()
        }

        addToCartButton.setOnClickListener {
            Toast.makeText(context, "Added $quantity item(s) to cart", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadProduct(productId: String, view: View) {
        db.collection("products")
            .whereEqualTo("product_id", productId)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.documents[0]
                    val product = document.toObject(Product::class.java)
                    if (product != null) {
                        displayProductDetails(view, product)
                    }
                } else {
                    Toast.makeText(context, "Product not found", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error loading product: ${e.message}", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
    }

    private fun displayProductDetails(view: View, product: Product) {
        view.findViewById<TextView>(R.id.productNameTextView).text = product.name
        view.findViewById<TextView>(R.id.productPriceTextView).text =
            getString(R.string.price_format, product.price)
        view.findViewById<TextView>(R.id.descriptionTextView).text = product.description_long
        view.findViewById<TextView>(R.id.ingredientsTextView).text =
            product.ingredients.joinToString("\n") { "• $it" }
        view.findViewById<RatingBar>(R.id.ratingBar).rating = product.rating

        // Load ảnh sản phẩm bằng tên file trực tiếp (không cần bỏ .jpg)
        val imageName = product.image.substringBeforeLast(".")
        val imageView = view.findViewById<ImageView>(R.id.productImageView)
        val resourceId = requireContext().resources.getIdentifier(
            imageName,
            "drawable",
            requireContext().packageName
        )

        if (resourceId != 0) {
            imageView.setImageResource(resourceId)
        } else {
            imageView.setImageResource(R.drawable.placeholder_image)
        }

        val addToCartButton = view.findViewById<Button>(R.id.addToCartButton)
        addToCartButton.isEnabled = product.isAvailable
        if (!product.isAvailable) {
            addToCartButton.text = getString(R.string.out_of_stock)
        }
    }
}
