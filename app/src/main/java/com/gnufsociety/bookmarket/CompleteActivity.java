package com.gnufsociety.bookmarket;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;
import com.gnufsociety.bookmarket.models.CompleteProfile;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_WRITE_FILE = 1;
    private BookmarketEndpoints apiEndpoints;

    private Uri avatar = null;

    private static final int REQUEST_GET_SINGLE_FILE = 0;

    @BindView(R.id.complete_btn)
    Button complete_btn;

    @BindView(R.id.edit_username)
    EditText userEdit;

    @BindView(R.id.profile_image)
    CircleImageView profileImage;

    @OnClick(R.id.profile_image)
    public void onAvatarClick() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_WRITE_FILE);

        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);
        }
    }


    @OnClick(R.id.complete_btn)
    public void onCompleteBtnClick() {
        String username = userEdit.getText().toString();
        if (username.equals("")) {
            Toast.makeText(CompleteActivity.this, "Username non può essere vuoto", Toast.LENGTH_SHORT).show();
        } else {

            File file = new File(avatar.getPath());
            RequestBody requestAvatar = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileAvatar = MultipartBody.Part.createFormData("user[avatar]", file.getName(), requestAvatar);

            RequestBody requestUsername = RequestBody.create(MediaType.parse("text/plain"), username);

            apiEndpoints.completeProfile(fileAvatar, requestUsername).enqueue(new Callback<Void>() {
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
        setContentView(R.layout.activity_complete);

        apiEndpoints = Api.getInstance().getApiEndpoint();
        ButterKnife.bind(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_GET_SINGLE_FILE:
                    Uri selectedImage = data.getData();
                    profileImage.setImageURI(selectedImage);
                    this.avatar = selectedImage;
                    String path = getRealPathFromURI(selectedImage);
                    if (path != null) {
                        File f = new File(path);
                        this.avatar = Uri.fromFile(f);
                    }
            }
        }
    }

    private String getFilePath(Uri file) {
        String path = null;
        Cursor cursor = getContentResolver()
                .query(file, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(0);
            }
            cursor.close();
        }
        return path;
    }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        Uri uri = Uri.fromFile(new File(path));
        Log.d("COMPLETE PROFILE", "getRealPathFromURI(), path : " + uri.toString());
        cursor.close();
        return path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_WRITE_FILE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.d("PERMISSIONS", "READ PERMISSION GRANTED");
                    onAvatarClick();
                }
        }
    }
}


