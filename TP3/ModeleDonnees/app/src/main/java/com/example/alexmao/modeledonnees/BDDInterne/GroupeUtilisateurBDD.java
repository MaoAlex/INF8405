package com.example.alexmao.modeledonnees.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.alexmao.modeledonnees.classeApp.Groupe;
import com.example.alexmao.modeledonnees.classeApp.Utilisateur;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alexMAO on 04/04/2016.
 */
public class GroupeUtilisateurBDD extends AbstractBDD {

    private static final String TAG = "GroupeUtilisateurBDD";

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_ID_CONVERSATION = 1;
    private static final int NUM_COL_ID_FIREBASE = 2;
    private static final int NUM_COL_ID_EVENEMENT = 3;


    private static final int NUM_COL_ID_UTILISATEUR = 0;
    private static final int NUM_COL_NOM = 1;
    private static final int NUM_COL_PRENOM = 2;
    private static final int NUM_COL_DATE_NAISSANCE = 3;
    private static final int NUM_COL_MAIL = 4;
    private static final int NUM_COL_PHOTO = 5;
    private static final int NUM_COL_LATITUDE = 6;
    private static final int NUM_COL_LONGITUDE = 7;
    private static final int NUM_COL_ID_FIREBASE_UTILISATEUR = 8;

    public GroupeUtilisateurBDD(Context pContext) {
        super(pContext);
    }

    public long insererGroupeUtilisateur(Groupe groupe, Utilisateur utilisateur){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs l'id du groupe avec l'id de la utilisateur
        ContentValues values = new ContentValues();
        values.put(Colonne.ID_GROUPE, groupe.getIdBDD());
        values.put(Colonne.ID_UTILISATEUR, utilisateur.getIdBDD());

        return database_.insert(Table.GROUPE_UTILISATEUR, null, values);
    }

    //méthode permettant de supprimer un utilisateur d'un groupe
    public int supprimerGroupeUtilisateur(int idGroupe, int idUtilisateur){
        return database_.delete(Table.GROUPE_UTILISATEUR, Colonne.ID_GROUPE + " = " + idGroupe
                + " AND " + Colonne.ID_UTILISATEUR + " = " + idUtilisateur, null);
    }

    //méthode permettant d'otenir les différents groupes auxquels un utilisateur appartient
    public ArrayList<Groupe> obtenirGroupeUtilisateur(Utilisateur utilisateur){
        ArrayList<Groupe> listeGroupe = new ArrayList<>();

        //On crée la requete nous permettant de faire la jointure des tables
        //On récupère ici les groupe qui intéressent un utilisateur
        String query = "SELECT * " + " FROM "
                + Table.GROUPE + " g INNER JOIN "
                + Table.GROUPE_UTILISATEUR + " gu ON gu."
                + Colonne.ID_GROUPE + " = g."
                + Colonne.ID_GROUPE + " WHERE gu." + Colonne.ID_UTILISATEUR + " = " + utilisateur.getIdBDD();

        Cursor c = database_.rawQuery(query, null);
        while (c.moveToNext()) {

            //On créé un événement
            Groupe groupe = new Groupe();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            groupe.setIdBDD(c.getInt(NUM_COL_ID));
            groupe.setIdFirebase(c.getString(NUM_COL_ID_FIREBASE));
            //
            //!!!!!!!!!!!!!!!!!!!A COMPLETER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //
            //groupe.setConversation(c.getInt());
            groupe.setListeMembre(obtenirGroupeUtilisateur(groupe));
            //groupe.setEvenement();

            listeGroupe.add(groupe);
        }
        c.close();

        return listeGroupe;
    }

    //méthode permettant d'obtenir les différents utilisateurs d'un groupe donné
    public ArrayList<Utilisateur> obtenirGroupeUtilisateur(Groupe groupe) {
        ArrayList<Utilisateur> listeUtilisateur = new ArrayList<>();

        //On crée la requete nous permettant de faire la jointure des tables
        //On récupère ici les groupe qui intéressent un utilisateur
        String query = "SELECT * " + " FROM "
                + Table.UTILISATEUR + " u INNER JOIN "
                + Table.GROUPE_UTILISATEUR + " gu ON gu."
                + Colonne.ID_UTILISATEUR + " = u."
                + Colonne.ID_UTILISATEUR + " WHERE gu." + Colonne.ID_GROUPE + " = " + groupe.getIdBDD();

        //on lance la requête
        Cursor c = database_.rawQuery(query, null);
        while (c.moveToNext()) {

            //On créé un événement
            Utilisateur utilisateur = new Utilisateur();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            utilisateur.setIdBDD(c.getInt(NUM_COL_ID_UTILISATEUR));
            utilisateur.setNom(c.getString(NUM_COL_NOM));
            utilisateur.setPrenom(c.getString(NUM_COL_PRENOM));
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            try {
                date = formatDate.parse(c.getString(NUM_COL_DATE_NAISSANCE).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "Date: " + formatDate.format(date));
            utilisateur.setDateNaissance(date);
            utilisateur.setMail(c.getString(NUM_COL_MAIL));
            if(c.getString(NUM_COL_PHOTO)!=""||c.getString(NUM_COL_PHOTO)!=null)
                utilisateur.setPhoto(Uri.parse(c.getString(NUM_COL_PHOTO)));
            utilisateur.setLatitude(c.getDouble(NUM_COL_LATITUDE));
            utilisateur.setLatitude(c.getDouble(NUM_COL_LONGITUDE));
            utilisateur.setIdFirebase(c.getString(NUM_COL_ID_FIREBASE_UTILISATEUR));
        }
        c.close();

        return listeUtilisateur;
    }

}
