package com.example.alexmao.projetfinal.BDDExterne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by filou on 12/04/16.
 */
@JsonIgnoreProperties({"idFirebase"})
public abstract class NotificationDataEBDD {
    private String expediteur;
    private String invite;
    private long date;
    private String idFirebase;

    public NotificationDataEBDD() {
    }

    public NotificationDataEBDD(String expediteur, String invite, long date) {
        this.expediteur = expediteur;
        this.invite = invite;
        this.date = date;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public String getInvite() {
        return invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }
}
