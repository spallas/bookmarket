package com.gnufsociety.bookmarket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;
import com.gnufsociety.bookmarket.models.BMUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private int this_user_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BookmarketEndpoints apiEndpoint = Api.getInstance().getApiEndpoint();

        Call<List<BMUser>> call = apiEndpoint.allUsers(/*give user id to api path*/ );
/*
        call.enqueue(new Callback<List<BMUser>>() {
            @Override
            public void onResponse(Call<List<BMUser>> call, Response<List<BMUser>> response) {
                Toast.makeText(HomeActivity.this,
                        response.body().get(this_user_id).user_id,
                        Toast.LENGTH_SHORT);

            }

            @Override
            public void onFailure(Call<List<BMUser>> call, Throwable t) {
                Toast.makeText(HomeActivity.this,
                        "Something went wrong...Error message: " + t.getMessage(),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }); */
    }

}
