package com.example.alexmao.tp2;

/**
 * Created by alexMAO on 14/03/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class UsersBDD extends AbstractBDD  {

    //private static final int VERSION_BDD = 1;
    //private static final String NOM_BDD = "Utilisateurs.db";

    private static final String TABLE_USERS = "table_Users";
    public static final String COL_ID = "id";

    private static final String COL_NOM = "Nom";
    private static final String COL_PRENOM = "Prenom";
    private static final String COL_MAIL = "mail";
    private static final String COL_PHOTO = "photo";

    private static final int NUM_COL_ID = 0;

    private static final int NUM_COL_NOM = 1;

    private static final int NUM_COL_PRENOM = 2;
    private static final String DEBUG_TAG = "UsersBDD" ;

    public UsersBDD(Context pContext) {
        super(pContext);
    }

    /*private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;

    public UsersBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }
*/
    public long insertUser(User user){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_NOM, user.getNom());
        values.put(COL_PRENOM, user.getPrenom());
        values.put(COL_MAIL, user.getMail_());
        values.put(COL_PHOTO, "");
        //on insère l'objet dans la BDD via le ContentValues
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)

        //on insère l'objet dans la BDD via le ContentValues


        //Log.d(DEBUG_TAG, "valeur du précédent index ajouté :" + database_.last_insert_rowid());

        //database_.execSQL("INSERT INTO table_groupe(est_organisateur,user_id,nom_du_groupe, id) VALUES (1,1,'Teiouiug', 90)");
        return database_.insert(maBaseSQLite_.TABLE_USERS, null, values);
    }

    public int updateUser(int id, User user){
        //La mise à jour d'un utilisateur dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel utilisateur on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_NOM, user.getNom());
        values.put(COL_PRENOM, user.getPrenom());
        values.put(COL_MAIL, user.getMail_());
        values.put(COL_PHOTO, "");
        return database_.update(maBaseSQLite_.TABLE_USERS, values, COL_ID + " = " + id, null);
    }

    public int removeUserWithID(int id){
        //Suppression d'un utilisateur de la BDD grâce à l'ID
        return database_.delete(maBaseSQLite_.TABLE_USERS, COL_ID + " = " + id, null);
    }

    public User getUserWithNom(String nom){
        //Récupère dans un Cursor les valeurs correspondant à un utilisateur contenu dans la BDD (ici on sélectionne le utilisateur grâce à son nom)
        Cursor c = database_.query(maBaseSQLite_.TABLE_USERS, new String[] {COL_ID, COL_NOM, COL_PRENOM, COL_MAIL}, COL_NOM + " LIKE \"" + nom +"\"", null, null, null, null);
        return cursorToUser(c);
    }

    //Cette méthode permet de convertir un cursor en un utilisateur
    private User cursorToUser(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un utilisateur
        User utilisateur = new User();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        utilisateur.setId(c.getInt(NUM_COL_ID));
        utilisateur.setNom(c.getString(NUM_COL_NOM));
        utilisateur.setPrenom(c.getString(NUM_COL_PRENOM));
        utilisateur.setMail_(c.getString(3));

        //On ferme le cursor
        c.close();

        //On retourne le utilisateur
        return utilisateur;
    }

   public void affichageUsers() {
       String query = "SELECT " + COL_ID + ","
               + COL_NOM + "," + COL_PRENOM + ", " + COL_MAIL
               + " FROM "
               + TABLE_USERS ;

       //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

       Log.d("query", query);
       Cursor cursor = database_.rawQuery(query, null);
       //
        //on insère l'objet dans la BDD via le ContentValues
       while (cursor.moveToNext()) {
           Log.d("UsersBDD", "L'id de l'utilisateur est : " + cursor.getInt(0) + ", son nom est : " + cursor.getString(1)
                   + ", son prenom est : " + cursor.getString(2) + ", et son mail est : " + cursor.getString(3));
       }
    }
}