package com.example.alexmao.modeledonnees.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.alexmao.modeledonnees.classeApp.Evenement;
import com.example.alexmao.modeledonnees.classeApp.Utilisateur;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *Classe permettant de récupérer les evenements qui intéressent un utilisateur depuis la BDD interne
 *Classe permettant aussi de récupérer les utilisateurs qui sont intéressés par un événement
 * A COMPLETER
 */
public class EvenementInteresseBDD extends AbstractBDD {

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
    private static final String TAG = "EvenementInteresseBDD";

    public EvenementInteresseBDD(Context pContext) {
        super(pContext);
    }

    public long insererEvenementInteresse(Evenement evenement, Utilisateur utilisateur){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs l'id de l'évenement avec l'id de l'utilisateur interessé
        ContentValues values = new ContentValues();
        values.put(Colonne.ID_EVENEMENT, evenement.getIdBDD());
        values.put(Colonne.ID_UTILISATEUR, utilisateur.getIdBDD());

        return database_.insert(Table.EVENEMENT_INTERESSE, null, values);
    }

    //méthode permettant la suppresion d'un événement de la table evenement selon l'id
    public int supprimerEvenementInteresse(int idEvenement, int idUtilisateur){
        //Suppression d'un interet pour un evenement d'un utilisateur de la BDD
        return database_.delete(Table.EVENEMENT_INTERESSE, Colonne.ID_EVENEMENT + " = " + idEvenement
                + " AND " + Colonne.ID_UTILISATEUR + " = " +idUtilisateur, null);
    }


    public ArrayList<Evenement> obtenirEvenementUtilisateur(Utilisateur utilisateur){
        ArrayList<Evenement> listeEvenement = new ArrayList<>();

        //On crée la requete nous permettant de faire la jointure des tables
        //On récupère ici les événements qui intéressent un utilisateur
        String query = "SELECT * " + " FROM "
                + Table.EVENEMENT + " e INNER JOIN "
                + Table.EVENEMENT_INTERESSE + " ei ON ei."
                + Colonne.ID_EVENEMENT + " = e."
                + Colonne.ID_EVENEMENT + " WHERE ei." + Colonne.ID_UTILISATEUR + " = " + utilisateur.getIdBDD();

        Cursor c = database_.rawQuery(query, null);
        while (c.moveToNext()) {

            //On créé un événement
            Evenement evenement = new Evenement();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            evenement.setIdBDD(c.getInt(NUM_COL_ID));
            evenement.setNbreMaxParticipants(c.getInt(NUM_COL_NOMBRE_PARTICIPANT));
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            try {
                date = formatDate.parse(c.getString(NUM_COL_DATE_EVENENEMENT).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "Date: " + formatDate.format(date));
            evenement.setDate(date);
            if(c.getString(NUM_COL_PHOTO)!=""||c.getString(NUM_COL_PHOTO)!=null)
                evenement.setPhoto(Uri.parse(c.getString(NUM_COL_PHOTO)));
            //
            //!!!!!!!!!!!!!!!!!!!A COMPLETER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //
            //SPORT
            // evenement.set(c.getString());
            evenement.setNomEvenement(c.getString(NUM_COL_NOM_EVENEMENT));
            evenement.setLatitude(c.getDouble(NUM_COL_LATITUDE));
            evenement.setLatitude(c.getDouble(NUM_COL_LONGITUDE));
            evenement.setLieu(c.getString(NUM_COL_NOM_LIEU));
            evenement.setIdFirebase(c.getString(NUM_COL_ID_FIREBASE));
            //
            //!!!!!!!!!!!!!!!!!!!A COMPLETER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //
            //evenement.setGroupeAssocie();
            //evenement.setOrganisateur();
            evenement.setVisibilite(c.getString(NUM_COL_VISIBILITE));
            //on l'ajoute à la liste d'evenement
            listeEvenement.add(evenement);

        }
        c.close();


        return listeEvenement;
    }

    public ArrayList<Utilisateur> obtenirEvenementUtilisateur(Evenement evenement) {
        ArrayList<Utilisateur> listeUtilisateur = new ArrayList<>();

        return listeUtilisateur;
    }

}
