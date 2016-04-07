package com.example.alexmao.chat.BDDExterne;

/**
 * Created by filou on 07/04/16.
 */
public class MessageBDD {
    private String message;
    private long date;
    private String expediteurID;

    public MessageBDD() {
    }

    public MessageBDD(String message, long date, String expediteurID) {
        this.message = message;
        this.date = date;
        this.expediteurID = expediteurID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getExpediteurID() {
        return expediteurID;
    }

    public void setExpediteurID(String expediteurID) {
        this.expediteurID = expediteurID;
    }
}
