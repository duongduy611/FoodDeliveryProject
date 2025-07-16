package com.example.foodappprm.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodappprm.Models.FeaturedModel;
import com.example.foodappprm.Models.FeaturedVerModel;
import com.example.foodappprm.R;
import com.example.foodappprm.adapters.FeaturedAdapter;
import com.example.foodappprm.adapters.FeaturedVerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {
    // Fragment horizontal revcycler view
    List<FeaturedModel> featuredModelList;
    RecyclerView recyclerView;
    FeaturedAdapter featuredAdapter;

    //featured Ver recycler view
    List<FeaturedVerModel> featuredVerModelList;
    RecyclerView recyclerView2;
    FeaturedVerAdapter featuredVerAdapter;


    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_first, container, false);

        // Fragment horizontal revcycler view

        recyclerView = view.findViewById(R.id.featured_hor_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        featuredModelList = new ArrayList<>();

        featuredModelList.add(new FeaturedModel(R.drawable.fav3, "Featured 1", "Description 1"));
        featuredModelList.add(new FeaturedModel(R.drawable.fav2, "Featured 2", "Description 2"));
        featuredModelList.add(new FeaturedModel(R.drawable.fav1, "Featured 3", "Description 3"));

        featuredAdapter = new FeaturedAdapter(featuredModelList);

        recyclerView.setAdapter(featuredAdapter);

        //featured Ver recycler view

        recyclerView2 = view.findViewById(R.id.featured_ver_rec);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        featuredVerModelList = new ArrayList<>();

        featuredVerModelList.add(new FeaturedVerModel(R.drawable.ver1, "Featured 1", "Description 1", "4.8", "10:00 - 8:00"));
        featuredVerModelList.add(new FeaturedVerModel(R.drawable.ver2, "Featured 2", "Description 2", "4.4", "11:00 - 6:00"));
        featuredVerModelList.add(new FeaturedVerModel(R.drawable.ver3, "Featured 3", "Description 3", "4.2", "10:40 - 6:00"));
        featuredVerModelList.add(new FeaturedVerModel(R.drawable.ver1, "Featured 1", "Description 1", "4.8", "10:00 - 8:00"));
        featuredVerModelList.add(new FeaturedVerModel(R.drawable.ver2, "Featured 2", "Description 2", "4.4", "11:00 - 6:00"));
        featuredVerModelList.add(new FeaturedVerModel(R.drawable.ver3, "Featured 3", "Description 3", "4.2", "10:40 - 6:00"));

        featuredVerAdapter = new FeaturedVerAdapter(featuredVerModelList);
        recyclerView2.setAdapter(featuredVerAdapter);


        return view;
    }
}