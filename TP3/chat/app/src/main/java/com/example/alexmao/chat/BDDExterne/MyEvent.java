package com.example.alexmao.chat.BDDExterne;


import com.example.alexmao.chat.classeApp.Groupe;

/**
 * Created by filou on 07/04/16.
 */
public class MyEvent {
    private int nbreMaxParticipants;
    private long date;
    private String lieu;
    private double latitude;
    private double longitude;
    private Picture picture;
    private String sport;
    private String groupID;
    private String nomEvenement;
    private String organisateurID;
    private String visibilite;

    public MyEvent() {
    }

    public MyEvent(int nbreMaxParticipants, String sport, String nomEvenement, String organisateurID, String visibilite) {
        this.nbreMaxParticipants = nbreMaxParticipants;
        this.sport = sport;
        this.nomEvenement = nomEvenement;
        this.organisateurID = organisateurID;
        this.visibilite = visibilite;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public int getNbreMaxParticipants() {
        return nbreMaxParticipants;
    }

    public void setNbreMaxParticipants(int nbreMaxParticipants) {
        this.nbreMaxParticipants = nbreMaxParticipants;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
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

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getNomEvenement() {
        return nomEvenement;
    }

    public void setNomEvenement(String nomEvenement) {
        this.nomEvenement = nomEvenement;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getOrganisateurID() {
        return organisateurID;
    }

    public void setOrganisateurID(String organisateurID) {
        this.organisateurID = organisateurID;
    }

    public String getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(String visibilite) {
        this.visibilite = visibilite;
    }
}
