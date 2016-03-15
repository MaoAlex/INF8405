package com.example.alexmao.tp2;

import java.util.Date;

/**
 * Created by alexMAO on 13/03/2016.
 */
public class Evenement {
    private Date date_;
    private Localisation localisation_;

    public Date getDate_() {
        return date_;
    }

    public void setDate_(Date date_) {
        this.date_ = date_;
    }

    public Localisation getLocalisation_() {
        return localisation_;
    }

    public void setLocalisation_(Localisation localisation_) {
        this.localisation_ = localisation_;
    }
}
