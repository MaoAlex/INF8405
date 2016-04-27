package com.example.alexmao.projetfinal.classeApp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fabien on 02/04/2016.
 */
public class Message implements Parcelable {
    /** The Constant STATUS_SENDING. */
    public static final int STATUS_SENDING = 0;

    /** The Constant STATUS_SENT. */
    public static final int STATUS_SENT = 1;

    /** The Constant STATUS_FAILED. */
    public static final int STATUS_FAILED = 2;



    private int status = STATUS_SENT;

    private String message;
    private long date;
    private Utilisateur expediteur;
    private long idBDD;

    public Message(Parcel in) {
        message = in.readString();
        date = (long) in.readLong();
        expediteur = in.readParcelable(Utilisateur.class.getClassLoader());
        idBDD = in.readLong();
    }

    public Message(String s, long date, Utilisateur utilisateur) {
        message = s;
        this.date = date;
        expediteur = utilisateur;
    }

    public Message() {
        this.message = "";
        this.expediteur = new Utilisateur();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Utilisateur getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(Utilisateur expediteur) {
        this.expediteur = expediteur;
    }

    public long getIdBDD() {
        return idBDD;
    }

    public void setIdBDD(long idBDD) {
        this.idBDD = idBDD;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
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
        dest.writeLong(idBDD);
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
