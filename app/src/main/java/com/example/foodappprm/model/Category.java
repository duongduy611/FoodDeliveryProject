package com.example.foodappprm.model;

public class Category {
    private String category_id;
    private String name;
    private String iconName; // Tên file drawable, ví dụ: "fried_potatoes", "hamburger", etc.

    public Category() {
        this("", "", "");
    }

    public Category(String category_id, String name, String iconName) {
        this.category_id = category_id;
        this.name = name;
        this.iconName = iconName;
    }

    // Getters
    public String getCategory_id() { return category_id; }
    public String getName() { return name; }
    public String getIconName() { return iconName; }

    // Setters
    public void setCategory_id(String category_id) { this.category_id = category_id; }
    public void setName(String name) { this.name = name; }
    public void setIconName(String iconName) { this.iconName = iconName; }
}
