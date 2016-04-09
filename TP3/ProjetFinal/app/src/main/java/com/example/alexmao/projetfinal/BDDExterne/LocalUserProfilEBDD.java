package com.example.alexmao.projetfinal.BDDExterne;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by filou on 09/03/16.
 */

@JsonIgnoreProperties({"dataBaseId", "picture"})
public class LocalUserProfilEBDD extends UtilisateurProfilEBDD {
    private String dataBaseId;
    private Picture picture;

    public LocalUserProfilEBDD(String dataBaseId) {

        this.dataBaseId = dataBaseId;
    }

    public static final Creator<LocalUserProfilEBDD> CREATOR = new Creator<LocalUserProfilEBDD>()
    {
        @Override
        public LocalUserProfilEBDD createFromParcel(Parcel source)
        {
            return new LocalUserProfilEBDD(source);
        }

        @Override
        public LocalUserProfilEBDD[] newArray(int size)
        {
            return new LocalUserProfilEBDD[size];
        }
    };

    public LocalUserProfilEBDD(Parcel in) {
        super(in);
        dataBaseId = in.readString();
    }

    public LocalUserProfilEBDD(String firstName, String lastName, String mailAdr, String dataBaseId) {
        super(firstName, lastName, mailAdr);
        this.dataBaseId = dataBaseId;
    }

    public LocalUserProfilEBDD(String firstName, String lastName, String mailAdr) {
        super(firstName, lastName, mailAdr);
    }

    public LocalUserProfilEBDD(String firstName, String lastName, String mailAdr, ArrayList<String> pref) {
        super(firstName, lastName, mailAdr, pref);
    }
    public LocalUserProfilEBDD(UtilisateurProfilEBDD user) {
        super(user.getFirstName(), user.getLastName(), user.getMailAdr());
    }

    public String getDataBaseId() {
        return dataBaseId;
    }

    public void setDataBaseId(String dataBaseId) {
        this.dataBaseId = dataBaseId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(dataBaseId);
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
