package com.mintusharma.newsapp.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mintusharma.newsapp.OnClickListner;
import com.mintusharma.newsapp.R;
import com.mintusharma.newsapp.databinding.RowItemForNewsListBinding;
import com.mintusharma.newsapp.models.Article;
import com.mintusharma.newsapp.models.NewsListResponseModel;

import java.util.List;


public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder>{

    private List<Article> items1;

    private OnClickListner onClickListner;

    public NewsListAdapter(List<Article> items, OnClickListner  onClickListner){
        this.items1 = items;
        this.onClickListner = onClickListner;
    }

    public void setNewList(List<Article> items){
        this.items1 = items;
        notifyDataSetChanged();

    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new MyViewHolder(RowItemForNewsListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position){

        Article taskModel = items1.get(position);
        holder.binding.tittle.setText(taskModel.getTitle());
        holder.binding.description.setText(taskModel.getDescription());
        holder.binding.date.setText(taskModel.getPublishedAt());

        Glide.with(holder.itemView.getContext())
                .load(taskModel.getUrlToImage())
                .centerCrop()
                .into(holder.binding.logo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListner.onTaskItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount(){
        return items1.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        RowItemForNewsListBinding binding;

        public MyViewHolder(RowItemForNewsListBinding b){
            super(b.getRoot());
            binding = b;
        }
    }
}