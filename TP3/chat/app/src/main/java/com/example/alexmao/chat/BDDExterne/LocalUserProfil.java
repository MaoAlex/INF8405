package com.example.alexmao.chat.BDDExterne;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by filou on 09/03/16.
 */

@JsonIgnoreProperties({"dataBaseId", "picture"})
public class LocalUserProfil extends UserProfil {
    private String dataBaseId;
    private Picture picture;

    public LocalUserProfil(String dataBaseId) {

        this.dataBaseId = dataBaseId;
    }

    public static final Parcelable.Creator<LocalUserProfil> CREATOR = new Parcelable.Creator<LocalUserProfil>()
    {
        @Override
        public LocalUserProfil createFromParcel(Parcel source)
        {
            return new LocalUserProfil(source);
        }

        @Override
        public LocalUserProfil[] newArray(int size)
        {
            return new LocalUserProfil[size];
        }
    };

    public LocalUserProfil(Parcel in) {
        super(in);
        dataBaseId = in.readString();
    }

    public LocalUserProfil(String firstName, String lastName, String mailAdr, String dataBaseId) {
        super(firstName, lastName, mailAdr);
        this.dataBaseId = dataBaseId;
    }

    public LocalUserProfil(String firstName, String lastName, String mailAdr) {
        super(firstName, lastName, mailAdr);
    }

    public LocalUserProfil(String firstName, String lastName, String mailAdr, ArrayList<String> pref) {
        super(firstName, lastName, mailAdr, pref);
    }
    public LocalUserProfil(UserProfil user) {
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
