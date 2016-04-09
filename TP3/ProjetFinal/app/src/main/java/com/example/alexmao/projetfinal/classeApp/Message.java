package com.example.alexmao.projetfinal.classeApp;

import java.util.Date;

/**
 * Created by Fabien on 02/04/2016.
 */
public class Message {
    /** The Constant STATUS_SENDING. */
    public static final int STATUS_SENDING = 0;

    /** The Constant STATUS_SENT. */
    public static final int STATUS_SENT = 1;

    /** The Constant STATUS_FAILED. */
    public static final int STATUS_FAILED = 2;



    private int status = STATUS_SENT;

    private String message;
    private Date date;
    private Utilisateur expediteur;
    private int idBDD;

    public Message(String s, Date date, Utilisateur utilisateur) {
        message = s;
        this.date = date;
        expediteur = utilisateur;
    }

    public Message() {
        this.message = "";
        this.date = new Date();
        this.expediteur = new Utilisateur();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Utilisateur getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(Utilisateur expediteur) {
        this.expediteur = expediteur;
    }

    public int getIdBDD() {
        return idBDD;
    }

    public void setIdBDD(int idBDD) {
        this.idBDD = idBDD;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
