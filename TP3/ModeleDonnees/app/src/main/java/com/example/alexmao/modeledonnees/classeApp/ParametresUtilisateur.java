package com.example.alexmao.modeledonnees.classeApp;

/**
 * Created by Fabien on 02/04/2016.
 */
public class ParametresUtilisateur {
    private int rayon;
    private boolean localisation;
    private boolean masquerNom;

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
}
