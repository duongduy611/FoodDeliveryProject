package com.example.foodappprm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodappprm.R
import com.example.foodappprm.model.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onCategoryClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition = -1 // -1 means no selection

    fun clearSelection() {
        val oldPosition = selectedPosition
        selectedPosition = -1
        notifyItemChanged(oldPosition)
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView: ImageView = itemView.findViewById(R.id.categoryIconImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.categoryNameTextView)

        fun bind(category: Category, isSelected: Boolean) {
            nameTextView.text = category.name

            // Load icon từ drawable sử dụng resource identifier
            val resourceId = itemView.context.resources.getIdentifier(
                category.iconName,
                "drawable",
                itemView.context.packageName
            )

            if (resourceId != 0) {
                iconImageView.setImageResource(resourceId)
            } else {
                iconImageView.setImageResource(R.drawable.placeholder_image)
            }

            // Set background based on selection state
            itemView.setBackgroundResource(
                if (isSelected) R.drawable.bg_category_selected
                else R.drawable.bg_category_normal
            )

            // Update text color based on selection
            nameTextView.setTextColor(
                itemView.context.getColor(
                    if (isSelected) R.color.white
                    else R.color.black
                )
            )

            itemView.setOnClickListener {
                if (adapterPosition != selectedPosition) {
                    val oldPosition = selectedPosition
                    selectedPosition = adapterPosition
                    notifyItemChanged(oldPosition)
                    notifyItemChanged(selectedPosition)
                    onCategoryClick(category)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], position == selectedPosition)
    }

    override fun getItemCount() = categories.size
}
