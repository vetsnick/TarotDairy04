package com.example.tarotdairy;

public class Tarots {

    private String cardname;
    int cardimage;

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public int getCardimage() {
        return cardimage;
    }

    public void setCardimage(int cardimage) {
        this.cardimage = cardimage;
    }

    public Tarots(String cardname, int cardimage) {
        this.cardname = cardname;
        this.cardimage = cardimage;
    }
}
