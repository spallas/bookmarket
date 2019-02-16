package com.gnufsociety.bookmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gnufsociety.bookmarket.adapters.GoogleBookAdapter;
import com.gnufsociety.bookmarket.api.GoogleApi;
import com.gnufsociety.bookmarket.api.GoogleBooksEndpoints;
import com.gnufsociety.bookmarket.models.Ad;
import com.gnufsociety.bookmarket.models.GoogleBook;
import com.gnufsociety.bookmarket.models.firebase.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdActivity extends AppCompatActivity {

    @BindView(R.id.ad_img) ImageView adImg;
    @BindView(R.id.my_ad_title) TextView adTitle;
    @BindView(R.id.my_ad_author) TextView adAuthor;
    @BindView(R.id.ad_price) TextView adPrice;
    @BindView(R.id.my_ad_desc) TextView adDesc;
    @BindView(R.id.ad_seller) TextView adSeller;
    @BindView(R.id.my_ad_propic) CircleImageView adSellerPic;
    @BindView(R.id.ad_contact_btn) Button contactBtn;
    @BindView(R.id.google_book_recycler) RecyclerView booksRecycler;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false);
        booksRecycler.setLayoutManager(layoutManager);
        loadGoogleBook();

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
        String notMyUid = ad.getUser().getFirebase_id();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

        ref.child(myUid).child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String chatId = dataSnapshot.getRef().push().getKey();
                boolean found = false;
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    Chat c = snap.getValue(Chat.class);
                    if (c.getUid().equals(notMyUid)){
                        chatId = snap.getKey();
                        found = true;
                        break;
                    }
                }
                if (!found){
                    Chat chat = new Chat();
                    chat.setUid(notMyUid);
                    chat.setTimestamp(System.currentTimeMillis());

                    ref.child(myUid).child("chats").child(chatId).setValue(chat);

                    chat.setUid(myUid);

                    ref.child(notMyUid).child("chats").child(chatId).setValue(chat);
                }

                Intent intent = new Intent(AdActivity.this,  ChatActivity.class);
                intent.putExtra("chat_id", chatId);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("AD ACTIVITY", "Start chat error: "+databaseError.getMessage());
            }
        });
    }


    private void loadGoogleBook(){

        GoogleBooksEndpoints googleApiEndPoint = GoogleApi.getInstance().getApiEndpoint();
        String query = this.ad.getBook().getTitle()+" "+this.ad.getBook().getAuthor();
        googleApiEndPoint.getBooks(query, 5).enqueue(new Callback<GoogleBook.GoogleBookList>() {
            @Override
            public void onResponse(Call<GoogleBook.GoogleBookList> call, Response<GoogleBook.GoogleBookList> response) {
                Log.e("TAG", "Response from google "+response.code());

                switch (response.code()) {
                    case 200:
                        GoogleBookAdapter adapter = new GoogleBookAdapter(response.body().getItems());

                        booksRecycler.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<GoogleBook.GoogleBookList> call, Throwable t) {
                Log.e("TAG", "ERRRRROR: "+t.getMessage());

            }
        });

    }
}
