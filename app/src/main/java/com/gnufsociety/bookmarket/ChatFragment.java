package com.gnufsociety.bookmarket;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.gnufsociety.bookmarket.adapters.ChatHolder;
import com.gnufsociety.bookmarket.models.firebase.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    public @BindView(R.id.recycle_chat)
    RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Chat, ChatHolder> adapter;

    private OnFragmentInteractionListener mListener;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ButterKnife.bind(this, rootView);


        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("users").child(uid).child("chats").orderByChild("timestamp")
                .limitToFirst(50);

        SnapshotParser<Chat> parser = new SnapshotParser<Chat>() {
            @NonNull
            @Override
            public Chat parseSnapshot(@NonNull DataSnapshot snapshot) {
                Chat chat = snapshot.getValue(Chat.class);
                if (chat == null)
                    return new Chat();
                chat.setChat_id(snapshot.getKey());
                return chat;
            }
        };

        FirebaseRecyclerOptions<Chat> options =
                new FirebaseRecyclerOptions.Builder<Chat>()
                        .setQuery(query, parser)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Chat, ChatHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ChatHolder chatHolder, int i, @NonNull Chat chat) {
                //chatHolder.username.setText(chat.getName());
                chatHolder.timestamp.setText(chat.loadFormattedDate());
                chatHolder.setChat_id(chat.getChat_id());
                chatHolder.last_msg.setText(chat.getLast_message());


                FirebaseDatabase.getInstance().getReference().child("users").child(chat.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String avatar = (String) dataSnapshot.child("avatar").getValue();
                        String username = (String) dataSnapshot.child("username").getValue();



                        chatHolder.username.setText(username);
                        Glide.with(chatHolder.username.getContext())
                                .load(avatar)
                                .into(chatHolder.avatar);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_layout, parent, false);

                return new ChatHolder(view);
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
        //        layoutManager.getOrientation());
        //recyclerView.addItemDecoration(dividerItemDecoration);
        return rootView;
        // Inflate the layout for this fragment
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        adapter.stopListening();
        super.onPause();
    }

    @Override
    public void onResume() {
        adapter.startListening();
        super.onResume();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
