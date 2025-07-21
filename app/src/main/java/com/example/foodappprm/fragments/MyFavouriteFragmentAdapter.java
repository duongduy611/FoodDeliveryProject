package com.example.foodappprm.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyFavouriteFragmentAdapter extends FragmentStateAdapter {
    public MyFavouriteFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new MyFavouriteFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
