package com.example.alexmao.tp2final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexMAO on 19/03/2016.
 */
public class LieuBDD extends AbstractBDD{

    private static final String TABLE_LIEU = "table_lieu";
    private static final String COL_KEY = "id";
    private static final String COL_TYPE = "type";
    private static final String COL_NOM_LIEU = "nom_lieu";
    private static final String COL_POSITION_X = "position_x";
    private static final String COL_POSITION_Y = "position_y";
    private static final String COL_EVEVENEMENT_ID = "evenement_id";

    private static final String WHERE_ID_EQUALS = MaBaseSQLite.COL_ID  + " =?";
    private static final String DEBUG_TAG = "LieuBDD";

    public LieuBDD(Context pContext) {
        super(pContext);
    }

    public void insertLieu(String type, String nomLieu, float positionX, float positionY, int eveId) {
         ContentValues values = new ContentValues();
        values.put(COL_TYPE, type);
        values.put(COL_NOM_LIEU, nomLieu);
        values.put(COL_POSITION_X, positionX);
        values.put(COL_POSITION_Y, positionY);
        values.put(COL_EVEVENEMENT_ID, eveId);
        database_.insert(TABLE_LIEU, null, values);
        Log.d(DEBUG_TAG, "Insertion du lieu faite ");


    }

    public void removeLieu(String nomLieu) {
        //Suppression d'un lieu de la table Groupe de la BDD grâce au nom
        database_.delete(TABLE_LIEU, COL_NOM_LIEU + " = ", new String[]{nomLieu});
    }

    public void removeLieuParType(String type) {
        database_.delete(TABLE_LIEU, COL_TYPE + " = ?", new String[]{type});
    }

    public HashMap<Integer, ArrayList<Lieu>> getLieux() {
       HashMap<Integer, ArrayList<Lieu>> lieux = new HashMap<Integer, ArrayList<Lieu>>();

        String query = "SELECT " + COL_TYPE + ","
                + COL_NOM_LIEU + "," + COL_POSITION_X
                + "," + COL_POSITION_Y + ","
                + COL_EVEVENEMENT_ID + " FROM "
                + TABLE_LIEU ;
      Cursor cursor = database_.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            Lieu lieu = new Lieu();
            lieu.setType(cursor.getString(0));
            lieu.setNomLieu(cursor.getString(1));
            lieu.setLocalisationLieu(new Localisation(cursor.getFloat(2), cursor.getFloat(3)));
            int idEve = cursor.getInt(4);
            lieu.setEvenementIdRelie(idEve);
       //Ajout au bon endroit dans la Hashmap
            if(lieux.containsKey(idEve)) {
                lieux.get(idEve).add(lieu);
                Log.d(DEBUG_TAG, "Ajout du lieu reussi");
            }else{
                ArrayList<Lieu> lTemp = new ArrayList<Lieu>();
                lTemp.add(lieu);
                lieux.put(idEve, lTemp);
                Log.d(DEBUG_TAG, "Ajout de la liste de lieu reussi");
            }


        }

        return lieux;
    }


    public ArrayList<Lieu> getlieuBDD(int idEvenement) {
        ArrayList<Lieu> lieux = new ArrayList<Lieu>();

        String query = "SELECT " + COL_TYPE + ","
                + COL_NOM_LIEU + "," + COL_POSITION_X
                + "," + COL_POSITION_Y + ","
                + COL_EVEVENEMENT_ID + " FROM "
                + TABLE_LIEU + " l WHERE l." + COL_EVEVENEMENT_ID + " = " + idEvenement;// +" ;" ;
        Cursor cursor = database_.rawQuery(query, null);

        while(cursor.moveToNext()){
            Lieu lieu = new Lieu();
            lieu.setType(cursor.getString(0));
            lieu.setNomLieu(cursor.getString(1));
            lieu.setLocalisationLieu(new Localisation(cursor.getFloat(2), cursor.getFloat(3)));
            int idEve = cursor.getInt(4);
            lieu.setEvenementIdRelie(idEve);
        }
        return lieux;
    }

    public void affichageLieux() {
        String query = "SELECT *"
                + " FROM "
                + TABLE_LIEU ;
        Cursor cursor = database_.rawQuery(query, null);
        //on insère l'objet dans la BDD via le ContentValues
        while (cursor.moveToNext()) {
            Log.d(DEBUG_TAG, cursor.getInt(0) + ", : " + cursor.getString(1)
                    + ", " + cursor.getString(2) + ", " + cursor.getFloat(3) + ", " + cursor.getFloat(4) + ", " + cursor.getInt(5));
        }
    }

}
