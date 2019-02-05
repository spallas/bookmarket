package com.gnufsociety.bookmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;
import com.gnufsociety.bookmarket.models.CompleteProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createSignInIntent();

    }

    public void createSignInIntent() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_launcher_foreground)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final BookmarketEndpoints apiEndpoint = Api.getInstance().getApiEndpoint();
        final Intent intent = new Intent(this, HomeActivity.class);
/*
        */

        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                apiEndpoint.existUser().enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200)
                            startActivity(intent);
                        else if (response.code() == 401) {
                            setContentView(R.layout.activity_main);

                            Button save_btn = findViewById(R.id.complete_btn);

                            save_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    EditText username_edit = findViewById(R.id.edit_username);
                                    String username = username_edit.getText().toString();
                                    if (username.equals("")){
                                        Toast.makeText(MainActivity.this, "Username non può essere vuoto", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        CompleteProfile prof = new CompleteProfile(username);
                                        apiEndpoint.completeProfile(prof).enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                if (response.code() == 201){
                                                    Toast.makeText(MainActivity.this, "Profilo completo!", Toast.LENGTH_SHORT).show();
                                                    startActivity(intent);
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {

                                            }
                                        });
                                    }

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                    }
                });
            }






            // TODO check if the user exists on rails backend, if yes jump to home activity, otherwise remain on complete profile

        } else if (resultCode == RESULT_CANCELED) {
            createSignInIntent();
        } else {
            // HANDLE ERRORS HERE???? che ce frega a noi!
        }
    }
}

