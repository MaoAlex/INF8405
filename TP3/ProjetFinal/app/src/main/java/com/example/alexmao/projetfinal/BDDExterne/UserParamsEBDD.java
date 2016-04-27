package com.example.alexmao.projetfinal.BDDExterne;

/**
 * Created by filou on 09/04/16.
 */
public class UserParamsEBDD {
    private int rayon;
    private boolean localisation;
    private boolean masquerNom;

    public UserParamsEBDD() {
    }

    public UserParamsEBDD(int rayon, boolean localisation, boolean masquerNom) {

        this.rayon = rayon;
        this.localisation = localisation;
        this.masquerNom = masquerNom;
    }

    public UserParamsEBDD(UserParamsEBDD userParamsEBDD) {
        rayon = userParamsEBDD.getRayon();
        localisation = userParamsEBDD.isLocalisation();
        masquerNom = userParamsEBDD.isMasquerNom();
    }

    public void update(UserParamsEBDD userParamsEBDD) {
        if (userParamsEBDD != null) {
            rayon = userParamsEBDD.getRayon();
            localisation = userParamsEBDD.isLocalisation();
            masquerNom = userParamsEBDD.isMasquerNom();
        }
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

    @Override
    public String toString() {
        return "UserParamsEBDD{" +
                "rayon=" + rayon +
                ", localisation=" + localisation +
                ", masquerNom=" + masquerNom +
                '}';
    }
}
