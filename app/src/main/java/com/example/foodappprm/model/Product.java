package com.example.foodappprm.model;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private String product_id;
    private String name;
    private double price;
    private String image;
    private String category_id;
    private float rating;
    private boolean isAvailable;
    private String description_short;
    private String description_long;
    private List<String> ingredients;
    private Map<String, String> nutrition;

    public Product() {
        this("", "", 0.0, "", "", 0.0f, true, "", "", new ArrayList<>(), new HashMap<>());
    }

    public Product(String product_id, String name, double price, String image, String category_id,
                  float rating, boolean isAvailable, String description_short, String description_long,
                  List<String> ingredients, Map<String, String> nutrition) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.category_id = category_id;
        this.rating = rating;
        this.isAvailable = isAvailable;
        this.description_short = description_short;
        this.description_long = description_long;
        this.ingredients = ingredients;
        this.nutrition = nutrition;
    }

    // Getters
    public String getProduct_id() { return product_id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getImage() { return image; }
    public String getCategory_id() { return category_id; }
    public float getRating() { return rating; }
    public boolean isAvailable() { return isAvailable; }
    public String getDescription_short() { return description_short; }
    public String getDescription_long() { return description_long; }
    public List<String> getIngredients() { return ingredients; }
    public Map<String, String> getNutrition() { return nutrition; }

    // Setters
    public void setProduct_id(String product_id) { this.product_id = product_id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setImage(String image) { this.image = image; }
    public void setCategory_id(String category_id) { this.category_id = category_id; }
    public void setRating(float rating) { this.rating = rating; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setDescription_short(String description_short) { this.description_short = description_short; }
    public void setDescription_long(String description_long) { this.description_long = description_long; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }
    public void setNutrition(Map<String, String> nutrition) { this.nutrition = nutrition; }
}
