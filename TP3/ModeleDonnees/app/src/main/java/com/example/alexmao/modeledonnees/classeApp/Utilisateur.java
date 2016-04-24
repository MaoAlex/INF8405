package com.example.alexmao.modeledonnees.classeApp;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Fabien on 02/04/2016.
 */
public class Utilisateur implements Parcelable {
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String mail;
    private List<String> sports;
    private Uri photo;
    private double latitude;
    private double longitude;
    private List<Utilisateur> listeConnexion;
    private List<Integer> listeInterets;
    private List<Integer> listeParticipations;
    private String idFirebase;
    private int idBDD;
    private ParametresUtilisateur parametres;

    public Utilisateur(Parcel in) {
        nom = in.readString();
        prenom = in.readString();
        dateNaissance = (Date) in.readSerializable();
        mail = in.readString();
        sports = in.readArrayList(String.class.getClassLoader());
        photo = in.readParcelable(Uri.class.getClassLoader());
        latitude = in.readDouble();
        longitude = in.readDouble();
        listeConnexion = in.readArrayList(Utilisateur.class.getClassLoader());
        listeInterets = in.readArrayList(Integer.class.getClassLoader());
        listeParticipations = in.readArrayList(Integer.class.getClassLoader());
        idFirebase = in.readString();
        idBDD = in.readInt();
        parametres = in.readParcelable(ParametresUtilisateur.class.getClassLoader());
    }


    public Utilisateur() {
        this.listeConnexion = new ArrayList<Utilisateur>();
        this.sports = new ArrayList<String>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<String> getSports() {
        return sports;
    }

    public void setSports(List<String> sports) {
        this.sports = sports;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Utilisateur> getListeConnexion() {
        return listeConnexion;
    }

    public void setListeConnexion(List<Utilisateur> listeConnexion) {
        this.listeConnexion = listeConnexion;
    }

    public List<Integer> getListeInterets() {
        return listeInterets;
    }

    public void setListeInterets(List<Integer> listeInterets) {
        this.listeInterets = listeInterets;
    }

    public List<Integer> getListeParticipations() {
        return listeParticipations;
    }

    public void setListeParticipations(List<Integer> listeParticipations) {
        this.listeParticipations = listeParticipations;
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

    public ParametresUtilisateur getParametres() {
        return parametres;
    }

    public void setParametres(ParametresUtilisateur parametres) {
        this.parametres = parametres;
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
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeSerializable(dateNaissance);
        dest.writeString(mail);
        dest.writeList(sports);
        dest.writeParcelable(photo, flags);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeParcelable(photo, flags);
        dest.writeList(listeConnexion);
        dest.writeList(listeInterets);
        dest.writeList(listeParticipations);
        dest.writeString(idFirebase);
        dest.writeInt(idBDD);
        dest.writeParcelable(parametres, flags);

    }

    // Creator
    public static final Parcelable.Creator<Utilisateur> CREATOR = new Parcelable.Creator<Utilisateur>() {
        public Utilisateur createFromParcel(Parcel in) {
            return new Utilisateur(in);
        }

        public Utilisateur[] newArray(int size) {
            return new Utilisateur[size];
        }
    };
}
