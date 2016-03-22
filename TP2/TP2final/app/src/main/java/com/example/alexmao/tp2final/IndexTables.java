package com.example.alexmao.tp2final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by alexMAO on 20/03/2016.
 */
public class IndexTables extends AbstractBDD {

    private static final String TABLE_INDEX = "table_index";
    private static final String COL_KEY = "id";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_EVENEMENT_ID = "evenement_id";

    private static final String WHERE_ID_EQUALS = MaBaseSQLite.COL_ID  + " =?";

    public IndexTables(Context pContext) {
        super(pContext);
    }

    public void creationIndex() {
        ContentValues values = new ContentValues();

        values.put(COL_KEY, 1);
        values.put(COL_USER_ID, 0);
        values.put(COL_EVENEMENT_ID, 0);


        //on insère l'objet dans la BDD via le ContentValues

        database_.insert(TABLE_INDEX, null, values);
        //database_.execSQL("INSERT INTO table_groupe(est_organisateur,user_id,nom_du_groupe, id) VALUES (1,1,'Teiouiug', 90)");
        Log.d("GroupeBDD", "Creation de l'index ");


    }

    public void incrementeUser(User user) {
        //Suppression d'un utilisateur de la table Groupe de la BDD grâce à l'ID
        ContentValues values = new ContentValues();
        String query = "SELECT " + COL_USER_ID + ", " + COL_EVENEMENT_ID +
                 " FROM "
                + TABLE_INDEX ;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);

        if(cursor!=null) {
            values.put(COL_KEY, 1);
            values.put(COL_USER_ID, cursor.getInt(0)+1);
            values.put(COL_EVENEMENT_ID, cursor.getInt(1));
        }
        database_.update(TABLE_INDEX, values, COL_KEY + " = 1", null);
    }

    public void incrementeEvenement(Evenement evenement) {
        //Suppression d'un utilisateur de la table Groupe de la BDD grâce à l'ID
        ContentValues values = new ContentValues();
        String query = "SELECT " + COL_USER_ID + ", " + COL_EVENEMENT_ID +
                " FROM "
                + TABLE_INDEX ;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);

        if(cursor!=null) {
            values.put(COL_KEY, 1);
            values.put(COL_USER_ID, cursor.getInt(0));
            values.put(COL_EVENEMENT_ID, cursor.getInt(1)+1);
        }
        database_.update(TABLE_INDEX, values, COL_KEY + " = 1", null);
    }

    public int getIndiceUser(){
        String query = "SELECT " + COL_USER_ID + ", " + COL_EVENEMENT_ID +
                " FROM "
                + TABLE_INDEX ;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);

        if(cursor!=null) {
           return cursor.getInt(0);
        }
        return -1;
    }

    public int getIndiceEvenement(){
        String query = "SELECT " + COL_USER_ID + ", " + COL_EVENEMENT_ID +
                " FROM "
                + TABLE_INDEX ;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);

        if(cursor!=null) {
            return cursor.getInt(1);
        }
        return -1;
    }
}
