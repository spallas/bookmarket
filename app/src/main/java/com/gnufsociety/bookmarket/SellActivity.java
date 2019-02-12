package com.gnufsociety.bookmarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;
import com.gnufsociety.bookmarket.models.Ad;
import com.gnufsociety.bookmarket.models.AdPostInfo;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellActivity extends AppCompatActivity {

    private BookmarketEndpoints apiEndpoint = Api.getInstance().getApiEndpoint();

    @BindView(R.id.edit_ad_title) EditText editTitle;
    @BindView(R.id.edit_ad_desc) EditText editDesc;
    @BindView(R.id.edit_ad_subj) EditText editSubj;
    @BindView(R.id.edit_ad_author) EditText editAuthor;
    @BindView(R.id.edit_ad_price) EditText editPrice;
    @BindView(R.id.add_ad_img) ImageView adImage;
    @BindView(R.id.add_ad_btn) Button addAdBtn;

    Ad newAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.add_ad_btn)
    public void postNewAd() {

        AdPostInfo adInfo = new AdPostInfo(
                Float.valueOf(editPrice.getText().toString()),
                editDesc.getText().toString(),
                editTitle.getText().toString(),
                editAuthor.getText().toString()
        );

        apiEndpoint.createAd(adInfo).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.e("SELL ACTIVITY request", bodyToString(call.request()));
                Log.e("SELL ACTIVITY request", call.request().headers().toString());
                if (response.code() == 201) {
                    Toast.makeText(SellActivity.this, "Ad created!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SellActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else if (response.code() == 401) {
                    Toast.makeText(SellActivity.this, "401 ERROR!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SellActivity.this, " ERROR!" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Sell Activity", t.getMessage());
            }
        });

    }


    @OnClick(R.id.add_ad_img)
    public void chooseImg() {

    }


    private static String bodyToString(final Request request){

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
