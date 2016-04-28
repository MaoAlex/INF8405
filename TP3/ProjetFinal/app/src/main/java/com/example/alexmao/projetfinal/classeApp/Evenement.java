package com.example.alexmao.projetfinal.classeApp;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Fabien on 02/04/2016.
 */
public class Evenement implements Parcelable {
    private int nbreMaxParticipants;
    private long date;
    private String lieu;
    private double latitude;
    private double longitude;
    private Uri photo;
    private String sport;
    private Groupe groupeAssocie;
    private String nomEvenement;
    private Utilisateur organisateur;
    private String visibilite;
    private String idFirebase;
    private long idBDD;

    public Evenement() {

    }

    public Evenement(Parcel in) {
        nbreMaxParticipants = in.readInt();
        date = in.readLong();
        lieu = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        photo = in.readParcelable(Uri.class.getClassLoader());
        sport = in.readString();
        groupeAssocie = in.readParcelable(Groupe.class.getClassLoader());
        nomEvenement = in.readString();
        organisateur = in.readParcelable(Utilisateur.class.getClassLoader());
        visibilite = in.readString();
        idFirebase = in.readString();
        idBDD = in.readLong();
    }

    public int getNbreMaxParticipants() {
        return nbreMaxParticipants;
    }

    public void setNbreMaxParticipants(int nbreMaxParticipants) {
        this.nbreMaxParticipants = nbreMaxParticipants;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
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

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Groupe getGroupeAssocie() {
        return groupeAssocie;
    }

    public void setGroupeAssocie(Groupe groupeAssocie) {
        this.groupeAssocie = groupeAssocie;
    }

    public String getNomEvenement() {
        return nomEvenement;
    }

    public void setNomEvenement(String nomEvenement) {
        this.nomEvenement = nomEvenement;
    }

    public Utilisateur getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(Utilisateur organisateur) {
        this.organisateur = organisateur;
    }

    public String getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(String visibilite) {
        this.visibilite = visibilite;
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

               /*
      * Parcelable methods
      */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nbreMaxParticipants);
        dest.writeLong(date);
        dest.writeString(lieu);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeParcelable(photo, flags);
        dest.writeString(sport);
        dest.writeParcelable(groupeAssocie, flags);//dest.writeParcelable(groupeAssocie, flags);
        dest.writeString(nomEvenement);
        dest.writeParcelable(organisateur, flags); //writeParcelable(organisateur, flags);
        dest.writeString(visibilite);
        dest.writeString(idFirebase);
        dest.writeLong(idBDD);
    }

    // Creator
    public static final Parcelable.Creator<Evenement> CREATOR = new Parcelable.Creator<Evenement>() {
        public Evenement createFromParcel(Parcel in) {
            return new Evenement(in);
        }

        public Evenement[] newArray(int size) {
            return new Evenement[size];
        }
    };

    public String construireDateEvt() {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hours = calendar.get(Calendar.HOUR);
            int minutes = calendar.get(Calendar.MINUTE);
            String dateEvt= new String(String.valueOf(day));
            dateEvt = dateEvt.concat("/");
            dateEvt = dateEvt.concat(String.valueOf(month));
            dateEvt = dateEvt.concat("/");
            dateEvt = dateEvt.concat(String.valueOf(year));
            dateEvt = dateEvt.concat(" à ");
            if(hours<10)
                dateEvt = dateEvt.concat("0");
            dateEvt = dateEvt.concat(String.valueOf(hours));
            dateEvt = dateEvt.concat("h");
            if(minutes<10)
                dateEvt.concat("0");
            dateEvt = dateEvt.concat(String.valueOf(minutes));

            return dateEvt;
    }
}
