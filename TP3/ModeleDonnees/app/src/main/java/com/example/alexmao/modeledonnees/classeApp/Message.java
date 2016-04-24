package com.example.alexmao.modeledonnees.classeApp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

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
    private Date date;
    private Utilisateur expediteur;
    private int idBDD;

    public Message(Parcel in) {
        message = in.readString();
        date = (Date) in.readSerializable();
        expediteur = in.readParcelable(Utilisateur.class.getClassLoader());
        idBDD = in.readInt();
    }


    public Message(String s, Date date, Utilisateur utilisateur) {
        message = s;
        this.date = date;
        expediteur = utilisateur;
    }

    public Message() {
        this.message = "";
        this.date = new Date();
        this.expediteur = new Utilisateur();
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
