package com.gnufsociety.bookmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.gnufsociety.bookmarket.adapters.MessageHolder;
import com.gnufsociety.bookmarket.models.firebase.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {


    @BindView(R.id.recycle_message)
    RecyclerView recyclerView;

    @BindView(R.id.edit_send)
    EditText messageEdit;

    private FirebaseRecyclerAdapter adapter;
    private String chat_id;
    private String user_chat;
    private DatabaseReference dbReference;
    private LinearLayoutManager linearLayoutManager;
    private final int VIEW_TYPE_SENT = 0;
    private final int VIEW_TYPE_RECV = 1;

    @OnClick(R.id.send_btn)
    public void sendMessage(View view){
        String message = messageEdit.getText().toString();
        if (!message.equals("")){
            Toast.makeText(view.getContext(), "Ma vaffanculo", Toast.LENGTH_SHORT).show();
            Message msg = new Message(Calendar.getInstance().getTimeInMillis(), message, FirebaseAuth.getInstance().getCurrentUser().getUid());

            dbReference.child("chats").child(chat_id).push().setValue(msg);
            messageEdit.setText("");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        chat_id = intent.getStringExtra("chat_id");
        user_chat = intent.getStringExtra("user_chat");

        ButterKnife.bind(this);

        Toast.makeText(this, user_chat, Toast.LENGTH_LONG).show();


        this.dbReference = FirebaseDatabase.getInstance().getReference();

        Query query = dbReference
                .child("chats").child(chat_id).orderByChild("timestamp")
                .limitToFirst(50);

        SnapshotParser<Message> parser = new SnapshotParser<Message>() {
            @NonNull
            @Override
            public Message parseSnapshot(@NonNull DataSnapshot snapshot) {
                Message message = snapshot.getValue(Message.class);
                if (message == null)
                    return new Message();
                message.setId(snapshot.getKey());

                return message;
            }
        };

        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(query, parser)
                        .build();

        linearLayoutManager = new LinearLayoutManager(this);

        adapter = new FirebaseRecyclerAdapter<Message, MessageHolder>(options) {

            @Override
            public int getItemViewType(int position) {
                Message m = (Message) adapter.getItem(position);
                if (m.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    return VIEW_TYPE_SENT;
                } else {
                    return VIEW_TYPE_RECV;
                }
            }

            @NonNull
            @Override
            public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view;
                if (viewType == VIEW_TYPE_RECV)
                    view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_message_recv, parent, false);
                else
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.row_message_sent, parent, false);

                return new MessageHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MessageHolder messageHolder, int i, @NonNull Message message) {
                messageHolder.message.setText(message.getMessage());
                messageHolder.time.setText(message.toPrettyTime());
            }
        };
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = adapter.getItemCount();
                int lastVisiblePosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);
                }
            }
        });


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                recyclerView.scrollToPosition(adapter.getItemCount()-1);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.startListening();
    }

    private void sendMessage(String message, String chat_id){

    }
}
