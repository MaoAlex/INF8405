package com.example.alexmao.tp2final.calendrier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Fabien on 23/03/2016.
 */
public class NonDisponibilites {
    private ArrayList<List<EvenementCalendrier>> listeListeEvenements;
    private List<Date[]> listeNonDisponibilites;
    private int sizeListes;
    private int[] sizeEvenements;
    private Date[] currentCouple;

    public NonDisponibilites() {
        this.listeListeEvenements = new ArrayList<>();
        this.listeNonDisponibilites = new ArrayList<>();
    }

    public void ajouterListeEvenements(List<EvenementCalendrier> listeEvenements) {
        this.listeListeEvenements.add(listeEvenements);
    }

    public void calculerNonDisponibilites() {
        calculerTailles();
        do {
            EvenementCalendrier premierEvenement = getMinimum();
            if(premierEvenement == null) {
                return;
            }
            currentCouple = new Date[2];
            currentCouple[0] = premierEvenement.getDateDebut();
            currentCouple[1] = premierEvenement.getDateFin();
            itererEvenements();
        } while(totalEvenements() > 0);
    }

    public EvenementCalendrier getMinimum() {
        // On recherche le minimum des hauts de pile
        EvenementCalendrier premierEvenement = null;
        for(int i=0; i<sizeListes; i++) {
            if(sizeEvenements[i]>0) {
                EvenementCalendrier evenement = this.listeListeEvenements.get(i).get(0);
                // Pas de minimum fixé -> On fixe cet événement comme le plus tôt
                if(premierEvenement == null) {
                    premierEvenement = evenement;
                }
                else if(evenement.getDateDebut().before(premierEvenement.getDateDebut())) {
                    premierEvenement = evenement;
                }
            }
        }

        return premierEvenement;
    }

    public void calculerTailles() {
        this.sizeListes = this.listeListeEvenements.size();
        this.sizeEvenements = new int[this.sizeListes];

        for(int i=0; i<sizeListes; i++) {
            sizeEvenements[i] = this.listeListeEvenements.get(i).size();
        }
    }

    public void itererEvenements() {
        boolean result = false;
        do {
            for (int i = 0; i < sizeListes; i++) {
                if (sizeEvenements[i] > 0) {
                    EvenementCalendrier evenement = this.listeListeEvenements.get(i).get(0);
                    if (evenement.getDateDebut().before(currentCouple[1])) {
                        // S'il termine après, on met à jour
                        if (evenement.getDateFin().before(currentCouple[1])) {
                            currentCouple[1] = evenement.getDateFin();
                            result = true;
                        }
                        // on dépile l'événement
                        this.listeListeEvenements.get(i).remove(evenement);
                        sizeEvenements[i]--;
                    }
                }
            }
        } while(result);
        this.listeNonDisponibilites.add(currentCouple);
    }

    public int totalEvenements() {
        int total = 0;
        for (int i = 0; i < sizeListes; i++) {
            total += this.sizeEvenements[i];
        }
        return total;
    }

    public List<Date[]> getListeNonDisponibilites() {
        return listeNonDisponibilites;
    }
}
