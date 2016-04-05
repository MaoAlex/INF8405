package com.example.alexmao.chat.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.alexmao.chat.classeApp.Evenement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *Classe permettant la manipulation de la table evenement
 * A COMPLETER
 */
public class EvenementBDD extends AbstractBDD {
    private static final String TAG = "EvenementBDD";

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

    public EvenementBDD(Context pContext) {
        super(pContext);
    }

    //insererEvenement permet d'inserer l'événement dans la BDD interne
    //Elle renvoie l'id de l'événement dans la BDD interne
    //!!!!!!!!!!!!!!!!!!!!!Verifier pour la date!!!!!!!!!!!!!!!!!!!
    public long insererEvenement(Evenement evenement){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs de l'evenement en fonction des colonnes
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Colonne.NOMBRE_PARTICIPANTS, evenement.getNbreMaxParticipants());
        //values.put(Colonne.DATE_EVENEMENT, evenement.getDate());
        if(evenement.getPhoto()!=null)
            values.put(Colonne.PHOTO, evenement.getPhoto().toString());
        else
            values.put(Colonne.PHOTO, "");
        //
        //!!!!!!!!!!!a completer!!!!!!!!!!!
        //
        //values.put(Colonne.ID_SPORT_ASSOCIE, evenement.getSport());
        values.put(Colonne.NOM_EVENEMENT, evenement.getNomEvenement());
        values.put(Colonne.ID_GROUPE, evenement.getGroupeAssocie().getIdBDD());
        values.put(Colonne.LATITUDE, evenement.getLatitude());
        values.put(Colonne.LONGITUDE, evenement.getLongitude());
        values.put(Colonne.NOM_LIEU, evenement.getLieu());
        values.put(Colonne.ID_FIREBASE, evenement.getIdFirebase());
        values.put(Colonne.ID_ORGANISATEUR, evenement.getOrganisateur().getIdBDD());
        values.put(Colonne.VISIBILITE, evenement.getVisibilite());

        //on insère l'objet dans la BDD via le ContentValues,
        return database_.insert(Table.EVENEMENT, null, values);
    }

    //méthode permettant de modifier un événement par son id et les nouvelles valeur de l'évenement
    public int modifierEvenement(int id, Evenement evenement){
        //Création d'un ContentValues (fonctionne comme une HashMap) pour la mise à jour
        //On va mettre les valeurs de l'evenement en fonction des colonnes
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Colonne.NOMBRE_PARTICIPANTS, evenement.getNbreMaxParticipants());
        //values.put(Colonne.DATE_EVENEMENT, evenement.getDate());
        if(evenement.getPhoto()!=null)
            values.put(Colonne.PHOTO, evenement.getPhoto().toString());
        else
            values.put(Colonne.PHOTO, "");
        //
        //!!!!!!!!!!!a completer!!!!!!!!!!!
        //
        //values.put(Colonne.ID_SPORT_ASSOCIE, evenement.getSport());
        values.put(Colonne.NOM_EVENEMENT, evenement.getNomEvenement());
        values.put(Colonne.ID_GROUPE, evenement.getGroupeAssocie().getIdBDD());
        values.put(Colonne.LATITUDE, evenement.getLatitude());
        values.put(Colonne.LONGITUDE, evenement.getLongitude());
        values.put(Colonne.NOM_LIEU, evenement.getLieu());
        values.put(Colonne.ID_FIREBASE, evenement.getIdFirebase());
        values.put(Colonne.ID_ORGANISATEUR, evenement.getOrganisateur().getIdBDD());
        values.put(Colonne.VISIBILITE, evenement.getVisibilite());

        return database_.update(Table.EVENEMENT, values, Colonne.ID_EVENEMENT + " = " + id, null);
    }

    //méthode permettant la suppresion d'un événement de la table evenement selon l'id
    public int supprimerEvenementParId(int id){
        //Suppression d'un evenement de la BDD grâce à l'ID
        return database_.delete(Table.EVENEMENT, Colonne.ID_EVENEMENT + " = " + id, null);
    }

    //méthode permettant de récuperer tous les événements stockés dans la BDD interne
    public ArrayList<Evenement> obtenirEvenements() {
        ArrayList<Evenement> listeEvenement = new ArrayList<>();
        String query = "SELECT *"
                + " FROM "
                + Table.EVENEMENT ;

        Log.d("query", query);
        Cursor c = database_.rawQuery(query, null);
        //
        //on insère l'objet dans la BDD via le ContentValues
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
            evenement.setSport(c.getString(NUM_COL_SPORT_ASSOCIE));
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

    //fonction de debuggage pour afficher les événements présents dans la BDD interne
    public void affichageEvenements() {
        String query = "SELECT * "
                + " FROM "
                + Table.EVENEMENT ;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //
        //on insère l'objet dans la BDD via le ContentValues
        while (cursor.moveToNext()) {
            Log.d(TAG, "L'id de l'evenement est : " + cursor.getInt(NUM_COL_ID)
                    + ", le nombre de participants est de : " + cursor.getInt(NUM_COL_NOMBRE_PARTICIPANT)
                    + ", la date de l'evenement est : " + cursor.getString(NUM_COL_DATE_EVENENEMENT)
                    + ", sa photo est : " + cursor.getString(NUM_COL_PHOTO)
                    + ", le sport associé est : " + cursor.getString(NUM_COL_SPORT_ASSOCIE)
                    + ", le nom de l'evenement est : " + cursor.getString(NUM_COL_NOM_EVENEMENT)
                    + ", l'id du groupe est : (" + cursor.getString(NUM_COL_ID_GROUPE)
                    + ", sa position est : (" + cursor.getDouble(NUM_COL_LATITUDE) + ", " + cursor.getDouble(NUM_COL_LONGITUDE)
                    + ", le nom du lieu est : " + cursor.getString(NUM_COL_NOM_LIEU)
                    + ", son id firebase est : " + cursor.getString(NUM_COL_ID_FIREBASE));
        }
        cursor.close();
    }
}
