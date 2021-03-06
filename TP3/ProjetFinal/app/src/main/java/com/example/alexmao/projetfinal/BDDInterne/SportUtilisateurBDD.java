package com.example.alexmao.projetfinal.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;

/**
 *Classe permettant de connaître les sports qui intéressent les différents utilisateurs
 * Dans la base de données local, nous n'avons a priori besoin que de ceux de l'utilisateur courant
 */
public class SportUtilisateurBDD extends AbstractBDD {

    private static final String TAG = "SportUtilisateurBDD";

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_ID_UTILISATEUR = 1;
    private static final int NUM_COL_SPORT = 2;

    public SportUtilisateurBDD(Context pContext) {
        super(pContext);
    }

    public static long insererSportUtilisateur(String sport, Utilisateur utilisateur){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs l'id de l'évenement avec l'id de l'utilisateur interessé
        ContentValues values = new ContentValues();
        values.put(Colonne.SPORT, sport);
        values.put(Colonne.ID_UTILISATEUR, utilisateur.getIdBDD());

        return database_.insert(Table.SPORT_UTILISATEUR, null, values);
    }

    public static int supprimerSport(String sport, int idUtilisateur){
        //l'utilisateur n'aime plus ce sport, nous le supprimons donc
        return database_.delete(Table.SPORT_UTILISATEUR, Colonne.ID_UTILISATEUR + " = " + idUtilisateur
                + " AND " + Colonne.SPORT + " = ?", new String[]{sport});
    }

    public static void insererListeSport(Utilisateur utilisateur){
        if(utilisateur.getSports()!=null && !utilisateur.getSports().isEmpty()){
            for (String sport : utilisateur.getSports()){
                insererSportUtilisateur(sport, utilisateur);
            }
        }
    }

    public static int supprimerSportUtilisateur(long idUtilisateur){
        //l'utilisateur n'aime plus ce sport, nous le supprimons donc
        return database_.delete(Table.SPORT_UTILISATEUR, Colonne.ID_UTILISATEUR + " = " + idUtilisateur
                , null);
    }

    public  static ArrayList<String> obtenirSportUtilisateur(long idUtilisateur){
        ArrayList<String> listeSport = new ArrayList<>();

        //requete pour récupérer les différentes invitations à destination de l'utilisateur
        String query = "SELECT *"
                + " FROM "
                + Table.SPORT_UTILISATEUR + " WHERE " + Colonne.ID_UTILISATEUR + " = " + idUtilisateur ;

        Log.d("query", query);

        Cursor c = database_.rawQuery(query, null);
        //On récupère toutes les sports qui intéressent un utilisateur, a priori ici l'utilisateur courant
        while (c.moveToNext()){
            String sport = new String();
            sport = c.getString(NUM_COL_SPORT);
            listeSport.add(sport);
        }
        c.close();

        return listeSport;
    }

    public static void affichageSportUtilisateur() {
        String query = "SELECT *" + " FROM "
                + Table.SPORT_UTILISATEUR ;

        Log.d("query", query);
        Log.d(TAG, "Affichage de des sports et utilisateur");
        Cursor cursor = database_.rawQuery(query, null);
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            //On affiche les différents éléments
            Log.d(TAG, "L'id du truc est : " + cursor.getLong(NUM_COL_ID)
                    + ", id de l'utilisateur: " + cursor.getLong(NUM_COL_ID_UTILISATEUR)
                    + ", sport pratique : " + cursor.getString(NUM_COL_SPORT));

        }

        cursor.close();

    }
}
