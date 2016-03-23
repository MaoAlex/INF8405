package com.example.alexmao.tp2final.firebase;

/**
 * Created by filou on 19/03/16.
 */
public class MeetingPlace {
    private String name;
    private String type;
    private double lat;
    private double longi;

    public double getLat() {
        return lat;
    }

    public String getName() {
        return name;
    }

    public double getLongi() {
        return longi;
    }

    public MeetingPlace(String name, String type, double lat, double longi) {
        this.type = type;
        this.name = name;
        this.lat = lat;
        this.longi = longi;
    }
}
