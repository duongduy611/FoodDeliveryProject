package com.example.foodappprm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodappprm.R
import com.example.foodappprm.model.Product
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(
    private var products: List<Product>,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.productImageView)
        private val nameText: TextView = view.findViewById(R.id.productNameTextView)
        private val priceText: TextView = view.findViewById(R.id.productPriceTextView)
        private val descriptionText: TextView = view.findViewById(R.id.productDescriptionTextView)
        private val ratingText: TextView = view.findViewById(R.id.productRatingTextView)

        fun bind(product: Product) {
            nameText.text = product.name
            descriptionText.text = product.description_short

            val formattedPrice = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                .format(product.price)
            priceText.text = formattedPrice

            ratingText.text = "Rating: ${product.rating}"

            // Load ảnh sản phẩm bằng tên file trực tiếp (không cần bỏ .jpg)
            val imageName = product.image.substringBeforeLast(".")
            val resourceId = itemView.context.resources.getIdentifier(
                imageName,
                "drawable",
                itemView.context.packageName
            )

            if (resourceId != 0) {
                imageView.setImageResource(resourceId)
            } else {
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
