package com.gnufsociety.bookmarket.models.firebase;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Message {
    private Long timestamp;
    private String id;
    private String message;
    private String user;

    public Message(Long timestamp, String message, String user) {
        this.timestamp = timestamp;
        this.message = message;
        this.user = user;
    }

    public Message() {
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String toPrettyTime() {
        Calendar msgTime = Calendar.getInstance();
        msgTime.setTimeInMillis(getTimestamp());

        return DateFormat.format("hh:mm", msgTime).toString();
    }
}
