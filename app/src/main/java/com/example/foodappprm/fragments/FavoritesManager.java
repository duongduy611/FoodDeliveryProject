package com.example.foodappprm.fragments;

import java.util.HashSet;
import java.util.Set;

public class FavoritesManager {

    private static final Set<String> favoriteIds = new HashSet<>();

    public static Set<String> getFavoriteIds() {

        return new HashSet<>(favoriteIds);
    }

    public static void addFavorite(String productId) {
        favoriteIds.add(productId);
    }

    public static void removeFavorite(String productId) {
        favoriteIds.remove(productId);
    }

    public static boolean isFavorite(String productId) {
        return favoriteIds.contains(productId);
    }
}