package com.example.alexmao.modeledonnees.BDDExterne;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.alexmao.modeledonnees.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by filou on 06/03/16.
 */

//Ne pas oublier d'appeler la fonction pour que le mot de passe soit pris en compte
public class UtilisateurProfilEBDD implements Parcelable {
    private String firstName;
    private String lastName;
    private String mailAdr;
    private long dateBirth;
    private List<String> sports;

    public long getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(long dateBirth) {
        this.dateBirth = dateBirth;
    }

    public static final Creator<UtilisateurProfilEBDD> CREATOR = new Creator<UtilisateurProfilEBDD>() {
        @Override
        public UtilisateurProfilEBDD createFromParcel(Parcel source)
        {
            return new UtilisateurProfilEBDD(source);
        }

        @Override
        public UtilisateurProfilEBDD[] newArray(int size)
        {
            return new UtilisateurProfilEBDD[size];
        }
    };

    public UtilisateurProfilEBDD(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        mailAdr = in.readString();
    }

    public UtilisateurProfilEBDD() {
    }

    public UtilisateurProfilEBDD(Utilisateur user) {
        firstName = user.getPrenom();
        lastName = user.getNom();
        mailAdr = user.getMail();
    }

    public List<String> getSports() {
        return sports;
    }

    public void setSports(List<String> sports) {
        this.sports = sports;
    }

    public UtilisateurProfilEBDD(String firstName, String lastName, String mailAdr) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAdr = mailAdr;
    }

    public UtilisateurProfilEBDD(String firstName, String lastName, String mailAdr, ArrayList<String> pref) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAdr = mailAdr;
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

    public void update(UtilisateurProfilEBDD userFromRemote) {
        firstName = userFromRemote.getFirstName();
        lastName = userFromRemote.getLastName();
        mailAdr = userFromRemote.getMailAdr();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(mailAdr);
    }
}
