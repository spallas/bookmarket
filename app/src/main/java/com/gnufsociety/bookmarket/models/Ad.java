package com.gnufsociety.bookmarket.models;

import java.io.Serializable;

/**
 * Created by spallas on 30/01/2019.
 */
public class Ad implements Serializable {

    private Integer id;
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

    public AdUser getUser() {
        return user;
    }

    public Integer getId() {
        return id;
    }
}
