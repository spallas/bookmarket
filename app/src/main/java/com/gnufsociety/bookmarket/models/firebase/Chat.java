package com.gnufsociety.bookmarket.models.firebase;

import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import java.sql.Timestamp;
import java.util.Calendar;

public class Chat {

    private String name;
    private String chat_id;
    private Long timestamp;
    private String uid;

    public Chat() {
    }

    public Chat(String name, String chat_id, Long timestamp, String uid) {
        this.name = name;
        this.chat_id = chat_id;
        this.timestamp = timestamp;
        this.uid = uid;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getPrettyDate(Context context) {
        //return DateUtils.getRelativeTimeSpanString(context , getTimestamp()*-1, DateUtils.DAY_IN_MILLIS, DateUtils.DAY_IN_MILLIS,0 ).toString();
        return "";
    }

    public String getFormattedDate() {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(getTimestamp()*-1);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "hh:mm";
        final String dateTimeFormatString = "dd/MM/yy";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
            return DateFormat.format(timeFormatString, smsTime).toString();
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
            return "Ieri";
        } else {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        }
    }
}
