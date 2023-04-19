package com.mintusharma.newsapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mintusharma.newsapp.databinding.FragmentNewsOpenBinding;
import com.mintusharma.newsapp.models.Article;

import java.util.List;


public class NewsOpenFragment extends Fragment {

    private FragmentNewsOpenBinding binding;
    List<Article> articles;
    private int position;

    public NewsOpenFragment() {
        // Required empty public constructor
    }

    public NewsOpenFragment newInstance(List<Article> param1, int position) {
        NewsOpenFragment fragment = new NewsOpenFragment();
        this.articles = param1;
        this.position = position;
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

        binding.fulltittle.setText(articles.get(position).getTitle());
        binding.date.setText(articles.get(position).getPublishedAt());
        binding.author.setText(articles.get(position).getAuthor());
        binding.fulltittle.setText(articles.get(position).getTitle());
        binding.content.setText(articles.get(position).getContent());

        Glide.with(getContext())
                .load(articles.get(position).getUrlToImage())
                .centerCrop()
                .into(binding.logo);


        return root;
    }
}