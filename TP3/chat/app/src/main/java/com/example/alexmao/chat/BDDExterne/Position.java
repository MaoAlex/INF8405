package com.example.alexmao.chat.BDDExterne;

/**
 * Created by filou on 07/04/16.
 */
public class Position {
    private double latitude;
    private double longitude;

    public Position() {
    }

    public Position(double longitude) {
        this.longitude = longitude;
    }

    public Position(Position position) {
        latitude = position.getLatitude();
        longitude = position.getLongitude();
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
}
