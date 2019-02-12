package com.gnufsociety.bookmarket;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.widget.Toast;

import com.gnufsociety.bookmarket.adapters.MyCardAdapter;
import com.gnufsociety.bookmarket.models.Ad;
import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private BookmarketEndpoints apiEndpoint = Api.getInstance().getApiEndpoint();
    MyCardAdapter adapter;
    @BindView(R.id.recycler_card_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        String query_text = "";

        // Get the intent, verify the action and get the query_text
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query_text = intent.getStringExtra(SearchManager.QUERY);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final String finalQuery = query_text;
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doSearch(finalQuery);
            }
        });

        doSearch(query_text);

    }

    public void doSearch(String query) {
        Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show();
        apiEndpoint.searchAds(query).enqueue(new Callback<List<Ad>>() {
            @Override
            public void onResponse(Call<List<Ad>> call, Response<List<Ad>> response) {
                Log.e("SEARCH ADS", Utils.bodyToString(call.request()));
                if (response.code() == 201) {
                    Toast.makeText(SearchActivity.this, "Returning your ads!", Toast.LENGTH_SHORT).show();
                    ArrayList<Ad> resultAds = (ArrayList<Ad>) response.body();
                    adapter = new MyCardAdapter(resultAds);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                } else {
                    Toast.makeText(SearchActivity.this, " ERROR! " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ad>> call, Throwable t) {

            }
        });
    }


}
