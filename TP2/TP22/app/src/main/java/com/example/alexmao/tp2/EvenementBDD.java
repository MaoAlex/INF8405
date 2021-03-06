package com.example.alexmao.tp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by alexMAO on 19/03/2016.
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
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //database_ = maBaseSQLite_.getWritableDatabase();
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_EVENEMENT_NAME, evenement.getNomEvenement());
        values.put(COL_EVENEMENT_ID, evenement.getEvenementId());
        values.put(COL_GROUP_NAME, evenement.getGroupName());
        //on insère l'objet dans la BDD via le ContentValues

        database_.insert(TABLE_EVENEMENT, null, values);

        //database_.execSQL("INSERT INTO table_groupe(est_organisateur,user_id,nom_du_groupe, id) VALUES (1,1,'Teiouiug', 90)");
        Log.d(DEBUG_TAG, "Insertion du evenement faite ");


    }

    public void removeEvenement(String nomEvenement) {
        //Suppression d'un utilisateur de la table Groupe de la BDD grâce à l'ID
        database_.delete(TABLE_EVENEMENT, COL_EVENEMENT_NAME + " =  ?", new String[]{nomEvenement});
    }

    public void removeEvenementGroup(String groupName) {
        //Suppression d'un g de la BDD grâce au nom
        database_.delete(TABLE_EVENEMENT, COL_GROUP_NAME + " = ?" , new String[]{groupName});
    }

    // METHOD 1
    // Uses rawQuery() to query multiple tables
    public int getEvenement(String evenementName){
        //Building query using INNER JOIN keyword
        String query = "SELECT " + COL_EVENEMENT_ID + ", "
                + COL_EVENEMENT_NAME + ", " + COL_GROUP_NAME
                + " FROM "
                + TABLE_EVENEMENT + " e WHERE e." + COL_EVENEMENT_NAME + " = ?" ;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_EVENEMENT;

        Log.d("query", query);
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
                + COL_EVENEMENT_NAME
                + " FROM "
                + TABLE_EVENEMENT + " e WHERE e." + COL_GROUP_NAME + " = ?" ;
        //String query = "SELECT * FROM " + TABLE_EVENEMENT + ";";

        Log.d("query select", query);
        //Cursor cursor = database_.rawQuery(query, new String[] {groupName});
        Cursor cursor = database_.rawQuery(query, new String[]{groupName});
        //ArrayList<User> userTemp = new ArrayList<User>();
        //ArrayList<Boolean> estOrganisateurTemp = new ArrayList<Boolean>();
        //Log.d("GroupeBDD", "Id de l'utilisateur : " + cursor.getInt(3) + " nom du groupe : " + cursor.getString(1).toString() + " estOrganisateur : " + cursor.getInt(2));
        // Log.d("dzd", cursor.getColumnCount() + "  alooooooooo " + cursor.getInt(0));

        while(cursor.moveToNext()){
            Evenement evenement = new Evenement();
            evenement.setEvenementId(cursor.getInt(0));
            Log.d("query", "Recupération du evenement");
            evenement.setNomEvenement(cursor.getString(1));
            evenements.add(evenement);
            Log.d("EvenementBDD", " : " + cursor.getInt(3) + " nom du groupe : " + cursor.getString(1).toString() + " estOrganisateur : " + cursor.getInt(2));

        }
        return evenements;
    }

    public void affichageEvenements() {
        String query = "SELECT *"
                + " FROM "
                + maBaseSQLite_.TABLE_EVENEMENT ;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //on insère l'objet dans la BDD via le ContentValues
        while (cursor.moveToNext()) {
            Log.d(DEBUG_TAG, cursor.getInt(0) + ", " + cursor.getString(1)
                    + ", " + cursor.getInt(2) +   ", " + cursor.getString(3)  );
        }
    }

}