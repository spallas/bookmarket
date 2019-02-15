package com.gnufsociety.bookmarket;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gnufsociety.bookmarket.models.Ad;
import com.google.firebase.auth.FirebaseAuth;

public class AdActivity extends AppCompatActivity {

    @BindView(R.id.ad_img) ImageView adImg;
    @BindView(R.id.ad_title) TextView adTitle;
    @BindView(R.id.ad_author) TextView adAuthor;
    @BindView(R.id.ad_price) TextView adPrice;
    @BindView(R.id.ad_desc) TextView adDesc;
    @BindView(R.id.ad_seller) TextView adSeller;
    @BindView(R.id.ad_propic) CircleImageView adSellerPic;
    @BindView(R.id.ad_contact_btn) Button contactBtn;
    Ad ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        ButterKnife.bind(this);

        ad = (Ad) getIntent().getExtras().getSerializable("ad");

        adTitle.setText(ad.getBook().getTitle());
        adAuthor.setText(ad.getBook().getAuthor());
        adPrice.setText("€" + ad.getPrice());
        adDesc.setText(ad.getDescription());
        adSeller.setText(ad.getUser().getUsername());

        Glide.with(this)
                .load(ad.getImgUrl())
                .into(adImg);

        Glide.with(this)
                .load(ad.getUser().getAvatar_url())
                .into(adSellerPic);
    }

    @OnClick(R.id.ad_contact_btn)
    void openNewChat() {
        String myUid = FirebaseAuth.getInstance().getUid();
        // TODO
    }
}
