package com.gnufsociety.bookmarket.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.gnufsociety.bookmarket.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FullImagePopUp extends PopupWindow {

    @BindView(R.id.fullscreen_image)
    PhotoView image;

    @BindView(R.id.fullscreen_close)
    ImageButton close_btn;

    private Context context;
    private String image_url;

    public FullImagePopUp(Context context, String image_url, View v) {
        super(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.fullscreen, null, false),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this, getContentView());
        this.context = context;
        this.image_url = image_url;

        setElevation(5.0f);

        Glide.with(context)
                .load(this.image_url)
                .into(image);

        showAtLocation(v, Gravity.CENTER, 0, 0);

    }


    @OnClick(R.id.fullscreen_close)
    void closeFullscreen(){
        dismiss();
    }
}
