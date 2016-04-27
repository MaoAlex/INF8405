package com.example.alexmao.projetfinal.classeApp;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Fabien on 02/04/2016.
 */
public class Utilisateur implements Parcelable {
    private String nom;
    private String prenom;
    private long dateNaissance;
    private String mail;
    private List<String> sports;
    private Uri photo;
    private double latitude;
    private double longitude;
    private List<String> listeConnexion;
    private List<String> listeInteretsID;
    private List<String> listeParticipationsID;
    private String idFirebase;
    private long idBDD;
    private ParametresUtilisateur parametres;

    public Utilisateur() {
        this.listeConnexion = new ArrayList<String>();
        this.sports = new ArrayList<String>();
    }

//    public Utilisateur(UtilisateurProfilEBDD utilisateurProfilEBDD, String idFirebase) {
//        this.nom = utilisateurProfilEBDD.getLastName();
//        this.prenom = utilisateurProfilEBDD.getFirstName();
//        GregorianCalendar gregorianCalendar = new GregorianCalendar();
//        gregorianCalendar.setTimeInMillis(utilisateurProfilEBDD.getDateBirth());
//        this.dateNaissance = utilisateurProfilEBDD.getDateBirth(); //gregorianCalendar.getTime();
//        this.mail = utilisateurProfilEBDD.getMailAdr();
//        this.sports = utilisateurProfilEBDD.getSports();
//        this.listeConnexion = utilisateurProfilEBDD.getListeConnexion();
//        this.listeParticipationsID = utilisateurProfilEBDD.getListeParticipationsID();
//        this.listeInteretsID = utilisateurProfilEBDD.getListeConnexion();
//        this.idFirebase = idFirebase;
//
//    }

    public Utilisateur(Parcel in) {
        nom = in.readString();
        prenom = in.readString();
        dateNaissance = in.readLong();
        mail = in.readString();
        sports = in.readArrayList(String.class.getClassLoader());
        photo = in.readParcelable(Uri.class.getClassLoader());
        latitude = in.readDouble();
        longitude = in.readDouble();
        listeConnexion = in.readArrayList(Utilisateur.class.getClassLoader());
        listeInteretsID = in.readArrayList(String.class.getClassLoader());
        listeParticipationsID = in.readArrayList(String.class.getClassLoader());
        idFirebase = in.readString();
        idBDD = in.readLong();
        parametres = in.readParcelable(ParametresUtilisateur.class.getClassLoader());
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

    public long getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(long dateNaissance) {
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

    public List<String> getListeConnexion() {
        return listeConnexion;
    }

    public void setListeConnexion(List<String> listeConnexion) {
        this.listeConnexion = listeConnexion;
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
        dest.writeLong(dateNaissance);
        dest.writeString(mail);
        dest.writeList(sports);
        dest.writeParcelable(photo, flags);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeParcelable(photo, flags);
        dest.writeList(listeConnexion);
        dest.writeList(listeInteretsID);
        dest.writeList(listeParticipationsID);
        dest.writeString(idFirebase);
        dest.writeLong(idBDD);
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

    public String construireDDN() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(dateNaissance);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dateNaissance = new String(String.valueOf(day));
        dateNaissance = dateNaissance.concat("/");
        dateNaissance = dateNaissance.concat(String.valueOf(month));
        dateNaissance = dateNaissance.concat("/");
        dateNaissance = dateNaissance.concat(String.valueOf(year));

        return dateNaissance;
    }

    public String construireNom() {
        String identite = new String(prenom);
        identite = identite.concat(" ");
        identite = identite.concat(nom);
        return identite;
    }
}
