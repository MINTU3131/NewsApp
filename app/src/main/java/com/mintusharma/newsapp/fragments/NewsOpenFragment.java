package com.mintusharma.newsapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mintusharma.newsapp.databinding.FragmentNewsOpenBinding;
import com.mintusharma.newsapp.models.Article;

import java.util.List;


public class NewsOpenFragment extends Fragment {

    private FragmentNewsOpenBinding binding;
    List<Article> projects1;

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