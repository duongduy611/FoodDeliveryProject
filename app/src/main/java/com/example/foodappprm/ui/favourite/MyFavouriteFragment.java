package com.example.foodappprm.ui.favourite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodappprm.R;
import com.example.foodappprm.fragments.FragmentAdapter;
import com.example.foodappprm.fragments.MyFavouriteFragmentAdapter;

public class MyFavouriteFragment extends Fragment {

    ViewPager2 viewPager2;
    MyFavouriteFragmentAdapter fragmentAdapter;
    private FragmentAdapter myContext;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_favourite, container, false);
        viewPager2 = root.findViewById(R.id.view_pager2);
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        fragmentAdapter = new MyFavouriteFragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);

        return root;
    }
}
