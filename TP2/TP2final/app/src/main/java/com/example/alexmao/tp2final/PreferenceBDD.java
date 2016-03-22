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


        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_GROUPE;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //ArrayList<User> userTemp = new ArrayList<User>();
        //ArrayList<Boolean> estOrganisateurTemp = new ArrayList<Boolean>();

        while (cursor.moveToNext()){

            User user = new User();
            Log.d("query", "Recupération de l'id de l'utilisateur");

            user.setId(cursor.getInt(2));
            //user.setNom(cursor.getString(3));
            //user.setPrenom(cursor.getString(4));

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
