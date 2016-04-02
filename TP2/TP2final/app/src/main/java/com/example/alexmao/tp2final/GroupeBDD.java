package com.example.alexmao.tp2final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexMAO on 15/03/2016.
 * Classe permettrant le stockage dans la base sqlite des informations sur un groupe (utilisateurs et organisateur)
 */
public class GroupeBDD extends AbstractBDD {

    private static final String TABLE_GROUPE = "table_groupe";
    private static final String COL_KEY = "id";
    private static final String COL_GROUP_NAME = "nom_du_groupe";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_EST_ORGANISATEUR = "est_organisateur";
    public static final int EST_ORGANISATEUR = 1;
    public static final int NON_ORGANISATEUR = 0;


    private static final String WHERE_ID_EQUALS = MaBaseSQLite.COL_ID  + " =?";
    private static final String DEBUG_TAG = "GroupeBDD";

    public GroupeBDD(Context pContext) {
        super(pContext);
    }

    public void insertInGroup(String group_name, int user_id, boolean estOrganisateur) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
         ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(maBaseSQLite_.COL_GROUP_NAME, group_name);
        values.put(maBaseSQLite_.COL_USER_ID, user_id);
        Log.d(DEBUG_TAG, "valeur de l'id de l'utilisateur" + user_id);
        if(estOrganisateur)
                values.put(maBaseSQLite_.COL_EST_ORGANISATEUR, EST_ORGANISATEUR);
        else
            values.put(maBaseSQLite_.COL_EST_ORGANISATEUR, NON_ORGANISATEUR);

        //on insère l'objet dans la BDD via le ContentValues

