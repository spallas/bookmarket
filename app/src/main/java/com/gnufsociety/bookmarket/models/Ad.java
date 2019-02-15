package com.gnufsociety.bookmarket.models;

/**
 * Created by spallas on 30/01/2019.
 */
public class Ad {

    private String price, description, img_url;
    private Book book;
    private AdUser user;

    public Ad(Book book, String desc, Float price) {
        this.price = "" + price;
        this.description = desc;
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public String getPrice() {
        return "" + price;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return img_url;
    }
}

class AdUser {
    private String username;
    private String avatar_url;
    private String firebase_id;

    public AdUser(String username, String avatar_url/*, String firebase_id*/) {
        this.username = username;
        this.avatar_url = avatar_url;
        this.firebase_id = firebase_id;
    }


}