package com.example.alexmao.tp2final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by alexMAO on 15/03/2016.
 */
public class DisponibiliteBDD extends AbstractBDD {
    private static final String TABLE_DISPONIBILITITE = "table_disponibilite";

    private static final String COL_USER_ID = "user_id";
    private static final String COL_DISPONIBILITE_DATE = "date";
    private static final String COL_DISPONIBILITE_HEURE_DEBUT = "heure_debut";
    private static final String COL_DISPONIBILITE_HEURE_FIN = "heure_fin";
    private static final String WHERE_ID_EQUALS = MaBaseSQLite.COL_ID  + " =?";
    private static final String DEBUG_TAG = "DisponibiliteBDD";

    public DisponibiliteBDD(Context pContext) {
        super(pContext);
    }

    public void insertDisponibilite(int id_user, String date, String heureDebut, String heureFin ) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //database_ = maBaseSQLite_.getWritableDatabase();
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)

        values.put(COL_USER_ID, id_user);
        values.put(COL_DISPONIBILITE_DATE, date);
        values.put(COL_DISPONIBILITE_HEURE_DEBUT, heureDebut);
        values.put(COL_DISPONIBILITE_HEURE_FIN, heureFin);
        //on insère l'objet dans la BDD via le ContentValues

        database_.insert(maBaseSQLite_.TABLE_DISPONIBILITITE, null, values);
        //database_.execSQL("INSERT INTO table_groupe(est_organisateur,user_id,nom_du_groupe, id) VALUES (1,1,'Teiouiug', 90)");
        Log.d(DEBUG_TAG, "Insertion disponibilite faite ");


    }

    public void removeDisponibiliteUser(long id_user) {
        //Suppression d'un utilisateur de la table Groupe de la BDD grâce à l'ID
        database_.delete(maBaseSQLite_.TABLE_DISPONIBILITITE, COL_USER_ID + " = " + id_user, null);
        Log.d(DEBUG_TAG, "retrait fait ");

    }

    public void removeDisponibilite(String disponibilite, String heureDebut, String heureFin) {
        //Suppression d'un g de la BDD grâce au nom
        database_.delete(maBaseSQLite_.TABLE_DISPONIBILITITE, COL_DISPONIBILITE_DATE + " = ?"
                + " AND " + COL_DISPONIBILITE_HEURE_DEBUT + " = ?" + " AND " + COL_DISPONIBILITE_HEURE_FIN + " = ?", new String[]{disponibilite, heureDebut, heureFin});
        Log.d(DEBUG_TAG, "retraits faits ");
    }
    public void removeDisponibilite(String disponibilite) {
        //Suppression d'un g de la BDD grâce au nom
        database_.delete(maBaseSQLite_.TABLE_DISPONIBILITITE, COL_DISPONIBILITE_DATE + " = ?"
                , new String[]{disponibilite});
        Log.d(DEBUG_TAG, "retraits faits ");
    }

    // METHOD 1
    // Uses rawQuery() to query multiple tables
    //Permet d'obtenir une hashmap permettant de connaitre la liste des utilisateurs selon la disponibilite(key)
    public HashMap<Integer,ArrayList<Disponibilite>> getDisponibilites() {
    // A faire en faisant attention au linkage
        HashMap<Integer, ArrayList<Disponibilite>> disponibilites = new HashMap<Integer, ArrayList<Disponibilite>>();
        //Building query using INNER JOIN keyword
        String query = "SELECT " + maBaseSQLite_.COL_DISPONIBILITE_DATE + "," + maBaseSQLite_.COL_DISPONIBILITE_HEURE_DEBUT + ","
                + maBaseSQLite_.COL_DISPONIBILITE_HEURE_FIN + ","
                + " d." +maBaseSQLite_.COL_USER_ID + "," + MaBaseSQLite.COL_NOM
                + "," + MaBaseSQLite.COL_PRENOM
                + " FROM "
                + maBaseSQLite_.TABLE_USERS + " u INNER JOIN "
                + maBaseSQLite_.TABLE_DISPONIBILITITE + " d ON " + "u." + maBaseSQLite_.COL_ID
                +" = " + "d." + maBaseSQLite_.COL_USER_ID;// +" ;" ;


        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_GROUPE;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);

        //ArrayList<User> userTemp = new ArrayList<User>();
        //ArrayList<Boolean> estOrganisateurTemp = new ArrayList<Boolean>();
        cursor.moveToFirst();
        while (cursor.moveToNext()){

            //User user = new User();
            Log.d("query", "Recuperation de l'id de l'utilisateur");


            Disponibilite disponibilite = new Disponibilite();
            String dateDebut = cursor.getString(0);
            dateDebut += " " + cursor.getString(1);
            String dateFin = cursor.getString(0);
            dateFin += " " + cursor.getString(2);
            SimpleDateFormat test = new SimpleDateFormat("dd/MM/yy h:m:s");
            try {
                Date dateD = test.parse(dateDebut);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Date dateF = test.parse(dateFin);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return disponibilites;
    }

    //Affiche tous les éléments dans la table disponibilite
    public void affichageDisponibilites() {
        String query = "SELECT *"
                + " FROM "
                + TABLE_DISPONIBILITITE ;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //
        //on insère l'objet dans la BDD via le ContentValues
        while (cursor.moveToNext()) {
            Log.d(DEBUG_TAG, cursor.getInt(0) + ", : " + cursor.getString(1)
                    + ", " + cursor.getString(2) + ", et son mail est : " + cursor.getString(3) + ", " + cursor.getString(4));
        }
    }
}
