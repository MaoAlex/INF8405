package com.example.alexmao.tp2final;

import com.google.android.gms.maps.model.LatLng;
/**
 * Created by Fabien on 20/03/2016.
 */
public class SearchCriteria {
    private LatLng latLng;
    private int radius;
    private String type;
    private String APIKey;
    private float color;

    public SearchCriteria(LatLng latLng, int radius, String type, String APIKey, float color) {
        this.latLng = latLng;
        this.radius = radius;
        this.type = type;
        this.APIKey = APIKey;
        this.color = color;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public int getRadius() {
        return radius;
    }

    public String getType() {
        return type;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public float getColor() { return color; }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

    public void setColor(float color) {
        this.color = color;
    }
}

