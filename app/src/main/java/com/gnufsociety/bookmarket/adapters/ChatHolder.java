package com.gnufsociety.bookmarket.adapters;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.gnufsociety.bookmarket.ChatActivity;
import com.gnufsociety.bookmarket.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.chat_username)
    TextView username;
    public @BindView(R.id.chat_time)
    TextView timestamp;

    private String chat_id;

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public ChatHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @OnClick
    void onClick(View view) {
        System.out.println(getAdapterPosition());
        System.out.println(getChat_id());
        Intent intent = new Intent(view.getContext(), ChatActivity.class);
        intent.putExtra("chat_id", getChat_id());
        intent.putExtra("user_chat", username.getText().toString());
        view.getContext().startActivity(intent);

        //clicked item position
    }

}
