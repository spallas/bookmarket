package com.gnufsociety.bookmarket.models;

import java.io.Serializable;

/**
 * Created by spallas on 31/01/2019.
 */
public class Book implements Serializable {
    private String title;
    private String author;
    private String subject;

    public Book(String title, String author, String subject) {
        this.title = title;
        this.author = author;
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSubject() {
        return subject;
    }
}
