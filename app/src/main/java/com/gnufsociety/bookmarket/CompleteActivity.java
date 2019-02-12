package com.gnufsociety.bookmarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;
import com.gnufsociety.bookmarket.models.CompleteProfile;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteActivity extends AppCompatActivity {

    private BookmarketEndpoints apiEndpoints;

    @BindView(R.id.complete_btn)
    Button complete_btn;

    @BindView(R.id.edit_username)
    EditText userEdit;


    @OnClick(R.id.complete_btn)
    public void onCompleteBtnClick() {
        String username = userEdit.getText().toString();
        if (username.equals("")) {
            Toast.makeText(CompleteActivity.this, "Username non può essere vuoto", Toast.LENGTH_SHORT).show();
        } else {
            CompleteProfile prof = new CompleteProfile(username);
            apiEndpoints.completeProfile(prof).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 201) {
                        Toast.makeText(CompleteActivity.this, "Profilo completo!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CompleteActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else if (response.code() == 401) {
                        Toast.makeText(CompleteActivity.this, "Questo username è stato già scelto!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("Complete Profile", t.getMessage());
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiEndpoints = Api.getInstance().getApiEndpoint();
        ButterKnife.bind(this);


    }
}

