package com.example.alexmao.tp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by alexMAO on 15/03/2016.
 */
public class GroupeBDD extends AbstractBDD {

    private static final String TABLE_GROUPE = "table_groupe";
    private static final String COL_KEY = "id";
    private static final String COL_GROUP_NAME = "nom_du_groupe";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_EST_ORGANISATEUR = "est_organisateur";
    //public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_GROUPE + " (" + COL_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_GROUP_NAME + " TEXT, " + COL_USER_ID + " TEXT" + COL_EST_ORGANISATEUR + "INTEGER);";
    //public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_GROUPE + ";";
    public static final int EST_ORGANISATEUR = 1;
    public static final int NON_ORGANISATEUR = 0;


    private static final String WHERE_ID_EQUALS = MaBaseSQLite.COL_ID  + " =?";

    public GroupeBDD(Context pContext) {
        super(pContext);
    }

    public void insertInGroup(String group_name, int user_id, boolean estOrganisateur) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //database_ = maBaseSQLite_.getWritableDatabase();
         ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)


            values.put(maBaseSQLite_.COL_GROUP_NAME, group_name);
            values.put(maBaseSQLite_.COL_USER_ID, user_id);
            if(estOrganisateur)
                values.put(maBaseSQLite_.COL_EST_ORGANISATEUR, EST_ORGANISATEUR);
            else
                values.put(maBaseSQLite_.COL_EST_ORGANISATEUR, NON_ORGANISATEUR);

        //on insère l'objet dans la BDD via le ContentValues

        database_.insert(maBaseSQLite_.TABLE_GROUPE, null, values);
        //database_.execSQL("INSERT INTO table_groupe(est_organisateur,user_id,nom_du_groupe, id) VALUES (1,1,'Teiouiug', 90)");
        Log.d("GroupeBDD", "Insertion faite ");


    }

    public void removeUserFromGroupByID(long id) {
        //Suppression d'un utilisateur de la table Groupe de la BDD grâce à l'ID
        database_.delete(maBaseSQLite_.TABLE_GROUPE, COL_USER_ID + " = " + id, null);
    }

    public void removeGroupe(String groupName) {
        //Suppression d'un g de la BDD grâce au nom
        database_.delete(maBaseSQLite_.TABLE_GROUPE, COL_GROUP_NAME + " = " + groupName, null);
    }
    /*public void updateGroupe(Groupe g ) {
    //La mise à jour d'un g dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel g on doit mettre à jour grâce à l'ID
        for (int i = 0;)
        ContentValues values = new ContentValues();

        values.put(COL_GROUP_NAME, g.getNomGroupe_());
        values.put(COL_USER_ID, g.getUsers_().get(i).getId());
        database_.update(TABLE_GROUPE, values, COL_GROUP_NAME + " = " + g.getNomGroupe_(), null);
    }*/
