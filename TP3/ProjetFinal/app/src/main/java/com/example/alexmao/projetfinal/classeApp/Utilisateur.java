package com.example.alexmao.projetfinal.classeApp;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Fabien on 02/04/2016.
 */
public class Utilisateur {
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String mail;
    private List<String> sports;
    private Uri photo;
    private double latitude;
    private double longitude;
    private List<Utilisateur> listeConnexion;
    private List<String> listeInteretsID;
    private List<String> listeParticipationsID;
    private String idFirebase;
    private int idBDD;
    private ParametresUtilisateur parametres;

    public Utilisateur() {
        this.listeConnexion = new ArrayList<Utilisateur>();
        this.sports = new ArrayList<String>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<String> getSports() {
        return sports;
    }

    public void setSports(List<String> sports) {
        this.sports = sports;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Utilisateur> getListeConnexion() {
        return listeConnexion;
    }

    public void setListeConnexion(List<Utilisateur> listeConnexion) {
        this.listeConnexion = listeConnexion;
    }

    public List<String> getListeInteretsID() {
        return listeInteretsID;
    }

    public void setListeInteretsID(List<String> listeInteretsID) {
        this.listeInteretsID = listeInteretsID;
    }

    public List<String> getListeParticipationsID() {
        return listeParticipationsID;
    }

    public void setListeParticipationsID(List<String> listeParticipationsID) {
        this.listeParticipationsID = listeParticipationsID;
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

    public ParametresUtilisateur getParametres() {
        return parametres;
    }

    public void setParametres(ParametresUtilisateur parametres) {
        this.parametres = parametres;
    }
}
