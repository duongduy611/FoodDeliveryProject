package com.example.foodappprm.model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    @DocumentId
    private String id;
    private String userId;
    private List<OrderItem> items;
    private String recipientName;
    private String recipientPhone;
    private String shippingAddress;
    private double subtotal;
    private double shippingFee;
    private double total;
    private String paymentMethod;
    private String status;
    private Date orderDate;

    public Order() {
        this.items = new ArrayList<>();
    }

    public static class OrderItem {
        private String productId;
        private String productName;
        private String productImage;
        private double price;
        private int quantity;
        private double totalPrice;
        private boolean isMeal;
        private List<String> includedProducts;

        public OrderItem() {
            this.includedProducts = new ArrayList<>();
        }

        public OrderItem(Cart cartItem) {
            this.productId = cartItem.getProductId();
            this.productName = cartItem.getProductName();
            this.productImage = cartItem.getProductImage();
            this.price = cartItem.getPrice();
            this.quantity = cartItem.getQuantity();
            this.totalPrice = cartItem.getTotalPrice();
            this.isMeal = cartItem.isMeal();
            this.includedProducts = cartItem.getIncludedProducts();
        }

        // Getters and setters
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public String getProductImage() { return productImage; }
        public void setProductImage(String productImage) { this.productImage = productImage; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getTotalPrice() { return totalPrice; }
        public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
        public boolean isMeal() { return isMeal; }
        public void setMeal(boolean meal) { isMeal = meal; }
        public List<String> getIncludedProducts() { return includedProducts; }
        public void setIncludedProducts(List<String> includedProducts) {
            this.includedProducts = includedProducts != null ? includedProducts : new ArrayList<>();
        }
    }

    // Getters and Setters with proper annotations for Firestore
    @PropertyName("id")
    public String getId() { return id; }

    @PropertyName("id")
    public void setId(String id) { this.id = id; }

    @PropertyName("userId")
    public String getUserId() { return userId; }

    @PropertyName("userId")
    public void setUserId(String userId) { this.userId = userId; }

    @PropertyName("items")
    public List<OrderItem> getItems() { return items; }

    @PropertyName("items")
    public void setItems(List<OrderItem> items) { this.items = items; }

    public void setItemsFromCart(List<Cart> cartItems) {
        this.items = new ArrayList<>();
        for (Cart cart : cartItems) {
            this.items.add(new OrderItem(cart));
        }
    }

    @PropertyName("recipientName")
    public String getRecipientName() { return recipientName; }

    @PropertyName("recipientName")
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    @PropertyName("recipientPhone")
    public String getRecipientPhone() { return recipientPhone; }

    @PropertyName("recipientPhone")
    public void setRecipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; }

    @PropertyName("shippingAddress")
    public String getShippingAddress() { return shippingAddress; }

    @PropertyName("shippingAddress")
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    @PropertyName("subtotal")
    public double getSubtotal() { return subtotal; }

    @PropertyName("subtotal")
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    @PropertyName("shippingFee")
    public double getShippingFee() { return shippingFee; }

    @PropertyName("shippingFee")
    public void setShippingFee(double shippingFee) { this.shippingFee = shippingFee; }

    @PropertyName("total")
    public double getTotal() { return total; }

    @PropertyName("total")
    public void setTotal(double total) { this.total = total; }

    @PropertyName("paymentMethod")
    public String getPaymentMethod() { return paymentMethod; }

    @PropertyName("paymentMethod")
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    @PropertyName("status")
    public String getStatus() { return status; }

    @PropertyName("status")
    public void setStatus(String status) { this.status = status; }

    @PropertyName("orderDate")
    public Date getOrderDate() { return orderDate; }

    @PropertyName("orderDate")
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
}
