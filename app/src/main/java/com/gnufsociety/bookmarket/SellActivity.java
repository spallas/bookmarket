package com.gnufsociety.bookmarket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;
import com.gnufsociety.bookmarket.models.Ad;
import com.gnufsociety.bookmarket.models.Book;

import java.io.File;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import id.zelory.compressor.FileUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SellActivity extends AppCompatActivity {

    private final static int PICK_GALLERY_INTENT = 1;

    private BookmarketEndpoints apiEndpoint = Api.getInstance().getApiEndpoint();

    @BindView(R.id.edit_ad_title) EditText editTitle;
    @BindView(R.id.edit_ad_desc) EditText editDesc;
    @BindView(R.id.edit_ad_subj) EditText editSubj;
    @BindView(R.id.edit_ad_author) EditText editAuthor;
    @BindView(R.id.edit_ad_price) EditText editPrice;
    @BindView(R.id.add_ad_img) ImageView adImage;
    @BindView(R.id.add_ad_btn) Button addAdBtn;

    private Uri uriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.add_ad_btn)
    public void postNewAd() {

        File file = storeImage();

        Book book = new Book(editTitle.getText().toString(), editAuthor.getText().toString(), "BOH");
        Ad ad = new Ad(book, editDesc.getText().toString(), Float.valueOf(editPrice.getText().toString()));

        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), book.getTitle());
        RequestBody author = RequestBody.create(MediaType.parse("text/plain"), book.getAuthor());
        RequestBody subject = RequestBody.create(MediaType.parse("text/plain"), book.getSubject());

        RequestBody requestDesc = RequestBody.create(MediaType.parse("text/plain"), ad.getDescription());
        RequestBody requestPrice = RequestBody.create(MediaType.parse("text/plain"), ad.getPrice());
        RequestBody requestAdImage = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part fileAdImage = MultipartBody.Part.createFormData("img", file.getName(), requestAdImage);

        apiEndpoint.createAd(title, author, subject, requestDesc, requestPrice, fileAdImage).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.e("SELL ACTIVITY request", Utils.bodyToString(call.request()));
                Log.e("SELL ACTIVITY request", call.request().headers().toString());
                if (response.code() == 201) {
                    Toast.makeText(SellActivity.this, "Ad created!", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (response.code() == 401) {
                    Toast.makeText(SellActivity.this, "401 ERROR!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SellActivity.this, "SELL ERROR!" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Sell Activity", t.getMessage());
            }
        });

    }


    @OnClick(R.id.add_ad_img)
    public void chooseImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select from gallery"), PICK_GALLERY_INTENT);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_GALLERY_INTENT) {
            if (resultCode == RESULT_OK) {
                uriImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImage);
                    adImage.clearColorFilter();
                    adImage.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    adImage.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
                    adImage.requestLayout();
                    adImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    adImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private File storeImage() {
        //get current image
        File toCompress = null;
        try {
            toCompress = FileUtil.from(this, uriImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Uploading ad..", Toast.LENGTH_LONG).show();

        //return compressed image PLAY WITH THIS
        final File compressedFile = new Compressor.Builder(this)
                .setMaxHeight(1024)
                .setMaxWidth(1024)
                .setQuality(90)
                .build().compressToFile(toCompress);

        return compressedFile;
    }


}
