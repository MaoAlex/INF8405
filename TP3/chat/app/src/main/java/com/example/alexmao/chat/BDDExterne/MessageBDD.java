package com.example.alexmao.chat.BDDExterne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by filou on 07/04/16.
 */
@JsonIgnoreProperties({"dataBaseId"})
public class MessageBDD {
    private String message;
    private long date;
    private String expediteurID;
    private String dataBaseId;

    public MessageBDD() {
    }

    public MessageBDD(String message, long date, String expediteurID) {
        this.message = message;
        this.date = date;
        this.expediteurID = expediteurID;
    }

    public MessageBDD(MessageBDD messageBDD) {
        message = messageBDD.getMessage();
        date = messageBDD.getDate();
        expediteurID = messageBDD.getExpediteurID();
        dataBaseId = messageBDD.getDataBaseId();
    }

    public String getDataBaseId() {
        return dataBaseId;
    }

    public void setDataBaseId(String dataBaseId) {
        this.dataBaseId = dataBaseId;
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
