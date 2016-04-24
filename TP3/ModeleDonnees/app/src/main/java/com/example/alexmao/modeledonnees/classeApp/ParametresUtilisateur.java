package com.example.alexmao.modeledonnees.classeApp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fabien on 02/04/2016.
 */
public class ParametresUtilisateur implements Parcelable {
    private int rayon;
    private boolean localisation;
    private boolean masquerNom;

    public ParametresUtilisateur(Parcel in) {
        rayon = in.readInt();
        localisation = in.readByte() != 0;
        masquerNom = in.readByte() != 0;
    }


    public int getRayon() {
        return rayon;
    }

    public void setRayon(int rayon) {
        this.rayon = rayon;
    }

    public boolean isLocalisation() {
        return localisation;
    }

    public void setLocalisation(boolean localisation) {
        this.localisation = localisation;
    }

    public boolean isMasquerNom() {
        return masquerNom;
    }

    public void setMasquerNom(boolean masquerNom) {
        this.masquerNom = masquerNom;
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
        dest.writeInt(rayon);
        dest.writeByte((byte) (localisation ? 1 : 0));
        dest.writeByte((byte) (masquerNom ? 1 : 0));
    }

    // Creator
    public static final Parcelable.Creator<ParametresUtilisateur> CREATOR = new Parcelable.Creator<ParametresUtilisateur>() {
        public ParametresUtilisateur createFromParcel(Parcel in) {
            return new ParametresUtilisateur(in);
        }

        public ParametresUtilisateur[] newArray(int size) {
            return new ParametresUtilisateur[size];
        }
    };
}
