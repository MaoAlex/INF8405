package com.example.alexmao.projetfinal.BDDExterne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by filou on 07/04/16.
 */
@JsonIgnoreProperties({"dataBaseId"})
public class MessageEBDD {
    private String message;
    private long date;
    private String expediteurID;
    private String dataBaseId;
    private String conversationID;

    public MessageEBDD() {
    }

    public MessageEBDD(String message, long date, String expediteurID) {
        this.message = message;
        this.date = date;
        this.expediteurID = expediteurID;
    }

    public MessageEBDD(MessageEBDD messageEBDD) {
        message = messageEBDD.getMessage();
        date = messageEBDD.getDate();
        expediteurID = messageEBDD.getExpediteurID();
        dataBaseId = messageEBDD.getDataBaseId();
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
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

    @Override
    public String toString() {
        return "MessageEBDD{" +
                "message='" + message + '\'' +
                ", date=" + date +
                ", expediteurID='" + expediteurID + '\'' +
                ", dataBaseId='" + dataBaseId + '\'' +
                ", conversationID='" + conversationID + '\'' +
                '}';
    }
}
