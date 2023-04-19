package com.mintusharma.newsapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mintusharma.newsapp.OnClickListner;
import com.mintusharma.newsapp.R;
import com.mintusharma.newsapp.RetrofitClient;
import com.mintusharma.newsapp.adapters.NewsListAdapter;
import com.mintusharma.newsapp.databinding.FragmentNewsListBinding;
import com.mintusharma.newsapp.databinding.FragmentNewsOpenBinding;
import com.mintusharma.newsapp.models.Article;
import com.mintusharma.newsapp.models.NewsListResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsListFragment extends Fragment {

    private FragmentNewsListBinding binding;
    public String country="us";
    public String category="business";
    public String apiKey="5e145a9a7a50486ead37f7cfa201feb2";
    List<Article> projects1;
    NewsListAdapter adapter;


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

        binding.progressbar.setVisibility(View.VISIBLE);

        projects1 = new ArrayList<>();

        binding.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewsListAdapter(projects1, new OnClickListner() {
            @Override
            public void onTaskItemClick(int position) {
                try {
                    NewsOpenFragment dataDetailScreenFragment = new NewsOpenFragment();
                    dataDetailScreenFragment.newInstance(projects1);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.add(R.id.container_list_frag, dataDetailScreenFragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        binding.rvList.setAdapter(adapter);
        binding.progressbar.setVisibility(View.GONE);

        getNewsList();

        return root;
    }

    private void getNewsList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                Call<NewsListResponseModel> call = RetrofitClient.getInstance().getApi().getNews(
                        country,category,apiKey);

                call.enqueue(new Callback<NewsListResponseModel>() {
                    @Override
                    public void onResponse(Call<NewsListResponseModel> call, Response<NewsListResponseModel> response) {

                        NewsListResponseModel newsListResponseModel = response.body();
                        if (response.isSuccessful()) {

                            try {
                                if (newsListResponseModel.getStatus().equals("ok")) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            projects1 = newsListResponseModel.getArticles();
                                            adapter.setNewList(projects1);
                                            binding.progressbar.setVisibility(View.GONE);
                                        }
                                    });

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "System Fail", Toast.LENGTH_SHORT).show();
                                binding.progressbar.setVisibility(View.GONE);

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<NewsListResponseModel> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        binding.progressbar.setVisibility(View.GONE);

                    }
                });
            }
        });

        thread.start();
    }

}