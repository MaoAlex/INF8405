package com.example.alexmao.modeledonnees.BDDInterne;

import android.content.ContentValues;
import android.content.Context;

import com.example.alexmao.modeledonnees.classeApp.Evenement;
import com.example.alexmao.modeledonnees.classeApp.Utilisateur;

/**
 *Classe permettant de connaître les participants à un événement
 * A COMPLETER
 */
public class ParticipantEvenementBDD extends AbstractBDD {

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_NOMBRE_PARTICIPANT = 1;
    private static final int NUM_COL_DATE_EVENENEMENT = 2;
    private static final int NUM_COL_PHOTO = 3;
    private static final int NUM_COL_SPORT_ASSOCIE = 4;
    private static final int NUM_COL_NOM_EVENEMENT = 5;
    private static final int NUM_COL_ID_FIREBASE = 6;
    private static final int NUM_COL_ID_GROUPE = 7;
    private static final int NUM_COL_LATITUDE = 8;
    private static final int NUM_COL_LONGITUDE = 9;
    private static final int NUM_COL_NOM_LIEU = 10;
    private static final int NUM_COL_ID_ORGANISATEUR = 11;
    private static final int NUM_COL_VISIBILITE = 12;
    private static final String TAG = "ParticipantEvenementBDD";

    public ParticipantEvenementBDD(Context pContext) {
        super(pContext);
    }

    public long insererParticipant(Evenement evenement, Utilisateur utilisateur){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs l'id de l'évenement avec l'id de l'utilisateur interessé
        ContentValues values = new ContentValues();
        values.put(Colonne.ID_EVENEMENT, evenement.getIdBDD());
        values.put(Colonne.ID_UTILISATEUR, utilisateur.getIdBDD());

        return database_.insert(Table.PARTICIPANT_EVENEMENT, null, values);
    }

    //méthode permettant la suppresion d'un événement de la table evenement selon l'id
    public int supprimerParticipantEvenement(int idEvenement, int idUtilisateur){
        //Suppression d'un interet pour un evenement d'un utilisateur de la BDD
        return database_.delete(Table.PARTICIPANT_EVENEMENT, Colonne.ID_EVENEMENT + " = " + idEvenement
                + " AND " + Colonne.ID_UTILISATEUR + " = " + idUtilisateur, null);
    }


}
