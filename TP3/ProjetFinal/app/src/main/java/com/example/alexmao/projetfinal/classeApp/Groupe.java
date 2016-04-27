package com.example.alexmao.projetfinal.classeApp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Fabien on 02/04/2016.
 */
public class Groupe implements Parcelable {
    private List<Utilisateur> listeMembre;
    private String idFirebase;
    private long idBDD;
    private String idFirebaseConversation;
    private Evenement evenement;

    public Groupe() {
        listeMembre = new LinkedList<>();
    }

    public Groupe(Parcel in) {
        listeMembre = in.readArrayList(Utilisateur.class.getClassLoader());
        idFirebase = in.readString();
        idBDD = in.readInt();
        idFirebaseConversation = in.readParcelable(Conversation.class.getClassLoader());
        evenement = in.readParcelable(Evenement.class.getClassLoader());
    }

    public List<Utilisateur> getListeMembre() {
        return listeMembre;
    }

    public void setListeMembre(List<Utilisateur> listeMembre) {
        this.listeMembre = listeMembre;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }

    public long getIdBDD() {
        return idBDD;
    }

    public void setIdBDD(long idBDD) {
        this.idBDD = idBDD;
    }

    public String getConversation() {
        return idFirebaseConversation;
    }

    public void setConversation(String idFirebaseConversation) {
        this.idFirebaseConversation = idFirebaseConversation;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public void addMember(Utilisateur utilisateur) {
        listeMembre.add(utilisateur);
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
        dest.writeList(listeMembre);
        dest.writeString(idFirebase);
        dest.writeLong(idBDD);
        dest.writeString(idFirebaseConversation);
        dest.writeParcelable(evenement, flags);
    }

    // Creator
    public static final Parcelable.Creator<Groupe> CREATOR = new Parcelable.Creator<Groupe>() {
        public Groupe createFromParcel(Parcel in) {
            return new Groupe(in);
        }

        public Groupe[] newArray(int size) {
            return new Groupe[size];
        }
    };
}
