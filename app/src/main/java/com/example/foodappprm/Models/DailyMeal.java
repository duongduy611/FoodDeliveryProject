package com.example.foodappprm.Models;

public class DailyMeal {

    int image;
    String name;
    String discount;
    String type;
    String description;

    public DailyMeal(int image, String name, String discount, String type, String description) {
        this.image = image;
        this.name = name;
        this.discount = discount;
        this.type = type;
        this.description = description;
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
    public String getDiscount() {
        return discount;
    }
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public static class DetailedDailyMeal {

        int image;
        String name;
        String description;
        String rating;
        String price;
        String time;

        public DetailedDailyMeal(int image, String name, String description, String rating, String price, String time) {
            this.image = image;
            this.name = name;
            this.description = description;
            this.rating = rating;
            this.price = price;
            this.time = time;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }


    }
}