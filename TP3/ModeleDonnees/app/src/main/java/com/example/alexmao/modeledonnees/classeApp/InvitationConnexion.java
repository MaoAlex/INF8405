package com.example.alexmao.modeledonnees.classeApp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Fabien on 02/04/2016.
 */
public class InvitationConnexion implements Parcelable {
    private Utilisateur expediteur;
    private Utilisateur invite;
    private Date date;
    private String idFirebase;
    private int idBDD;

    public InvitationConnexion(Parcel in) {
        expediteur = in.readParcelable(Utilisateur.class.getClassLoader());
        invite = in.readParcelable(Utilisateur.class.getClassLoader());
        date = (Date) in.readSerializable();
        idFirebase = in.readString();
        idBDD = in.readInt();
    }


    public Utilisateur getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(Utilisateur expediteur) {
        this.expediteur = expediteur;
    }

    public Utilisateur getInvite() {
        return invite;
    }

    public void setInvite(Utilisateur invite) {
        this.invite = invite;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
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
        dest.writeParcelable(expediteur, flags);
        dest.writeParcelable(invite, flags);
        dest.writeSerializable(date);
        dest.writeString(idFirebase);
        dest.writeInt(idBDD);
    }

    // Creator
    public static final Parcelable.Creator<InvitationConnexion> CREATOR = new Parcelable.Creator<InvitationConnexion>() {
        public InvitationConnexion createFromParcel(Parcel in) {
            return new InvitationConnexion(in);
        }

        public InvitationConnexion[] newArray(int size) {
            return new InvitationConnexion[size];
        }
    };
}
