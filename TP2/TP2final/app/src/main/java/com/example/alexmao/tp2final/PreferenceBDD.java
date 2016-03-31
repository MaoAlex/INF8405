package com.example.alexmao.tp2final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexMAO on 19/03/2016.
 *
 * Classe gérant les différentes préférences stockées dans la BDD interne
 */
public class PreferenceBDD extends AbstractBDD {
    private static final String TABLE_PREFERENCE = "table_preference";
    private static final String COL_ID_PREF = "id";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_PREFERENCE = "preference";


    private static final String WHERE_ID_EQUALS = MaBaseSQLite.COL_ID  + " =?";
    private static final String DEBUG_TAG = "PreferenceBDD";

    public PreferenceBDD(Context pContext) {
        super(pContext);
    }

    public void insertPreference(int id_user, String preference) {
        ContentValues values = new ContentValues();

        values.put(COL_USER_ID, id_user);
        values.put(COL_PREFERENCE, preference);
        database_.insert(maBaseSQLite_.TABLE_PREFERENCE, null, values);

        Log.d(DEBUG_TAG, "Insertion preference faite ");


    }

    public void removePreferenceUser(long id_user) {
        //Suppression d'un utilisateur de la table Groupe de la BDD grâce à l'ID
        database_.delete(maBaseSQLite_.TABLE_PREFERENCE, COL_USER_ID + " = " + id_user, null);
    }

    public void removePreference(String preference) {
        //Suppression d'un g de la BDD grâce au nom
        database_.delete(maBaseSQLite_.TABLE_PREFERENCE, COL_PREFERENCE + " = ?", new String[]{preference});
    }

    public ArrayList<String> getPreferencesForUser(int id) {

        ArrayList<String> preferences = new ArrayList<>();
        //Building query using INNER JOIN keyword
        String query = "SELECT " + COL_PREFERENCE
                + " FROM "
                + maBaseSQLite_.TABLE_PREFERENCE + " WHERE " + maBaseSQLite_.COL_USER_ID
                +" = " + id;// +" ;" ;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);

        while (cursor.moveToNext()){

            String pref = cursor.getString(0);
            Log.d("query", "Recupération des preferences de l'utilisateur : "+ pref);
            preferences.add(pref);
        }
        Log.d("PreferenceBDD", "Renvoie des preference");

        return preferences;
    }
    // METHOD 1
    // Uses rawQuery() to query multiple tables
    //Permet d'obtenir une hashmap permettant de connaitre la liste des utilisateurs selon la preference(key)
    public HashMap<String,ArrayList<User>> getPreferences() {

        HashMap<String, ArrayList<User>> preferences = new HashMap<String, ArrayList<User>>();
        //Building query using INNER JOIN keyword
        String query = "SELECT " + COL_PREFERENCE + ","
                + maBaseSQLite_.COL_USER_ID + "," + MaBaseSQLite.COL_NOM
                + "," + MaBaseSQLite.COL_PRENOM
                + " FROM "
                + maBaseSQLite_.TABLE_USERS + " u INNER JOIN "
                + maBaseSQLite_.TABLE_PREFERENCE + " p ON " + "u." + maBaseSQLite_.COL_ID
                +" = " + "p." + maBaseSQLite_.COL_USER_ID;// +" ;" ;

      Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);

        while (cursor.moveToNext()){

            User user = new User();
            Log.d("query", "Recupération de l'id de l'utilisateur");

            user.setId(cursor.getInt(1));
            user.setNom(cursor.getString(2));
            user.setPrenom(cursor.getString(3));

            //Achat d'une nouvelle liste avec le nom du groupe non existant
            String preference = cursor.getString(0);
            if(preferences.containsKey(preference)) {
                preferences.get(preference).add(user);
            }else{
                ArrayList<User> pTemp = new ArrayList<User>();
                pTemp.add(user);
                preferences.put(preference, pTemp);
            }
            Log.d("query", "Creation du groupe");

        }

        return preferences;
    }



    public void affichagePreferences() {
        String query = "SELECT *"
                + " FROM "
                + TABLE_PREFERENCE;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //
        //on insère l'objet dans la BDD via le ContentValues
        while (cursor.moveToNext()) {
            Log.d(DEBUG_TAG, cursor.getInt(0) + ", : " + cursor.getString(1)
                    + ", " + cursor.getInt(2));
        }
    }

}
