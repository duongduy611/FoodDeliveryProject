package com.example.foodappprm.ui.shipper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodappprm.R;
import com.example.foodappprm.model.Order;
import java.util.List;

public class OrderShipperAdapter extends RecyclerView.Adapter<OrderShipperAdapter.OrderViewHolder> {
    private List<Order> orders;
    private Context context;

    public OrderShipperAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_order_shipper, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.tvOrderCustomer.setText(order.getRecipientName());
        holder.tvOrderAddress.setText(order.getShippingAddress());
        holder.tvOrderPhone.setText(order.getRecipientPhone());
        holder.tvOrderStatus.setText(order.getStatus());
        holder.tvOrderPrice.setText(String.format("%,.0fđ", order.getTotal()));
        holder.btnMarkDelivered.setOnClickListener(v -> {
            // TODO: Xử lý xác nhận giao hàng
        });
        // Sự kiện click vào item để xem chi tiết
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("customer", order.getRecipientName());
            intent.putExtra("address", order.getShippingAddress());
            intent.putExtra("phone", order.getRecipientPhone());
            intent.putExtra("status", order.getStatus());
            intent.putExtra("price", String.format("%,.0fđ", order.getTotal()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderCustomer, tvOrderAddress, tvOrderPhone, tvOrderStatus, tvOrderPrice;
        Button btnMarkDelivered;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderCustomer = itemView.findViewById(R.id.tvOrderCustomer);
            tvOrderAddress = itemView.findViewById(R.id.tvOrderAddress);
            tvOrderPhone = itemView.findViewById(R.id.tvOrderPhone);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderPrice = itemView.findViewById(R.id.tvOrderPrice);
            btnMarkDelivered = itemView.findViewById(R.id.btnMarkDelivered);
        }
    }
} 