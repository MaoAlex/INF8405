package com.example.alexmao.tp2final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by alexMAO on 19/03/2016.
 *  * Classe permettant d'un evenement dans la base interne sqlite
 */
public class EvenementBDD extends AbstractBDD{

    public static final String TABLE_EVENEMENT = "table_evenement";
    public static final String COL_GROUP_NAME = "nom_du_groupe";
    private static final String COL_EVENEMENT_ID = "evenement_id";
    private static final String COL_EVENEMENT_NAME = "evenement_nom";
    private static final String WHERE_ID_EQUALS = MaBaseSQLite.COL_ID  + " =?";
    private static final String DEBUG_TAG = "EvenementBDD";

    public EvenementBDD(Context pContext) {
        super(pContext);
    }

    public void insertEvenement(Evenement evenement) {
         ContentValues values = new ContentValues();
        values.put(COL_EVENEMENT_NAME, evenement.getNomEvenement());
        values.put(COL_GROUP_NAME, evenement.getGroupName());
        String query = "SELECT " + COL_EVENEMENT_ID
                + " FROM "
                + maBaseSQLite_.TABLE_EVENEMENT ;
        Cursor cursor = database_.rawQuery(query, null);
        cursor.moveToLast();
        if (cursor!=null){
            values.put(COL_EVENEMENT_ID, cursor.getInt(0) + 1);
            database_.insert(TABLE_EVENEMENT, null, values);
        }
        else {
            database_.insert(TABLE_EVENEMENT, null, values);
            query = "SELECT "
                    + maBaseSQLite_.COL_KEY
                    + " FROM "
                    + maBaseSQLite_.TABLE_EVENEMENT ;
            cursor = database_.rawQuery(query, null);
            cursor.moveToLast();
            values.put(COL_EVENEMENT_ID, cursor.getInt(0));
            database_.update(TABLE_EVENEMENT,  values, maBaseSQLite_.COL_KEY + " = " + cursor.getInt(0), null );
        }
    }

    public void removeEvenement(String nomEvenement) {
        //Suppression d'un evenement de la BDD grâce au nom
        database_.delete(TABLE_EVENEMENT, COL_EVENEMENT_NAME + " =  ?", new String[]{nomEvenement});
    }

    public void removeEvenementGroup(String groupName) {
        //Suppression des evenements liés à un groupe
        database_.delete(TABLE_EVENEMENT, COL_GROUP_NAME + " = ?" , new String[]{groupName});
    }


    public int getEvenement(String evenementName){
        String query = "SELECT " + COL_EVENEMENT_ID + ", "
                + COL_EVENEMENT_NAME + ", " + COL_GROUP_NAME
                + " FROM "
                + TABLE_EVENEMENT + " e WHERE e." + COL_EVENEMENT_NAME + " = ?" ;

        Cursor cursor = database_.rawQuery(query, new String[]{evenementName});
        cursor.moveToFirst();
        if (cursor!=null)
            return cursor.getInt(0);
        else
            return -1;

    }


    //Récupération de tous les événements en fonction d'un groupe
    public ArrayList<Evenement> getEvenementBDD(String groupName) {
        ArrayList<Evenement> evenements = new ArrayList<Evenement>();
        String query = "SELECT " + COL_EVENEMENT_ID + ","
                + COL_EVENEMENT_NAME + ", " + COL_GROUP_NAME
                + " FROM "
                + TABLE_EVENEMENT + " e WHERE e." + COL_GROUP_NAME + " = ?" ;
         Cursor cursor = database_.rawQuery(query, new String[]{groupName});
       while(cursor.moveToNext()){
            Evenement evenement = new Evenement();
            evenement.setEvenementId(cursor.getInt(0));
            evenement.setNomEvenement(cursor.getString(1));
            evenement.setGroupName(cursor.getString(2));
            evenements.add(evenement);
        }
        return evenements;
    }

    public void affichageEvenements() {
        String query = "SELECT *"
                + " FROM "
                + maBaseSQLite_.TABLE_EVENEMENT ;
          Cursor cursor = database_.rawQuery(query, null);
        //on insère l'objet dans la BDD via le ContentValues
        while (cursor.moveToNext()) {
            Log.d(DEBUG_TAG, cursor.getInt(0) + ", " + cursor.getString(1)
                    + ", " + cursor.getInt(2) +   ", " + cursor.getString(3)  );
        }
    }

}