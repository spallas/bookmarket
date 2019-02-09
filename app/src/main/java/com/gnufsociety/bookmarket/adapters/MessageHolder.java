package com.gnufsociety.bookmarket.adapters;

import android.view.View;
import android.widget.TextView;

import com.gnufsociety.bookmarket.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageHolder extends RecyclerView.ViewHolder {


    public @BindView(R.id.message_text)
    TextView message;
    public @BindView(R.id.message_time)
    TextView time;


    public MessageHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }




}
