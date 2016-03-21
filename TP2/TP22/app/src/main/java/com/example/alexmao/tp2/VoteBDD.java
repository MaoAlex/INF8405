package com.example.alexmao.tp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.Date;

/**
 * Created by alexMAO on 19/03/2016.
 */
public class VoteBDD extends  AbstractBDD {
    public static final String TABLE_VOTE = "table_vote";
    private static final String COL_KEY = "id";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_EVENEMENT_ID = "evenement_id";
    private static final String COL_NOM_LIEU = "nom_lieu";
    private static final String COL_DATE = "date";
    private static final String WHERE_ID_EQUALS = MaBaseSQLite.COL_ID  + " =?";
    private static final String DEBUG_TAG = "VoteBDD";

    public VoteBDD(Context pContext) {
        super(pContext);
    }

    public void insertVote(int userId, int evenementId, String lieu, Date date) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //database_ = maBaseSQLite_.getWritableDatabase();
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)

        values.put(COL_USER_ID, userId);
        values.put(COL_EVENEMENT_ID, evenementId);
        values.put(COL_NOM_LIEU, lieu);
        values.put(COL_DATE, date.toString());

        //on insère l'objet dans la BDD via le ContentValues

        database_.insert(TABLE_VOTE, null, values);
        //database_.execSQL("INSERT INTO table_groupe(est_organisateur,user_id,nom_du_groupe, id) VALUES (1,1,'Teiouiug', 90)");
        Log.d(DEBUG_TAG, "Insertion faite ");


    }

    public void removeVote(int userId) {
        //Suppression d'un utilisateur de la table Groupe de la BDD grâce à l'ID
        database_.delete(maBaseSQLite_.TABLE_VOTE, COL_USER_ID + " = " + userId, null);
    }

    public void removeVote(int userId, int evenementID) {
        //Suppression d'un utilisateur de la table Groupe de la BDD grâce à l'ID
        database_.delete(maBaseSQLite_.TABLE_VOTE, COL_USER_ID + " = " + userId + " AND " + COL_EVENEMENT_ID + " = " + evenementID, null);
    }


    // METHOD 1
    // Uses rawQuery() to query multiple tables

    public void getVotes(Vote vote) {
        //Building query using INNER JOIN keyword
        String query = "SELECT " + COL_USER_ID + ","
                + COL_NOM_LIEU + "," + COL_EVENEMENT_ID + "," + COL_DATE
                + " FROM "
                + TABLE_VOTE ;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);

        while (cursor.moveToNext()){
            vote.votePourPropositions(cursor.getString(1));
            Log.d(DEBUG_TAG, "Ajout du vote pour : " + cursor.getString(1));
        }

    }


    public void affichageVotes() {
        String query = "SELECT *"
                + " FROM "
                + TABLE_VOTE;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //
        //on insère l'objet dans la BDD via le ContentValues
        while (cursor.moveToNext()) {
            Log.d("UsersBDD", cursor.getInt(0) + ", : " + cursor.getInt(
                    1)
                    + ", " + cursor.getInt(2) + ", : " + cursor.getString(3)
                    + ", " + cursor.getString(4));
        }
    }
}
