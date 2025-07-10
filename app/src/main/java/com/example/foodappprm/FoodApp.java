package com.example.foodappprm;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;

public class FoodApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Firebase persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