/*
    public Groupe getGroupeWithName(String groupName) {
        Cursor c = database_.query(TABLE_GROUPE, new String[] {COL_KEY, COL_GROUP_NAME, COL_USER_ID, COL_EST_ORGANISATEUR}, COL_GROUP_NAME + " LIKE \"" + groupName +"\"", null, null, null, null);
        return cursorToGroup(c);
    }

    //Cette méthode permet de convertir un cursor en un utilisateur
    private Groupe cursorToGroup(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un g
        Groupe g = new Groupe(c.getString(NUM_COL_GROUP_NAME));
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        ArrayList<Boolean> estOrganisateur = null;
        ArrayList<User> users = null;

        users.add(());
        g.setNom_(c.getString(NUM_COL_NOM));
        utilisateur.setPrenom_(c.getString(NUM_COL_PRENOM));
        //On ferme le cursor
        c.close();

        //On retourne le utilisateur
        return utilisateur;
    }*/

    // METHOD 1
    // Uses rawQuery() to query multiple tables

    public HashMap<String,Groupe> getGroupes() {
        //ArrayList<User> users = new ArrayList<User>();
        //ArrayList<Boolean> estOrganisateur = new ArrayList<Boolean>();
        HashMap<String, Groupe> groupes = new HashMap<String, Groupe>();
        /*
        String query = "SELECT " + EMPLOYEE_ID_WITH_PREFIX + ","
                + EMPLOYEE_NAME_WITH_PREFIX + "," + DataBaseHelper.EMPLOYEE_DOB
                + "," + DataBaseHelper.EMPLOYEE_SALARY + ","
                + DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + ","
                + DEPT_NAME_WITH_PREFIX + " FROM "
                + DataBaseHelper.EMPLOYEE_TABLE + " emp, "
                + DataBaseHelper.DEPARTMENT_TABLE + " dept WHERE emp."
                + DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + " = dept."
                + DataBaseHelper.ID_COLUMN;
        */

        //Building query using INNER JOIN keyword
        String query = "SELECT " + COL_GROUP_NAME + ","
        + COL_EST_ORGANISATEUR + "," + MaBaseSQLite.COL_USER_ID
        + "," + MaBaseSQLite.COL_NOM + ","
        + MaBaseSQLite.COL_PRENOM + " FROM "
        + MaBaseSQLite.TABLE_USERS + " user INNER JOIN "
        + MaBaseSQLite.TABLE_GROUPE + " group ON user."
        + MaBaseSQLite.COL_ID + " = group."
        + MaBaseSQLite.COL_USER_ID;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_GROUPE;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //ArrayList<User> userTemp = new ArrayList<User>();
        //ArrayList<Boolean> estOrganisateurTemp = new ArrayList<Boolean>();
        cursor.moveToFirst();
        do{

            User user = new User();
            Log.d("query", "Recupération de l'id de l'utilisateur");

            user.setId(cursor.getInt(2));
            //user.setNom(cursor.getString(3));
            //user.setPrenom(cursor.getString(4));

            //Achat d'une nouvelle liste avec le nom du groupe non existant
            String nomGroupe = cursor.getString(0);
            if(groupes.containsKey(nomGroupe)) {
                groupes.get(nomGroupe).getUsers().add(user);
            }else{
                Groupe gTemp = new Groupe(nomGroupe);
                gTemp.getUsers().add(user);
                groupes.put(nomGroupe, gTemp);
            }
            Log.d("query", "Creation du groupe");

            if(cursor.getInt(1) == EST_ORGANISATEUR)
                groupes.get(nomGroupe).getOrganisateur().add(true);
            else
                groupes.get(nomGroupe).getOrganisateur().add(false);

        }while (cursor.moveToNext());

        return groupes;
    }

    public HashMap<String,Groupe> getGroupesBDD() {
        //ArrayList<User> users = new ArrayList<User>();
        //ArrayList<Boolean> estOrganisateur = new ArrayList<Boolean>();
        HashMap<String, Groupe> groupes = new HashMap<String, Groupe>();
        /*
        String query = "SELECT " + EMPLOYEE_ID_WITH_PREFIX + ","
                + EMPLOYEE_NAME_WITH_PREFIX + "," + DataBaseHelper.EMPLOYEE_DOB
                + "," + DataBaseHelper.EMPLOYEE_SALARY + ","
                + DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + ","
                + DEPT_NAME_WITH_PREFIX + " FROM "
                + DataBaseHelper.EMPLOYEE_TABLE + " emp, "
                + DataBaseHelper.DEPARTMENT_TABLE + " dept WHERE emp."
                + DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + " = dept."
                + DataBaseHelper.ID_COLUMN;
        */

        //Building query using INNER JOIN keyword
        String query = "SELECT "
                + maBaseSQLite_.COL_GROUP_NAME
                + maBaseSQLite_.COL_EST_ORGANISATEUR + "," + maBaseSQLite_.COL_USER_ID
                + "," + maBaseSQLite_.COL_NOM + ","
                + maBaseSQLite_.COL_PRENOM +
                " FROM "
                + maBaseSQLite_.TABLE_USERS + " u INNER JOIN "
                + maBaseSQLite_.TABLE_GROUPE + " g ON " + "u." + maBaseSQLite_.COL_ID +" = " + "g." + maBaseSQLite_.COL_USER_ID;// +" ;" ;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //ArrayList<User> userTemp = new ArrayList<User>();
        //ArrayList<Boolean> estOrganisateurTemp = new ArrayList<Boolean>();
        while (cursor.moveToNext()){

            User user = new User();
            user.setId(cursor.getInt(2));
            user.setNom(cursor.getString(3));
            user.setPrenom(cursor.getString(4));
            //Achat d'une nouvelle liste avec le nom du groupe non existant
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



    public Groupe getGroupeBDD(String groupName) {
        //ArrayList<User> users = new ArrayList<User>();
        //ArrayList<Boolean> estOrganisateur = new ArrayList<Boolean>();
        Groupe group = new Groupe(groupName);
        /*
        String query = "SELECT " + EMPLOYEE_ID_WITH_PREFIX + ","
                + EMPLOYEE_NAME_WITH_PREFIX + "," + DataBaseHelper.EMPLOYEE_DOB
                + "," + DataBaseHelper.EMPLOYEE_SALARY + ","
                + DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + ","
                + DEPT_NAME_WITH_PREFIX + " FROM "
                + DataBaseHelper.EMPLOYEE_TABLE + " emp, "
                + DataBaseHelper.DEPARTMENT_TABLE + " dept WHERE emp."
                + DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + " = dept."
                + DataBaseHelper.ID_COLUMN;
        */

        //Building query using INNER JOIN keyword
        /*String query = "SELECT "
                + maBaseSQLite_.COL_GROUP_NAME + ","
                + COL_EST_ORGANISATEUR + "," + maBaseSQLite_.COL_USER_ID
                + "," + maBaseSQLite_.COL_NOM + ","
                + maBaseSQLite_.COL_PRENOM +
                " FROM "
                + maBaseSQLite_.TABLE_USERS + " user INNER JOIN "
                + maBaseSQLite_.TABLE_GROUPE + " group ON user."
                + maBaseSQLite_.COL_ID + " = group."
                + maBaseSQLite_.COL_USER_ID + ";";*/
    /*
        String query = "SELECT "
                + maBaseSQLite_.COL_GROUP_NAME + ","
                + COL_EST_ORGANISATEUR + "," + maBaseSQLite_.COL_USER_ID
                + "," + maBaseSQLite_.COL_NOM + ","
                + maBaseSQLite_.COL_PRENOM +
                " FROM "
                + maBaseSQLite_.TABLE_USERS + " u, "
                + maBaseSQLite_.TABLE_GROUPE + " g WHERE " + "g." + maBaseSQLite_.COL_ID +" = " + "u." + maBaseSQLite_.COL_USER_ID +  "g." +
                 maBaseSQLite_.COL_GROUP_NAME + " =?"; // u." + maBaseSQLite_.COL_ID ;*/

        /*String query = "SELECT "
                + maBaseSQLite_.COL_GROUP_NAME + ","
                + COL_EST_ORGANISATEUR + "," + maBaseSQLite_.COL_USER_ID
                + "," + maBaseSQLite_.COL_NOM + ","
                + maBaseSQLite_.COL_PRENOM +
                " FROM "
                + maBaseSQLite_.TABLE_USERS + " u INNER JOIN "
                + maBaseSQLite_.TABLE_GROUPE + " g ON " + "u." + maBaseSQLite_.COL_ID +" = " + "g." + maBaseSQLite_.COL_USER_ID +";" ;
        */
        String query = "SELECT "
                + maBaseSQLite_.COL_GROUP_NAME + ","
                + COL_EST_ORGANISATEUR + "," + maBaseSQLite_.COL_USER_ID
                + "," + maBaseSQLite_.COL_NOM + ","
                + maBaseSQLite_.COL_PRENOM +
                " FROM "
                + maBaseSQLite_.TABLE_USERS + " u INNER JOIN "
                + maBaseSQLite_.TABLE_GROUPE + " g ON " + "u." + maBaseSQLite_.COL_ID +" = " + "g." + maBaseSQLite_.COL_USER_ID +" WHERE g." + maBaseSQLite_.COL_GROUP_NAME + " =? ";// +" ;" ;

        //String query = "SELECT * FROM " + TABLE_GROUPE + ";";

        Log.d("query select", query);
        Cursor cursor = database_.rawQuery(query, new String[] {groupName});
        //Cursor cursor = database_.rawQuery(query, null);
        //ArrayList<User> userTemp = new ArrayList<User>();
        //ArrayList<Boolean> estOrganisateurTemp = new ArrayList<Boolean>();
        //Log.d("GroupeBDD", "Id de l'utilisateur : " + cursor.getInt(3) + " nom du groupe : " + cursor.getString(1).toString() + " estOrganisateur : " + cursor.getInt(2));
       // Log.d("dzd", cursor.getColumnCount() + "  alooooooooo " + cursor.getInt(0));
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            int i =0;
            User test = new User(cursor.getString(2),cursor.getString(3));
            group.getUsers().add(test);
            group.getOrganisateur().add(false);
            /*User user = new User();
            user.setId(cursor.getInt(3));
          //  user.setNom(cursor.getString(3));
          //  user.setPrenom(cursor.getString(4));
            //Achat d'une nouvelle liste avec le nom du groupe non existant
            group.getUsers_().add(user);*/
            /*
            if(cursor.getInt(1) == EST_ORGANISATEUR)
                group.getOrganisateur_().add(true);
            else
                group.getOrganisateur_().add(false);
            */
            Log.d("GroupeBDD", "Id de l'utilisateur : " + cursor.getInt(3) + " nom du groupe : " + cursor.getString(1).toString() + " estOrganisateur : " + cursor.getInt(2));

        }

        return group;
    }

    public void affichageGroupes() {
        String query = "SELECT *"
                + " FROM "
                + TABLE_GROUPE ;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //
        //on insère l'objet dans la BDD via le ContentValues
        while (cursor.moveToNext()) {
            Log.d("UsersBDD", cursor.getInt(0) + ", : " + cursor.getString(1)
                    + ", " + cursor.getInt(2) + ", : " + cursor.getInt(3));
        }
    }

}
