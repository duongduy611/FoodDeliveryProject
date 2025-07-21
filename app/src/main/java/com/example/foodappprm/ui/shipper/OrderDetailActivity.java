package com.example.foodappprm.ui.shipper;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodappprm.R;

public class OrderDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        TextView tvCustomer = findViewById(R.id.tvDetailCustomer);
        TextView tvAddress = findViewById(R.id.tvDetailAddress);
        TextView tvPhone = findViewById(R.id.tvDetailPhone);
        TextView tvStatus = findViewById(R.id.tvDetailStatus);
        TextView tvPrice = findViewById(R.id.tvDetailPrice);

        // Nhận dữ liệu từ intent
        String customer = getIntent().getStringExtra("customer");
        String address = getIntent().getStringExtra("address");
        String phone = getIntent().getStringExtra("phone");
        String status = getIntent().getStringExtra("status");
        String price = getIntent().getStringExtra("price");

        tvCustomer.setText(customer);
        tvAddress.setText(address);
        tvPhone.setText(phone);
        tvStatus.setText(status);
        tvPrice.setText(price);
    }
} 