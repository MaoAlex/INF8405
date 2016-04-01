package com.example.alexmao.tp2final.calendrier;

import java.util.Date;

/**
 * Created by Fabien on 23/03/2016.
 */
public class EvenementCalendrier implements Comparable<EvenementCalendrier> {
    private String titre;
    private Date dateDebut;
    private Date dateFin;

    public EvenementCalendrier(String titre, Date dateDebut, Date dateFin) {
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int compareTo(EvenementCalendrier evenement2) {
        return dateDebut.compareTo(evenement2.dateDebut);
    }
}