        database_.insert(maBaseSQLite_.TABLE_GROUPE, null, values);
    }

    public void removeUserFromGroupByID(long id) {
        //Suppression d'un groupe de la table Groupe de la BDD grâce à l'ID
        database_.delete(maBaseSQLite_.TABLE_GROUPE, COL_USER_ID + " = " + id, null);
    }

    public void removeGroupe(String groupName) {
        //Suppression d'un g de la BDD grâce au nom
        database_.delete(maBaseSQLite_.TABLE_GROUPE, COL_GROUP_NAME + " = " + groupName, null);
    }

    //Recuperation des groupes associes a un utilisateur par son ID
    public ArrayList<String> getGroupesByUserId(int id) {
        Log.d(DEBUG_TAG, "recuperation des groupes pour l'utilisateur d'id : " + id);

        ArrayList<String> groupes = new ArrayList<>();
        String query = "SELECT " + COL_GROUP_NAME
                + " FROM "
                + MaBaseSQLite.TABLE_GROUPE + " WHERE "
                + MaBaseSQLite.COL_USER_ID + " = "
                + id;
        Cursor cursor = database_.rawQuery(query, null);
        Log.d(DEBUG_TAG, "getGroupesByUserId: " + query);
        while (cursor.moveToNext()){
            Log.d(DEBUG_TAG, "tour dans la recuperetion des groupes");
            groupes.add(cursor.getString(0));
        }
        return groupes;
    }


    //recuperation de tous les groupes de la base de donnees
    public HashMap<String,Groupe> getGroupes() {
        HashMap<String, Groupe> groupes = new HashMap<String, Groupe>();
        //Building query using INNER JOIN keyword
        String query = "SELECT " + COL_GROUP_NAME + ","
        + COL_EST_ORGANISATEUR + "," + MaBaseSQLite.COL_USER_ID
        + "," + MaBaseSQLite.COL_NOM + ","
        + MaBaseSQLite.COL_PRENOM + " FROM "
        + MaBaseSQLite.TABLE_USERS + " user INNER JOIN "
        + MaBaseSQLite.TABLE_GROUPE + " group ON user."
        + MaBaseSQLite.COL_ID + " = group."
        + MaBaseSQLite.COL_USER_ID;
        Cursor cursor = database_.rawQuery(query, null);
       while (cursor.moveToNext()){
            User user = new User();
            user.setId(cursor.getInt(2));
             //Ajout d'une nouvelle liste avec le nom du groupe non existant
            String nomGroupe = cursor.getString(0);
            if(groupes.containsKey(nomGroupe)) {
                groupes.get(nomGroupe).getUsers().add(user);
            }else{
                Groupe gTemp = new Groupe(nomGroupe);
                gTemp.getUsers().add(user);
                groupes.put(nomGroupe, gTemp);
            }

            if(cursor.getInt(1) == EST_ORGANISATEUR)
                groupes.get(nomGroupe).getOrganisateur().add(true);
            else
                groupes.get(nomGroupe).getOrganisateur().add(false);
        }
        return groupes;
    }

    //recuperation de tous les groupes de la base de donnees
    public HashMap<String,Groupe> getGroupesBDD() {
        HashMap<String, Groupe> groupes = new HashMap<String, Groupe>();
     //Building query using INNER JOIN keyword
        String query = "SELECT "
                + maBaseSQLite_.COL_GROUP_NAME+ ", "
                + maBaseSQLite_.COL_EST_ORGANISATEUR + "," + maBaseSQLite_.COL_USER_ID
                + "," + maBaseSQLite_.COL_NOM + ","
                + maBaseSQLite_.COL_PRENOM +
                " FROM "
                + maBaseSQLite_.TABLE_USERS + " u INNER JOIN "
                + maBaseSQLite_.TABLE_GROUPE + " g ON " + "u." + maBaseSQLite_.COL_ID +" = " + "g." + maBaseSQLite_.COL_USER_ID;// +" ;" ;

        Cursor cursor = database_.rawQuery(query, null);
        Log.d(DEBUG_TAG, query);

        while (cursor.moveToNext()){
            User user = new User();
            user.setId(cursor.getInt(2));
            user.setNom(cursor.getString(3));
            user.setPrenom(cursor.getString(4));
            //Ajout d'une nouvelle liste avec le nom du groupe non existant
            String nomGroupe = cursor.getString(0);
            if(groupes.containsKey(nomGroupe)) {
                groupes.get(nomGroupe).getUsers().add(user);
            }else{
                Groupe gTemp = new Groupe(nomGroupe);
                gTemp.getUsers().add(user);
                groupes.put(nomGroupe, gTemp);
            }
            if(cursor.getInt(1) == EST_ORGANISATEUR)
                groupes.get(nomGroupe).getOrganisateur().add(true);
            else
                groupes.get(nomGroupe).getOrganisateur().add(false);
        }
        return groupes;
    }


    //recuperation de tous les membres d'un groupe depuis la base de donnees interne avec le nom du groupe
    public Groupe getGroupeBDD(String groupName) {
       Groupe group = new Groupe(groupName);

        String query = "SELECT "
                + maBaseSQLite_.COL_GROUP_NAME + ","
                + COL_EST_ORGANISATEUR + ",u." + maBaseSQLite_.COL_ID
                + "," + maBaseSQLite_.COL_NOM + ","
                + maBaseSQLite_.COL_PRENOM + ", " + maBaseSQLite_.COL_MAIL +
                " FROM "
                + maBaseSQLite_.TABLE_USERS + " u INNER JOIN "
                + maBaseSQLite_.TABLE_GROUPE + " g ON " + "u." + maBaseSQLite_.COL_ID +" = " + "g." + maBaseSQLite_.COL_USER_ID +" WHERE g." + maBaseSQLite_.COL_GROUP_NAME + " =?";
        Cursor cursor = database_.rawQuery(query, new String[]{groupName});
        if(cursor!=null)
            Log.d(DEBUG_TAG, "Il y a " + cursor.getCount() + "lignes et " + cursor.getColumnCount() + "colonnes" );

        while(cursor.moveToNext()){
            int i =0;
            User test = new User(cursor.getString(3),cursor.getString(4));
            test.setMail_(cursor.getString(5));
            test.setId(cursor.getInt(2));

            if(cursor.getInt(1) == EST_ORGANISATEUR) {
                group.getOrganisateur().add(true);
                test.setEstOrganisateur_(true);
            }
            else {
                group.getOrganisateur().add(false);
                test.setEstOrganisateur_(true);
            }
            group.getUsers().add(test);

            Log.d(DEBUG_TAG, "Id de l'utilisateur : " + cursor.getInt(2) + " nom du groupe : "
                    + cursor.getString(0) + " estOrganisateur : " + cursor.getInt(1));

        }

        return group;
    }

    //fonciton de debug pour afficher les elements presents dans la table des groupes
    public void affichageGroupes() {
        Log.d(DEBUG_TAG, "debut affichage des groupes");

        String query = "SELECT *"
                + " FROM "
                + TABLE_GROUPE ;
         Cursor cursor = database_.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Log.d(DEBUG_TAG, cursor.getInt(0) + ", : " + cursor.getString(1)
                    + ", " + cursor.getInt(2) + ", : " + cursor.getString(3) + ", "+ cursor.getInt(4
            ));
        }
    }

}
