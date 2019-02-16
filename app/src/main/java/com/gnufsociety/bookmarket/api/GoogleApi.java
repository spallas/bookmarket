package com.gnufsociety.bookmarket.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class GoogleApi {
    private static final GoogleApi ourInstance = new GoogleApi();
    private static final String BASE_URL = "https://www.googleapis.com/";
    private final GoogleBooksEndpoints apiEndpoint;

    private GoogleApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        this.apiEndpoint = retrofit.create(GoogleBooksEndpoints.class);
    }

    public static GoogleApi getInstance() {
        return ourInstance;
    }

    public GoogleBooksEndpoints getApiEndpoint() {
        return this.apiEndpoint;
    }
}
