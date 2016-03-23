package com.example.alexmao.tp2final;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Fabien on 22/03/2016.
 */
public class Place {
    private double latitude;
    private double longitude;
    private String nom;
    private String type;

    public Place(JSONObject currentObj) {
        try {
            String name = currentObj.getString("name");
            // L'emplacement (lat, lng) sont dans un élément location lui même inclus dans l'élément geometry de l'objet JSON de la place
            JSONObject geometry = currentObj.getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");

            this.nom = name;
            this.latitude = lat;
            this.longitude = lng;

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
