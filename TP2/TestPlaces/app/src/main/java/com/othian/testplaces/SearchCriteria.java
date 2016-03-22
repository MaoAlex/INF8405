package com.othian.testplaces;

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
}
