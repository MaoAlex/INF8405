package com.example.alexmao.chat.BDDExterne;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.alexmao.chat.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by filou on 06/03/16.
 */

//Ne pas oublier d'appeler la fonction pour que le mot de passe soit pris en compte
public class UserProfil implements Parcelable {
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

    public static final Creator<UserProfil> CREATOR = new Creator<UserProfil>() {
        @Override
        public UserProfil createFromParcel(Parcel source)
        {
            return new UserProfil(source);
        }

        @Override
        public UserProfil[] newArray(int size)
        {
            return new UserProfil[size];
        }
    };

    public UserProfil(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        mailAdr = in.readString();
    }

    public UserProfil() {
    }

    public UserProfil(User user) {
        firstName = user.getPrenom();
        lastName = user.getNom();
        mailAdr = user.getMail_();
    }

    public List<String> getSports() {
        return sports;
    }

    public void setSports(List<String> sports) {
        this.sports = sports;
    }

    public UserProfil(String firstName, String lastName, String mailAdr) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAdr = mailAdr;
    }

    public UserProfil(String firstName, String lastName, String mailAdr, ArrayList<String> pref) {
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

    public void update(UserProfil userFromRemote) {
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
