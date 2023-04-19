package com.mintusharma.newsapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;




public class NewsOpenFragment extends Fragment {

    private FragmentNewsOpenBinding binding;

    public NewsOpenFragment() {
        // Required empty public constructor
    }

    public static NewsOpenFragment newInstance(String param1, String param2) {
        NewsOpenFragment fragment = new NewsOpenFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsOpenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }
}