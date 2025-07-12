package com.example.foodappprm.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String id;
    private String userId;
    private String productId;
    private String productName;
    private String productImage;
    private double price;
    private int quantity;
    private double totalPrice;
    private boolean isMeal;
    private List<String> includedProducts;

    // Empty constructor for Firestore
    public Cart() {
        this.includedProducts = new ArrayList<>();
    }

    // Constructor for regular product
    public Cart(String userId, String productId, String productName, String productImage, double price, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
        this.isMeal = false;
        this.includedProducts = new ArrayList<>();
    }

    // Constructor for meal combo
    public Cart(String userId, String productId, String productName, String productImage, double price, int quantity, List<String> includedProducts) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
        this.isMeal = true;
        this.includedProducts = includedProducts != null ? includedProducts : new ArrayList<>();
    }

    // Standard getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {  // Alias for getId()
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    // Alias methods for adapter compatibility
    public String getName() {
        return productName;
    }

    public String getImage() {
        return productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.totalPrice = this.price * this.quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = this.price * this.quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isMeal() {
        return isMeal;
    }

    public void setMeal(boolean meal) {
        isMeal = meal;
    }

    public List<String> getIncludedProducts() {
        return includedProducts != null ? includedProducts : new ArrayList<>();
    }

    public void setIncludedProducts(List<String> includedProducts) {
        this.includedProducts = includedProducts != null ? includedProducts : new ArrayList<>();
    }
}
