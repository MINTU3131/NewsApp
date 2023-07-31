package com.mintusharma.newsapp.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mintusharma.newsapp.NewsDao;
import com.mintusharma.newsapp.OnClickListner;
import com.mintusharma.newsapp.R;
import com.mintusharma.newsapp.RetrofitClient;
import com.mintusharma.newsapp.adapters.NewsListAdapter;
import com.mintusharma.newsapp.databinding.FragmentNewsListBinding;
import com.mintusharma.newsapp.dbhandler.NewsDatabase;
import com.mintusharma.newsapp.models.Article;
import com.mintusharma.newsapp.models.NewsListResponseModel;

import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.DialogPropertiesPendulum;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum;

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
    List<Article> articles;
    NewsListAdapter adapter;
    private NewsDao newsDao;


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

        articles = new ArrayList<>();

        newsDao = NewsDatabase.getInstance(getContext()).newsDao();


        binding.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewsListAdapter(articles, new OnClickListner() {
            @Override
            public void onTaskItemClick(int position) {
                try {
                    NewsOpenFragment dataDetailScreenFragment = new NewsOpenFragment();
                    dataDetailScreenFragment.newInstance(articles,position);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.add(R.id.container_list_frag, dataDetailScreenFragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onTaskInsideItemClick(int position) {

                articles.remove(position);
                adapter.setNewList(articles);

            }
        });
        binding.rvList.setAdapter(adapter);

        checkInternetConnection();

        return root;
    }

    private void checkInternetConnection() {
        NoInternetDialogPendulum.Builder builder = new NoInternetDialogPendulum.Builder(
                getActivity(),
                getLifecycle()
        );

        DialogPropertiesPendulum properties = builder.getDialogProperties();

        properties.setConnectionCallback(new ConnectionCallback() { // Optional
            @Override
            public void hasActiveConnection(boolean hasActiveConnection) {
                if (hasActiveConnection){
                    getNewsList();
                }else {
                    Toast.makeText(getContext(), "Data is Loading From Local database", Toast.LENGTH_SHORT).show();
                    getNewsFromLocalStorage();
                }
            }
        });

        properties.setCancelable(true); // Optional
        properties.setNoInternetConnectionTitle("No Internet"); // Optional
        properties.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
        properties.setShowInternetOnButtons(true); // Optional
        properties.setPleaseTurnOnText("Please turn on"); // Optional
        properties.setWifiOnButtonText("Wifi"); // Optional
        properties.setMobileDataOnButtonText("Mobile data"); // Optional

        properties.setOnAirplaneModeTitle("No Internet"); // Optional
        properties.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
        properties.setPleaseTurnOffText("Please turn off"); // Optional
        properties.setAirplaneModeOffButtonText("Airplane mode"); // Optional
        properties.setShowAirplaneModeOffButtons(true); // Optional

        builder.build();
    }

    @SuppressLint("StaticFieldLeak")
    private void getNewsFromLocalStorage() {
        // Retrieve data from the local database using Room
        new AsyncTask<Void, Void, List<Article>>() {
            @Override
            protected List<Article> doInBackground(Void... voids) {
                return newsDao.getAllArticles();
            }

            @Override
            protected void onPostExecute(List<Article> articlesFromDb) {
                super.onPostExecute(articlesFromDb);
                articles.clear();
                articles.addAll(articlesFromDb);
                adapter.notifyDataSetChanged();
                binding.progressbar.setVisibility(View.GONE);
            }
        }.execute();
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

                                    articles = newsListResponseModel.getArticles();
                                    saveNewsToLocalStorage(articles);

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.setNewList(articles);
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

    @SuppressLint("StaticFieldLeak")
    private void saveNewsToLocalStorage(List<Article> articles) {
        new AsyncTask<Void, Void, Void>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Void... voids) {
                // Insert articles into the 'Article' table
                newsDao.insertAll(articles);


                return null;
            }
        }.execute();
    }

}