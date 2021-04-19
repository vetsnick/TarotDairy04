package com.example.tarotdairy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Question {


    long timestamp;
    String title;
    int cardnum;


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCardnum() {
        return cardnum;
    }

    public void setCardnum(int cardnum) {
        this.cardnum = cardnum;
    }



    public Question(long timestamp, String title, int cardnum) {
        this.timestamp = timestamp;
        this.title = title;
        this.cardnum = cardnum;
    }


}
