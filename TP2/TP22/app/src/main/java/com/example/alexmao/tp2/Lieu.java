package com.example.alexmao.tp2;

/**
 * Created by alexMAO on 19/03/2016.
 */
public class Lieu {
    private String type;
    private String nomLieu;
    private Localisation localisationLieu;
    private int evenementIdRelie;
    public Lieu(String type, String nomLieu, Localisation localisationLieu) {
        this.type = type;
        this.nomLieu = nomLieu;
        this.localisationLieu = localisationLieu;
    }

    public Lieu() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNomLieu() {
        return nomLieu;
    }

    public void setNomLieu(String nomLieu) {
        this.nomLieu = nomLieu;
    }

    public Localisation getLocalisationLieu() {
        return localisationLieu;
    }

    public void setLocalisationLieu(Localisation localisationLieu) {
        this.localisationLieu = localisationLieu;
    }

    public int getEvenementIdRelie() {
        return evenementIdRelie;
    }

    public void setEvenementIdRelie(int evenementIdRelie) {
        this.evenementIdRelie = evenementIdRelie;
    }

    public String toString(){

        return " Type : " + type + ", nom du lieu : " + nomLieu +
                ", Position : (" + localisationLieu.toString() + "), evenement id : " + evenementIdRelie;

    }


}
