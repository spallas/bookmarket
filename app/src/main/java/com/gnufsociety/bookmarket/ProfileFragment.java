package com.gnufsociety.bookmarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gnufsociety.bookmarket.adapters.MyCardAdapter;
import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;
import com.gnufsociety.bookmarket.models.Ad;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private BookmarketEndpoints apiEndpoint = Api.getInstance().getApiEndpoint();
    private MyCardAdapter adapter;
    private String myUsername;
    private String myAvatar;

    @BindView(R.id.logout_btn) ImageButton logoutBtn;
    @BindView(R.id.profile_ads) RecyclerView myAdsRecycler;
    @BindView(R.id.profile_pic) CircleImageView profileImg;
    @BindView(R.id.profile_username) TextView usernameTxt;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ProfileFragment.
     */
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, rootView);
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String avatar = (String) dataSnapshot.child("avatar").getValue();
                String username = (String) dataSnapshot.child("username").getValue();

                myAvatar = avatar;
                myUsername = username;

                usernameTxt.setText(username);
                Glide.with(getContext())
                        .load(avatar)
                        .into(profileImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       loadMyAds();

        return rootView;
    }

    public void loadMyAds(){
        apiEndpoint.getMyAds().enqueue(new Callback<List<Ad>>() {
            @Override
            public void onResponse(Call<List<Ad>> call, Response<List<Ad>> response) {
                // Log.e("PROFILE MY ADS", Utils.bodyToString());
                if (response.code() == 200) {
                    Log.d("PROFILE FRAGMENT", "Returning your ads!");
                    ArrayList<Ad> myAds = (ArrayList<Ad>) response.body();
                    adapter = new MyCardAdapter(myAds);
                    myAdsRecycler.setAdapter(adapter);
                    myAdsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    Toast.makeText(getContext(), " ERROR: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ad>> call, Throwable t) {
                Toast.makeText(getActivity(),
                        "Something went wrong... Error message: " + t.getMessage(),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }


    @OnClick(R.id.logout_btn)
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), HomeActivity.class);
        getActivity().finish();
        startActivity(intent);
    }

    @OnClick(R.id.edit_btn)
    void editUser(){
        Intent i = new Intent(getContext(), CompleteActivity.class);
        i.putExtra("edit", true);
        i.putExtra("username", myUsername);
        i.putExtra("avatar", myAvatar);
        startActivity(i);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
