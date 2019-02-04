package com.gnufsociety.bookmarket.models;

/**
 * Created by spallas on 04/02/2019.
 */
public class AdPostInfo {
    AdInfo info;
    BookInfo book;
}

class AdInfo {
    Float price;
    String description;
}

class BookInfo {
    String title;
    String author;
}