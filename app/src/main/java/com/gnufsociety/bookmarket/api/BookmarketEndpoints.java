package com.gnufsociety.bookmarket.api;

import com.gnufsociety.bookmarket.models.BMUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by spallas on 29/01/2019.
 */
public interface BookmarketEndpoints {

    @GET("users/")
    Call<List<BMUser>> allUsers();

}
