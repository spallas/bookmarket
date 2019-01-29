package com.gnufsociety.bookmarket.api;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by spallas on 29/01/2019.
 */
public class Api {
    private static final Api ourInstance = new Api();
    private static final String BASE_URL = "http://127.0.0.1:5000/";
    private final BookmarketEndpoints apiEndpoint;

    private Api() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        this.apiEndpoint = retrofit.create(BookmarketEndpoints.class);
    }

    public static Api getInstance() {
        return ourInstance;
    }

    public BookmarketEndpoints getApiEndpoint() {
        return this.apiEndpoint;
    }
}
