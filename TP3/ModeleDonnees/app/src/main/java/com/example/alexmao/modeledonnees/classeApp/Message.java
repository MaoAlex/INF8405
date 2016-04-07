package com.example.alexmao.modeledonnees.classeApp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Fabien on 02/04/2016.
 */
public class Message implements Parcelable {
    private String message;
    private Date date;
    private Utilisateur expediteur;
    private int idBDD;

    public Message(Parcel in) {
        message = in.readString();
        date = (Date) in.readSerializable();
        expediteur = in.readParcelable(Utilisateur.class.getClassLoader());
        idBDD = in.readInt();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Utilisateur getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(Utilisateur expediteur) {
        this.expediteur = expediteur;
    }

    public int getIdBDD() {
        return idBDD;
    }

    public void setIdBDD(int idBDD) {
        this.idBDD = idBDD;
    }

    /*
     * Parcelable methods
     */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeSerializable(date);
        dest.writeParcelable(expediteur, flags);
        dest.writeInt(idBDD);
    }

    // Creator
    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
