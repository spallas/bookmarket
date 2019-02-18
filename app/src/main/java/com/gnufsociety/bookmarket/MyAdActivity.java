package com.gnufsociety.bookmarket;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gnufsociety.bookmarket.adapters.FullImagePopUp;
import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;
import com.gnufsociety.bookmarket.models.Ad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdActivity extends AppCompatActivity {

    @BindView(R.id.my_ad_img) ImageView adImg;
    @BindView(R.id.my_ad_title) TextView adTitle;
    @BindView(R.id.my_ad_author) TextView adAuthor;
    @BindView(R.id.my_ad_price) TextView adPrice;
    @BindView(R.id.my_ad_desc) TextView adDesc;
    Ad ad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myad);

        ButterKnife.bind(this);

        ad = (Ad) getIntent().getExtras().getSerializable("ad");

        adTitle.setText(ad.getBook().getTitle());
        adAuthor.setText(ad.getBook().getAuthor());
        adPrice.setText("€" + ad.getPrice());
        adDesc.setText(ad.getDescription());

        Glide.with(this)
                .load(ad.getImgUrl())
                .into(adImg);
    }

    @OnClick(R.id.my_ad_img)
    void goFullscreen(){
        new FullImagePopUp(this, ad.getImgUrl(),adImg);
    }

    @OnClick(R.id.ad_delete_btn)
    void deleteMyAd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Annuncio venduto");
        builder.setMessage("Sei sicuro di segnare questo annuncio come venduto? Verrà eliminato");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BookmarketEndpoints api = Api.getInstance().getApiEndpoint();
                api.deleteAd(ad.getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        switch (response.code()){
                            case 200:
                                finish();
                                break;
                            case 401:
                                Toast.makeText(getApplicationContext(), "This ad isn't yours!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("DELETE MY AD", t.getMessage());
                        Toast.makeText(getApplicationContext(), "Errore: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

    }
}
