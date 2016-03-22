package com.example.alexmao.tp2final;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by alexMAO on 15/03/2016.
 */
public class Disponibilite implements Parcelable {

    private Date dateDebut_;
    private Date dateFin_;

    public Disponibilite(){super();};

    protected Disponibilite(Parcel in) {
        super();
        //this.dateDebut_ = Date(in.readString());
        //this.dateFin_ = Date(in.readString());
    }

    public static final Creator<Disponibilite> CREATOR = new Creator<Disponibilite>() {
        @Override
        public Disponibilite createFromParcel(Parcel in) {
            return new Disponibilite(in);
        }

        @Override
        public Disponibilite[] newArray(int size) {
            return new Disponibilite[size];
        }
    };

    public Date getDateDebut() {
        return dateDebut_;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut_ = dateDebut_;
    }
    public Date getDateFin() {
        return dateFin_;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin_ = dateFin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dateDebut_.toString());
        dest.writeString(dateFin_.toString());
    }
}
