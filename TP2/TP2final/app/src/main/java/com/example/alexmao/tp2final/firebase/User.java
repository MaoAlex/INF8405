package com.example.alexmao.tp2final.firebase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by filou on 06/03/16.
 */

//Ne pas oublier d'appeler la fonction pour que le mot de passe soit pris en compte
public class User implements Parcelable {
    private String firstName;
    private String lastName;
    private String mailAdr;
    private double lat;
    private double longi;
    private ArrayList<String> preferences;

    public static final Creator<User> CREATOR = new Creator<User>()
    {
        @Override
        public User createFromParcel(Parcel source)
        {
            return new User(source);
        }

        @Override
        public User[] newArray(int size)
        {
            return new User[size];
        }
    };

    public User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        mailAdr = in.readString();
        lat = in.readDouble();
        longi = in.readDouble();
    }

    public User() {
    }

    public User(String firstName, String lastName, String mailAdr) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAdr = mailAdr;
        preferences = new ArrayList<>();
    }

    public void addPreference(String pref) {
        preferences.add(pref);
    }

    public ArrayList<String> getPreferences() {
        return preferences;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMailAdr() {
        return mailAdr;
    }

    public void update(User userFromRemote) {
        firstName = userFromRemote.getFirstName();
        lastName = userFromRemote.getLastName();
        mailAdr = userFromRemote.getMailAdr();
        lat = userFromRemote.getLat();
        longi = userFromRemote.getLongi();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMailAdr(String mailAdr) {
        this.mailAdr = mailAdr;
    }

    public double getLat() {
        return lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(mailAdr);
        dest.writeDouble(lat);
        dest.writeDouble(longi);
    }
}
