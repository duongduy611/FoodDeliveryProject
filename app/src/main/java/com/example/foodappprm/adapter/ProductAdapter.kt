package com.example.foodappprm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodappprm.R
import com.example.foodappprm.model.Product

class ProductAdapter(
    private val products: List<Product>,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.productDescriptionTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.productImageView)
        private val ratingTextView: TextView = itemView.findViewById(R.id.productRatingTextView)

        fun bind(product: Product) {
            nameTextView.text = product.name
            priceTextView.text = itemView.context.getString(R.string.price_format, product.price)
            descriptionTextView.text = product.description_short
            ratingTextView.text = itemView.context.getString(R.string.rating_format, product.rating)

            // Load image from drawable
            val imageName = product.image.replace(".jpg", "")
            try {
                val drawableId = R.drawable::class.java.getField(imageName).getInt(null)
                imageView.setImageResource(drawableId)
            } catch (e: Exception) {
                imageView.setImageResource(R.drawable.placeholder_image)
            }

            itemView.setOnClickListener {
                onProductClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size
}
