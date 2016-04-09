package com.example.alexmao.projetfinal.classeApp;

import java.util.Date;

/**
 * Created by Fabien on 02/04/2016.
 */
public class InvitationConnexion {
    private Utilisateur expediteur;
    private Utilisateur invite;
    private Date date;
    private String idFirebase;
    private int idBDD;

    public Utilisateur getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(Utilisateur expediteur) {
        this.expediteur = expediteur;
    }

    public Utilisateur getInvite() {
        return invite;
    }

    public void setInvite(Utilisateur invite) {
        this.invite = invite;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }

    public int getIdBDD() {
        return idBDD;
    }

    public void setIdBDD(int idBDD) {
        this.idBDD = idBDD;
    }
}
