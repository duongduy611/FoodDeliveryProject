package com.example.foodappprm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodappprm.R;
import com.example.foodappprm.model.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products;
    private OnProductClickListener onProductClickListener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(List<Product> products, OnProductClickListener listener) {
        this.products = products;
        this.onProductClickListener = listener;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameText;
        private TextView priceText;
        private TextView descriptionText;
        private TextView ratingText;

        public ProductViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.productImageView);
            nameText = view.findViewById(R.id.productNameTextView);
            priceText = view.findViewById(R.id.productPriceTextView);
            descriptionText = view.findViewById(R.id.productDescriptionTextView);
            ratingText = view.findViewById(R.id.productRatingTextView);
        }

        public void bind(final Product product) {
            nameText.setText(product.getName());
            descriptionText.setText(product.getDescription_short());

            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            priceText.setText(format.format(product.getPrice()));

            ratingText.setText(String.format("%.1f", product.getRating()));

            String imageName = product.getImage();
            if (imageName.contains(".")) {
                imageName = imageName.substring(0, imageName.lastIndexOf("."));
            }

            int resourceId = itemView.getContext().getResources().getIdentifier(
                imageName,
                "drawable",
                itemView.getContext().getPackageName()
            );

            if (resourceId != 0) {
                imageView.setImageResource(resourceId);
            } else {
                imageView.setImageResource(R.drawable.placeholder_image);
            }

            itemView.setOnClickListener(v -> {
                if (onProductClickListener != null) {
                    onProductClickListener.onProductClick(product);
                }
            });
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateProducts(List<Product> newProducts) {
        this.products = newProducts;
        notifyDataSetChanged();
    }
}
