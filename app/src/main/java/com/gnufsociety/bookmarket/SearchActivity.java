package com.gnufsociety.bookmarket;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.gnufsociety.bookmarket.adapters.MyCardAdapter;
import com.gnufsociety.bookmarket.models.Ad;
import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private BookmarketEndpoints apiEndpoint;
    MyCardAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        apiEndpoint = Api.getInstance().getApiEndpoint();
        String query_text = "";

        // Get the intent, verify the action and get the query_text
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query_text = intent.getStringExtra(SearchManager.QUERY);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_card_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);

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

    }


}
