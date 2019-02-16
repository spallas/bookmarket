package com.gnufsociety.bookmarket.api;

import com.gnufsociety.bookmarket.models.GoogleBook;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleBooksEndpoints {
    @GET("/books/v1/volumes")
    Call<GoogleBook.GoogleBookList> getBooks(@Query("q") String query, @Query("maxResults") int maxRes);

}
