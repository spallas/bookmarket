package com.gnufsociety.bookmarket.api;

import android.content.Intent;

import com.gnufsociety.bookmarket.models.Ad;
import com.gnufsociety.bookmarket.models.UserComplete;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by spallas on 29/01/2019.
 */
public interface BookmarketEndpoints {

    @GET("users/exist")
    Call<Void> existUser();

    @Multipart
    @POST("users/complete")
    Call<UserComplete> completeProfile(
            @Part() MultipartBody.Part avatar,
            @Part("user[username]") RequestBody username
            );


    @Multipart
    @POST("users/complete")
    Call<UserComplete> completeProfile(
            @Part("user[username]") RequestBody username
    );
    @Multipart
    @POST("ads/")
    Call<Void> createAd(@Part("book[title]") RequestBody title,
                        @Part("book[author]") RequestBody author,
                        @Part("book[subject]") RequestBody subject,
                        @Part("description") RequestBody description,
                        @Part("price") RequestBody price,
                        @Part() MultipartBody.Part image);

    @DELETE("ads/{id}")
    Call<Void> deleteAd(@Path("id") Integer adID);

    @GET("ads/")
    Call<List<Ad>> getMyAds();

    @GET("/search")
    Call<List<Ad>> searchAds(@Query("title") String title,
                             @Query("author") String author,
                             @Query("subject") String subject,
                             @Query("price") String price);

}
