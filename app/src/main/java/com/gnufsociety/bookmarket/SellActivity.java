package com.gnufsociety.bookmarket;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gnufsociety.bookmarket.models.Ad;

public class SellActivity extends AppCompatActivity {

    @BindView(R.id.edit_ad_title) EditText editTitle;
    @BindView(R.id.edit_ad_desc) EditText editDesc;
    @BindView(R.id.edit_ad_subj) EditText editSubj;
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
        // TODO
    }
}
