package com.example.alexmao.tp2;

import android.net.Uri;

/**
 * Created by alexMAO on 17/03/2016.
 */
public class LieuChoisi {

    private Uri photo_;
    private String description_;
    private Localisation positionLieu_;
    private int evenementId;

    public LieuChoisi(){

    }

    public LieuChoisi(Uri photo, String description, Localisation positionLieu, int evenementId){
        this.positionLieu_ = positionLieu;
        this.photo_ = photo;
        this.description_ = description;
        this.evenementId = evenementId;
    }

    public Uri getPhoto() {
        return photo_;
    }

    public void setPhoto(Uri photo) {
        this.photo_ = photo;
    }

    public String getDescription() {
        return description_;
    }

    public void setDescription(String description) {
        this.description_ = description;
    }

    public Localisation getPositionLieu() {
        return positionLieu_;
    }

    public void setPositionLieu(Localisation positionLieu) {
        this.positionLieu_ = positionLieu;
    }

    public int getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(int evenementId) {
        this.evenementId = evenementId;
    }
}
