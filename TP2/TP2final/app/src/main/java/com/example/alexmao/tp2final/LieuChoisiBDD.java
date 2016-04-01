package com.example.alexmao.tp2final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by alexMAO on 19/03/2016.
 * classe permettant d'interagir avec la BDD interne pour le lieu choisi pour un evenement
 */

public class LieuChoisiBDD extends  AbstractBDD {
    public static final String TABLE_LIEU_CHOISI = "table_lieu_choisi";
    private static final String COL_KEY = "id";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_POSITION_X = "position_x";
    private static final String COL_POSITION_Y = "position_y";
    private static final String COL_PHOTO = "photo";
    private static final String COL_EVEVENEMENT_ID = "evenement_id";
    private static final String WHERE_ID_EQUALS = MaBaseSQLite.COL_ID  + " =?";
    private static final String DEBUG_TAG = "LieuChoisiBDD";

    public LieuChoisiBDD(Context pContext) {
        super(pContext);
    }

    public void insertLieuChoisi(LieuChoisi lieuChoisi) {
        ContentValues values = new ContentValues();
        values.put(COL_POSITION_X, lieuChoisi.getPositionLieu().getPositionX_());
        values.put(COL_POSITION_Y, lieuChoisi.getPositionLieu().getPositionY_());
        values.put(COL_DESCRIPTION, lieuChoisi.getDescription());
        values.put(COL_PHOTO, "");
        values.put(COL_EVEVENEMENT_ID, lieuChoisi.getEvenementId());
        database_.insert(maBaseSQLite_.TABLE_LIEU_CHOISI, null, values);
    }

    public void removeLieuChoisi(int eveId) {
        //Suppression d'un utilisateur de la table Groupe de la BDD grâce à l'ID
        database_.delete(maBaseSQLite_.TABLE_LIEU_CHOISI, COL_EVEVENEMENT_ID + " = " + eveId, null);
    }

      public LieuChoisi getLieuChoisi(int eveId) {

        LieuChoisi lieuChoisi = new LieuChoisi();
        //Building query using INNER JOIN keyword

        String query = "SELECT " + COL_DESCRIPTION + ", " + COL_POSITION_X + ","
                + COL_POSITION_Y + "," + COL_PHOTO + ", " + COL_EVEVENEMENT_ID
                + " FROM "
                + TABLE_LIEU_CHOISI + " WHERE " + COL_EVEVENEMENT_ID + " = " + eveId  ;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //ArrayList<User> userTemp = new ArrayList<User>();
        //ArrayList<Boolean> estOrganisateurTemp = new ArrayList<Boolean>();
        cursor.moveToFirst();
        if (cursor!=null){

            lieuChoisi.setDescription(cursor.getString(0));
            //lieuChoisi.setPhoto(cursor.getString(3));
            lieuChoisi.setPositionLieu(new Localisation(cursor.getFloat(1), cursor.getFloat(2)));

            Log.d("query", "Recupération du lieu choisi de l'utilisateur " );
        }

        return lieuChoisi;
    }

    public void affichageLieuChoisi() {
        String query = "SELECT *"
                + " FROM "
                + TABLE_LIEU_CHOISI ;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //
        //on insère l'objet dans la BDD via le ContentValues
        while (cursor.moveToNext()) {
            Log.d(DEBUG_TAG, cursor.getInt(0) + ", : " + cursor.getString(1)
                    + ", " + cursor.getString(2) + ", " + cursor.getFloat(3) + ", " + cursor.getFloat(4) + ", " + cursor.getInt(5));
        }
    }

}
