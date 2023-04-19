package com.mintusharma.newsapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mintusharma.newsapp.R;
import com.mintusharma.newsapp.databinding.FragmentNewsListBinding;
import com.mintusharma.newsapp.databinding.FragmentNewsOpenBinding;


public class NewsListFragment extends Fragment {

    private FragmentNewsListBinding binding;


    public NewsListFragment() {
        // Required empty public constructor
    }


    public static NewsListFragment newInstance(String param1, String param2) {
        NewsListFragment fragment = new NewsListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;

    }
}