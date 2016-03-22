package com.example.alexmao.tp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.HashMap;

/**
 *Classe permettant de stocker dans la BDD les informations sur la localisation des utilisateurs
 */

public class LocalisationBDD extends  AbstractBDD {
    public static final String TABLE_LOCALISATION = "table_localisation";
    private static final String COL_KEY = "id";
    private static final String COL_POSITION_X = "position_x";
    private static final String COL_POSITION_Y = "position_y";
    private static final String COL_USER_ID = "user_id";

    private static final String WHERE_ID_EQUALS = MaBaseSQLite.COL_ID  + " =?";
    private static final String DEBUG_TAG = "LocalisationBDD";

    public LocalisationBDD(Context pContext) {
        super(pContext);
    }

    public void insertLocalisation(Localisation localisation, int userId) {

        ContentValues values = new ContentValues();

        values.put(COL_POSITION_X, localisation.getPositionX_());
        values.put(COL_POSITION_Y, localisation.getPositionY_());
        values.put(maBaseSQLite_.COL_USER_ID, userId);

        //on insère l'objet dans la BDD via le ContentValues

        database_.insert(maBaseSQLite_.TABLE_LOCALISATION, null, values);
        Log.d(DEBUG_TAG, "Insertion faite ");


    }

    public void removeLocalisation(int userId) {
        //Suppression d'un utilisateur de la table Groupe de la BDD grâce à l'ID
        database_.delete(maBaseSQLite_.TABLE_LOCALISATION, COL_USER_ID + " = " + userId, null);
    }


    // METHOD 1
    // Uses rawQuery() to query multiple tables

    public HashMap<Integer, Localisation> getLocalisations() {
         HashMap<Integer, Localisation> localisations = new HashMap<Integer, Localisation>();

        //Building query using INNER JOIN keyword
        String query = "SELECT " + COL_POSITION_X + ","
                + COL_POSITION_Y + "," + COL_USER_ID
                + " FROM "
                + TABLE_LOCALISATION ;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //ArrayList<User> userTemp = new ArrayList<User>();
        //ArrayList<Boolean> estOrganisateurTemp = new ArrayList<Boolean>();
        while (cursor.moveToNext()){

            Localisation localisation = new Localisation(cursor.getInt(0), cursor.getInt(1));
            int userId = cursor.getInt(2);
            Log.d("query", "Recupération du localisation de l'utilisateur : " + userId );

            //Ajout au bon endroit dans la Hashmap
                localisations.put(userId,localisation);
                Log.d("LocalisationBDD", "Ajout de la liste de localisation reussi");
        }

        return localisations;
    }


    public Localisation getlocalisationUser(int idUser) {
        //ArrayList<User> users = new ArrayList<User>();
        //ArrayList<Boolean> estOrganisateur = new ArrayList<Boolean>();

        String query = "SELECT " + COL_POSITION_X + ","
                + COL_POSITION_Y + " FROM "
                + TABLE_LOCALISATION + " l WHERE l." + COL_USER_ID + " = " + idUser;// +" ;" ;

        Log.d("query select", query);
        Cursor cursor = database_.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor!=null) {
            Localisation localisation = new Localisation(cursor.getInt(0), cursor.getInt(1));

            Log.d("LocalisationBDD", " : " + localisation.toString() + " de l'utilisateur : " + idUser);
            return localisation;
        }

        return null;
    }

    public void affichageLocalisations() {
        String query = "SELECT *"
                + " FROM "
                + TABLE_LOCALISATION ;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //
        //on insère l'objet dans la BDD via le ContentValues
        while (cursor.moveToNext()) {
            Log.d(DEBUG_TAG, cursor.getInt(0) + ", : "
                     + cursor.getFloat(1) + ", " + cursor.getFloat(2) + ", " + cursor.getInt(3));
        }
    }
}
