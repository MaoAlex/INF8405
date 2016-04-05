package com.example.alexmao.modeledonnees.classeApp;

import java.util.Date;

/**
 * Created by Fabien on 02/04/2016.
 */
public class Message {
    private String message;
    private Date date;
    private Utilisateur expediteur;
    private int idBDD;

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
}
