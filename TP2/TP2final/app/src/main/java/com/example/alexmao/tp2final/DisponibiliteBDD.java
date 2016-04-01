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
 * Classe permettant le stockage des données dans la base interne sqlite
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
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)

        values.put(COL_USER_ID, id_user);
        values.put(COL_DISPONIBILITE_DATE, date);
        values.put(COL_DISPONIBILITE_HEURE_DEBUT, heureDebut);
        values.put(COL_DISPONIBILITE_HEURE_FIN, heureFin);
        //on insère l'objet dans la BDD via le ContentValues
        database_.insert(maBaseSQLite_.TABLE_DISPONIBILITITE, null, values);


    }

    public void removeDisponibiliteUser(long id_user) {
        //Suppression grâce à l'ID
        database_.delete(maBaseSQLite_.TABLE_DISPONIBILITITE, COL_USER_ID + " = " + id_user, null);

    }

    public void removeDisponibilite(String disponibilite, String heureDebut, String heureFin) {
        database_.delete(maBaseSQLite_.TABLE_DISPONIBILITITE, COL_DISPONIBILITE_DATE + " = ?"
                + " AND " + COL_DISPONIBILITE_HEURE_DEBUT + " = ?" + " AND " + COL_DISPONIBILITE_HEURE_FIN + " = ?", new String[]{disponibilite, heureDebut, heureFin});
    }
    public void removeDisponibilite(String disponibilite) {
        //Suppression d'un g de la BDD grâce au nom
        database_.delete(maBaseSQLite_.TABLE_DISPONIBILITITE, COL_DISPONIBILITE_DATE + " = ?"
                , new String[]{disponibilite});
    }

    //Permet d'obtenir une hashmap permettant de les disponibilités des utilisateurs(key)
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

        Cursor cursor = database_.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){
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
    Cursor cursor = database_.rawQuery(query, null);
       while (cursor.moveToNext()) {
            Log.d(DEBUG_TAG, cursor.getInt(0) + ", : " + cursor.getString(1)
                    + ", " + cursor.getString(2) + ", et son mail est : " + cursor.getString(3) + ", " + cursor.getString(4));
        }
    }
}
