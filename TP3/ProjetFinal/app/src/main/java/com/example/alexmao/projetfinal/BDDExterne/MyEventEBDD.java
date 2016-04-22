package com.example.alexmao.projetfinal.BDDExterne;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by filou on 07/04/16.
 */
@JsonIgnoreProperties({"dataBaseId"})
public class MyEventEBDD {
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
    private String dataBaseId;

    public MyEventEBDD() {
    }

    public String getDataBaseId() {
        return dataBaseId;
    }

    public void setDataBaseId(String dataBaseId) {
        this.dataBaseId = dataBaseId;
    }

    public MyEventEBDD(int nbreMaxParticipants, String sport, String nomEvenement, String organisateurID, String visibilite) {
        this.nbreMaxParticipants = nbreMaxParticipants;
        this.sport = sport;
        this.nomEvenement = nomEvenement;
        this.organisateurID = organisateurID;
        this.visibilite = visibilite;
    }

    public MyEventEBDD(MyEventEBDD myEventEBDD) {
        nbreMaxParticipants = myEventEBDD.getNbreMaxParticipants();
        date = myEventEBDD.getDate();
        lieu = myEventEBDD.getLieu();
        latitude = myEventEBDD.getLatitude();
        longitude = myEventEBDD.getLongitude();
        picture = myEventEBDD.getPicture();
        sport = myEventEBDD.getSport();
        groupID = myEventEBDD.getGroupID();
        nomEvenement = myEventEBDD.getNomEvenement();
        organisateurID = myEventEBDD.getOrganisateurID();
        visibilite = myEventEBDD.getVisibilite();
        dataBaseId = myEventEBDD.getDataBaseId();
    }

    public void update(MyEventEBDD myEventEBDD) {
        nbreMaxParticipants = myEventEBDD.getNbreMaxParticipants();
        date = myEventEBDD.getDate();
        lieu = myEventEBDD.getLieu();
        latitude = myEventEBDD.getLatitude();
        longitude = myEventEBDD.getLongitude();
        picture = myEventEBDD.getPicture();
        sport = myEventEBDD.getSport();
        groupID = myEventEBDD.getGroupID();
        nomEvenement = myEventEBDD.getNomEvenement();
        organisateurID = myEventEBDD.getOrganisateurID();
        visibilite = myEventEBDD.getVisibilite();
        dataBaseId = myEventEBDD.getDataBaseId();
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
