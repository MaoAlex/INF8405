package com.example.alexmao.tp2final;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by alexMAO on 13/03/2016.
 */
//Classe contenant les differentes informations sur un utilisateur
public class User implements Parcelable {
    private int id_;
    private String nom_;
    private String prenom_;
    private String mail_;
    private Uri photo_;
    private boolean estOrganisateur_;
    public ArrayList<String> preference_;
    private Localisation localisation_;

    public User(){
        nom_ = "";
        prenom_ = "";
        mail_ = "";
        photo_ = null;
        estOrganisateur_ = false;
        localisation_=null;
    }

    public User(String nom_, String prenom_){
        this.nom_ = nom_;
        this.prenom_ = prenom_;
        mail_ = "";
        photo_ = null;
        estOrganisateur_ = false;
        localisation_=null;

    }
    public User(String nom_, String prenom_,  String mail_, Uri photo_,
                boolean estOrganisateur_, ArrayList<String> preference_, Localisation localisation_){
        this.nom_ = nom_;
        this.prenom_ = prenom_;
        this. mail_ = mail_;
        this.photo_ = photo_;
        this.estOrganisateur_ = estOrganisateur_;
        this.localisation_ = localisation_;
    }

    protected User(Parcel in) {
        id_ = in.readInt();
        nom_ = in.readString();
        prenom_ = in.readString();
        mail_ = in.readString();
        photo_ = in.readParcelable(Uri.class.getClassLoader());
        estOrganisateur_ = in.readByte() != 0;
        preference_ = in.createStringArrayList();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getNom() {
        return nom_;
    }

    public void setNom(String nom_) {
        this.nom_ = nom_;
    }

    public String getPrenom() {
        return prenom_;
    }

    public void setPrenom(String prenom_) {
        this.prenom_ = prenom_;
    }

    public String getMail_() {
        return mail_;
    }

    public void setMail_(String mail_) {
        this.mail_ = mail_;
    }

    public Uri getPhoto() {
        return photo_;
    }

    public void setPhoto(Uri photo) {
        this.photo_ = photo;
    }

    public boolean isEstOrganisateur_() {
        return estOrganisateur_;
    }

    public void setEstOrganisateur_(boolean estOrganisateur_) {
        this.estOrganisateur_ = estOrganisateur_;
    }

    public ArrayList<String> getPreference_(){
        return this.preference_;
    }

    public void setPreference_(ArrayList<String> preference_){
        this.preference_=preference_;
    }

    public String toString(){
        return "Nom : "+ nom_ + " Prenom : " + " Id : " + id_;
    }

    public int getId() {
        return id_;
    }

    public void setId(int id) {
        this.id_ = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_);
        dest.writeString(nom_);
        dest.writeString(prenom_);
        dest.writeString(mail_);
        dest.writeParcelable(photo_, flags);
        dest.writeByte((byte) (estOrganisateur_ ? 1 : 0));
        dest.writeStringList(preference_);
    }

    /*@Override
    public boolean equals(User u){
        return
    }*/
}
