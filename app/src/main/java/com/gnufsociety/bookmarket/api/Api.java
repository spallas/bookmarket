package com.gnufsociety.bookmarket.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by spallas on 29/01/2019.
 */
public class Api {
    private static final Api ourInstance = new Api();
    private static final String BASE_URL = "http://bookmarket.servebeer.com:80/";
    private final BookmarketEndpoints apiEndpoint;

    private Api() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new FirebaseUserIdTokenInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
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
