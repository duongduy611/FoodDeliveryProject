package com.example.foodappprm.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String itemId;
    private String name;
    private String image;
    private double price;
    private int quantity;
    private boolean isMeal;
    private List<String> includedProducts; // Danh sách ID của các sản phẩm trong meal

    public Cart() {
        this.includedProducts = new ArrayList<>();
    }

    // Constructor cho sản phẩm đơn lẻ
    public Cart(String itemId, String name, String image, double price, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.isMeal = false;
        this.includedProducts = new ArrayList<>();
    }

    // Constructor cho combo meal
    public Cart(String itemId, String name, String image, double price, int quantity, List<String> includedProducts) {
        this.itemId = itemId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.isMeal = true;
        this.includedProducts = includedProducts != null ? includedProducts : new ArrayList<>();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isMeal() {
        return isMeal;
    }

    public void setMeal(boolean meal) {
        isMeal = meal;
    }

    public List<String> getIncludedProducts() {
        return includedProducts;
    }

    public void setIncludedProducts(List<String> includedProducts) {
        this.includedProducts = includedProducts;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}
