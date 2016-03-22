package com.example.alexmao.tp2final;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexMAO on 13/03/2016.
 */
public class Evenement implements Parcelable{
    private ArrayList<Lieu> listeLieu;
    private String groupName;
    private HashMap<Integer,Disponibilite> disponibiliteParUtilisateur;
    private int evenementId;
    private String nomEvenement;

    public Evenement(){
        listeLieu=new ArrayList<>();
        groupName = new String();
        disponibiliteParUtilisateur = new HashMap<>();
    }
    public Evenement(String groupName, int evenementId, String nomEvenement){
        this.groupName = groupName;
        this.evenementId = evenementId;
        this.nomEvenement = nomEvenement;
        listeLieu=new ArrayList<>();
        disponibiliteParUtilisateur = new HashMap<>();
    }

    protected Evenement(Parcel in) {
        groupName = in.readString();
        evenementId = in.readInt();
        nomEvenement = in.readString();
    }

    public static final Creator<Evenement> CREATOR = new Creator<Evenement>() {
        @Override
        public Evenement createFromParcel(Parcel in) {
            return new Evenement(in);
        }

        @Override
        public Evenement[] newArray(int size) {
            return new Evenement[size];
        }
    };

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<Lieu> getDateDebut_() {
        return listeLieu;
    }

    public void setDateDebut_(ArrayList<Lieu> listeLieu) {
        this.listeLieu = listeLieu;
    }

    public HashMap<Integer, Disponibilite> getDisponibiliteParUtilisateur() {
        return disponibiliteParUtilisateur;
    }

    public void setDisponibiliteParUtilisateur(HashMap<Integer, Disponibilite> disponibiliteParUtilisateur) {
        this.disponibiliteParUtilisateur = disponibiliteParUtilisateur;
    }

    public int getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(int evenementId) {
        this.evenementId = evenementId;
    }

    public String getNomEvenement() {
        return nomEvenement;
    }

    public void setNomEvenement(String nomEvenement) {
        this.nomEvenement = nomEvenement;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupName);
        dest.writeInt(evenementId);
        dest.writeString(nomEvenement);
    }
}
