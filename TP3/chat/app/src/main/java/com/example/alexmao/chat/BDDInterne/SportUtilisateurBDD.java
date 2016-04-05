package com.example.alexmao.chat.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alexmao.chat.classeApp.Utilisateur;

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

    public long insererSportUtilisateur(String sport, Utilisateur utilisateur){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs l'id de l'évenement avec l'id de l'utilisateur interessé
        ContentValues values = new ContentValues();
        values.put(Colonne.SPORT, sport);
        values.put(Colonne.ID_UTILISATEUR, utilisateur.getIdBDD());

        return database_.insert(Table.SPORT_UTILISATEUR, null, values);
    }

    public int supprimerSportUtilisateur(String sport, int idUtilisateur){
        //l'utilisateur n'aime plus ce sport, nous le supprimons donc
        return database_.delete(Table.SPORT_UTILISATEUR, Colonne.ID_UTILISATEUR + " = " + idUtilisateur
                + " AND " + Colonne.SPORT + " = ?", new String[]{sport});
    }

    public ArrayList<String> obtenirSportUtilisateur(int idUtilisateur){
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
}
