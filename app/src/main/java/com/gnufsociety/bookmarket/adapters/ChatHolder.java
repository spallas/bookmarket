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
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.chat_username)
    TextView username;
    public @BindView(R.id.chat_time)
    TextView timestamp;

    public @BindView(R.id.chatAvatarImage)
    CircleImageView avatar;

    public @BindView(R.id.chat_lastmsg)
    TextView last_msg;

    private String chat_id;
    private String avatar_url;

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public ChatHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @OnClick
    void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ChatActivity.class);
        intent.putExtra("chat_id", this.chat_id);
        intent.putExtra("avatar_url", this.avatar_url);
        intent.putExtra("user_chat", this.username.getText().toString());
        view.getContext().startActivity(intent);

        //clicked item position
    }

}
