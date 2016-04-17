package com.example.alexmao.projetfinal.BDDExterne;

import android.os.Parcel;
import android.os.Parcelable;

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
    private List<String> listeInteretsID;
    private List<String> listeParticipationsID;

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
        dateBirth = in.readLong();
        in.readStringList(sports);
        in.readStringList(listeInteretsID);
        in.readStringList(listeParticipationsID);
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
        dest.writeLong(dateBirth);
        dest.writeStringList(sports);
        dest.writeStringList(listeInteretsID);
        dest.writeStringList(listeParticipationsID);
    }

    public UtilisateurProfilEBDD() {
        sports = new ArrayList<>();
        listeInteretsID = new ArrayList<>();
        listeParticipationsID = new ArrayList<>();
    }



    public UtilisateurProfilEBDD(UtilisateurProfilEBDD user) {
        firstName = user.getFirstName();
        lastName = user.getLastName();
        mailAdr = user.getMailAdr();
        dateBirth = user.getDateBirth();
        sports = user.getSports();
    }

    public List<String> getListeInteretsID() {
        return listeInteretsID;
    }

    public void setListeInteretsID(List<String> listeInteretsID) {
        this.listeInteretsID = listeInteretsID;
    }

    public List<String> getListeParticipationsID() {
        return listeParticipationsID;
    }

    public void setListeParticipationsID(List<String> listeParticipationsID) {
        this.listeParticipationsID = listeParticipationsID;
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

    public UtilisateurProfilEBDD(String firstName, String lastName,
                                 String mailAdr, ArrayList<String> pref) {
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
        sports = userFromRemote.getSports();
        listeInteretsID = userFromRemote.getListeInteretsID();
        listeParticipationsID = userFromRemote.getListeParticipationsID();
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
}
