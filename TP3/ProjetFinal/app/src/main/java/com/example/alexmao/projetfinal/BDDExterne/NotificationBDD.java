package com.example.alexmao.projetfinal.BDDExterne;

import java.util.Map;

/**
 * Created by filou on 12/04/16.
 */
public class NotificationBDD {
    String Type;
    Map<String, String> params;
    String askerID;
    String destID;

    public NotificationBDD() {
    }

    public NotificationBDD(String type, Map<String, String> params, String askerID, String destID) {
        Type = type;
        this.params = params;
        this.askerID = askerID;
        this.destID = destID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map<String, String>  params) {
        this.params = params;
    }
}
