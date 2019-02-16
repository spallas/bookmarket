package com.gnufsociety.bookmarket;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gnufsociety.bookmarket.models.Ad;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @OnClick(R.id.ad_edit_btn)
    void editMyAd() {

    }

    @OnClick(R.id.ad_delete_btn)
    void deleteMyAd() {

    }
}
