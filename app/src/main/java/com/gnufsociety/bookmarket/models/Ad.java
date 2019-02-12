package com.gnufsociety.bookmarket.models;

/**
 * Created by spallas on 30/01/2019.
 */
public class Ad {

    private AdInfo ad;
    private Book book;

    public Ad(Book book, String desc, Float price) {
        this.ad = new AdInfo(desc, price);
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public String getPrice() {
        return "" + ad.price;
    }

    public String getDescription() {
        return ad.description;
    }
}

class AdInfo {
    Float price;
    String description;

    AdInfo(String description, Float price) {
        this.price = price;
        this.description = description;
    }
}
