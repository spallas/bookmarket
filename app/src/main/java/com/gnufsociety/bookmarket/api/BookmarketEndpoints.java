package com.gnufsociety.bookmarket.api;

import com.gnufsociety.bookmarket.models.Ad;
import com.gnufsociety.bookmarket.models.BMUser;
import com.gnufsociety.bookmarket.models.CompleteProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by spallas on 29/01/2019.
 */
public interface BookmarketEndpoints {

    @GET("users/exist")
    Call<Void> existUser();

    @POST("users/complete")
    Call<Void> completeProfile(@Body CompleteProfile body);

    @GET("ad/{id}")
    Call<Ad> getAd(@Path("id") String adID);

    @POST("ads/")
    Call<Void> createAd(@Body Ad ad);

    @DELETE("ad/{id}")
    Call<Void> deleteAd(@Path("id") String adID);

    @GET("ads/")
    Call<List<Ad>> getMyAds();

    @GET("ads/{query}")
    Call<List<Ad>> searchAds(@Path("query") String query);

}
