package com.gnufsociety.bookmarket;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.gnufsociety.bookmarket.models.Ad;
import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private BookmarketEndpoints apiEndpoint;
    CardAdapter adapter;
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

        final String finalQuery = query_text;
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doSearch(finalQuery);
            }
        });

        doSearch(query_text);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_card_view);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void doSearch(String query) {
        Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show();
        SearchAsync async = new SearchAsync();
        async.execute();
    }

    public class SearchAsync extends AsyncTask<Void, Void, ArrayList<Ad>> {

        @Override
        protected ArrayList<Ad> doInBackground(Void... params) {
            // TODO
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Ad> ads) {
            super.onPostExecute(ads);
            adapter = new CardAdapter(ads);
            recyclerView.setAdapter(adapter);
            refreshLayout.setRefreshing(false);
        }
    }
}
