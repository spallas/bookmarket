package com.gnufsociety.bookmarket.models;

import java.io.Serializable;

/**
 * Created by spallas on 15/02/2019.
 */

public class AdUser implements Serializable {
    private String username;
    private String avatar_url;
    private String firebase_id;

    public AdUser(String username, String avatar_url, String firebase_id) {
        this.username = username;
        this.avatar_url = avatar_url;
        this.firebase_id = firebase_id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getFirebase_id() {
        return firebase_id;
    }
}