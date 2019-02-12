package com.gnufsociety.bookmarket.models;

/**
 * Created by spallas on 04/02/2019.
 */
public class AdPostInfo {
    public AdInfo ad;
    public BookInfo book;
    public AdPostInfo(Float price, String description, String title, String author) {
        ad = new AdInfo(price, description);
        book = new BookInfo(title, author);
    }
}

class AdInfo {
    public Float price;
    public String description;

    public AdInfo(Float price, String description) {
        this.price = price;
        this.description = description;
    }
}

class BookInfo {
    public String title;
    public String author;

    public BookInfo(String title, String author) {
        this.title = title;
        this.author = author;
    }
}