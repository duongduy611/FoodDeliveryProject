package com.example.foodappprm.Models;

public class FeaturedModel {
    int image;
    String productId;
    String name;
    String desc;

    public FeaturedModel(int image, String name, String desc) {
        this.image = image;
        this.name = name;
        this.desc = desc;
    }

    public FeaturedModel(int image, String productId, String name, String desc) {
        this.image = image;
        this.productId = productId;
        this.name = name;
        this.desc = desc;
    }

    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
