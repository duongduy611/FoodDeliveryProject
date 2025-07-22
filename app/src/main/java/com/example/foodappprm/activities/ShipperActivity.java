package com.example.foodappprm.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodappprm.R;
import com.example.foodappprm.ui.shipper.ShipperFragment;

public class ShipperActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.shipper_fragment_container, new ShipperFragment())
                .commit();
        }
    }
} 