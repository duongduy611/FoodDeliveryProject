package com.example.foodappprm.ui.shipper;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.foodappprm.R;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodappprm.model.Order;
import java.util.ArrayList;
import java.util.List;

public class ShipperFragment extends Fragment {
    public ShipperFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shipper, container, false);
        RecyclerView rvOrders = view.findViewById(R.id.rvOrders);

        // Tạo danh sách đơn hàng mẫu (hoặc lấy từ database)
        List<Order> orderList = new ArrayList<>();
        Order order1 = new Order();
        order1.setRecipientName("Nguyễn Văn A");
        order1.setShippingAddress("123 Đường ABC, Quận 1");
        order1.setRecipientPhone("0901234567");
        order1.setStatus("Đang giao");
        order1.setTotal(150000);
        orderList.add(order1);

        Order order2 = new Order();
        order2.setRecipientName("Trần Thị B");
        order2.setShippingAddress("456 Đường XYZ, Quận 3");
        order2.setRecipientPhone("0912345678");
        order2.setStatus("Chờ lấy hàng");
        order2.setTotal(200000);
        orderList.add(order2);

        Order order3 = new Order();
        order3.setRecipientName("Lê Văn C");
        order3.setShippingAddress("789 Đường DEF, Quận 5");
        order3.setRecipientPhone("0987654321");
        order3.setStatus("Đã giao");
        order3.setTotal(175000);
        orderList.add(order3);

        // Truyền vào adapter
        OrderShipperAdapter adapter = new OrderShipperAdapter(orderList);
        rvOrders.setAdapter(adapter);
        rvOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        return view; 
    }
}
