package com.gnufsociety.bookmarket.models;

/**
 * Created by spallas on 30/01/2019.
 */
public class Ad {

    private AdInfo ad;
    private Book book;
    private AdUser user;

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


class AdUser {
    String username;
    String avatar_url;
    // String firebase_id;

    public AdUser(String username, String avatar_url/*, String firebase_id*/) {
        this.username = username;
        this.avatar_url = avatar_url;
        // this.firebase_id = firebase_id;
    }


}