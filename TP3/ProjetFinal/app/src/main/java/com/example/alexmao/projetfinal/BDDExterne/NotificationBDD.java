package com.example.alexmao.projetfinal.BDDExterne;

import java.util.Map;

/**
 * Created by filou on 12/04/16.
 */
public class NotificationBDD {
    String type;
    Map<String, String> params;
    String askerID;
    String destID;

    public NotificationBDD() {
    }

    public NotificationBDD(String type, Map<String, String> params,
                           String askerID, String destID) {
        this.type = type;
        this.params = params;
        this.askerID = askerID;
        this.destID = destID;
    }

    public NotificationBDD(NotificationBDD notificationBDD) {
        type = notificationBDD.getType();
        params = notificationBDD.getParams();
        askerID = notificationBDD.getAskerID();
        destID = notificationBDD.getDestID();
    }

    public String getAskerID() {
        return askerID;
    }

    public void setAskerID(String askerID) {
        this.askerID = askerID;
    }

    public String getDestID() {
        return destID;
    }

    public void setDestID(String destID) {
        this.destID = destID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map<String, String>  params) {
        this.params = params;
    }
}
