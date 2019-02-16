package com.gnufsociety.bookmarket;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gnufsociety.bookmarket.adapters.MyCardAdapter;
import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;
import com.gnufsociety.bookmarket.models.Ad;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private static final BookmarketEndpoints apiEndpoint = Api.getInstance().getApiEndpoint();
    MyCardAdapter adapter;
    @BindView(R.id.recycler_card_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.search_title_edit) EditText titleEdit;
    @BindView(R.id.search_author_edit) EditText authorEdit;
    @BindView(R.id.search_subj_edit) EditText subjectEdit;
    @BindView(R.id.search_price_edit) EditText priceEdit;
    @BindView(R.id.advanced_search_group) ConstraintLayout advancedSearch;

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
        refreshLayout.setOnRefreshListener(() -> doSearch(finalQuery, null, null, null));
        titleEdit.setText(query_text);
        doSearch(query_text, null, null, null);
    }

    public void doSearch(String query, String author, String subject, String price) {
        apiEndpoint.searchAds(query, author, subject, price).enqueue(new Callback<List<Ad>>() {
            @Override
            public void onResponse(Call<List<Ad>> call, Response<List<Ad>> response) {
                if (response.code() == 200) {
                    ArrayList<Ad> resultAds = (ArrayList<Ad>) response.body();
                    adapter = new MyCardAdapter(resultAds);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

                } else {
                    Toast.makeText(SearchActivity.this, " ERROR: " + response.code(), Toast.LENGTH_SHORT).show();
                }
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Ad>> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.search_btn)
    void doSearch() {
        doSearch(titleEdit.getText().toString(),
                 authorEdit.getText().toString(),
                 subjectEdit.getText().toString(),
                 priceEdit.getText().toString());
        advancedSearch.setVisibility(View.GONE);
    }

    @OnClick(R.id.search_title_edit)
    void showAdvancedSearch() {
        advancedSearch.setVisibility(View.VISIBLE);
    }
}
