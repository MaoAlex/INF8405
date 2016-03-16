package com.example.alexmao.testtp2;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by alexMAO on 13/03/2016.
 */
//Classe contenant les differentes informations sur un utilisateur
public class User {
    private String nom_;
    private String prenom_;
    private String mail_;
    private Uri photo_;
    private boolean estOrganisateur_;
    public ArrayList<String> preference_;
    private Localisation localisation_;

    public User(){
        nom_ = "";
        prenom_ = "";
        mail_ = "";
        photo_ = null;
        estOrganisateur_ = false;
        localisation_=null;
    }

    public User( String nom_, String prenom_, Uri photo_, String mail_, boolean estOrganisateur_, ArrayList<String> preference_, Localisation localisation_){
        this.nom_ = nom_;
        this.prenom_ = prenom_;
        this. mail_ = mail_;
        this.photo_ = photo_;
        this.estOrganisateur_ = estOrganisateur_;
        this.localisation_ = localisation_;
    }

    public String getNom_() {
        return nom_;
    }

    public void setNom_(String nom_) {
        this.nom_ = nom_;
    }

    public String getPrenom_() {
        return prenom_;
    }

    public void setPrenom_(String prenom_) {
        this.prenom_ = prenom_;
    }

    public String getMail_() {
        return mail_;
    }

    public void setMail_(String mail_) {
        this.mail_ = mail_;
    }

    public Uri getPhoto() {
        return photo_;
    }

    public void setPhoto(Uri photo) {
        this.photo_ = photo;
    }

    public boolean isEstOrganisateur_() {
        return estOrganisateur_;
    }

    public void setEstOrganisateur_(boolean estOrganisateur_) {
        this.estOrganisateur_ = estOrganisateur_;
    }

    public ArrayList<String> getPreference_(){
        return this.preference_;
    }

    public void setPreference_(ArrayList<String> preference_){
        this.preference_=preference_;
    }

}
