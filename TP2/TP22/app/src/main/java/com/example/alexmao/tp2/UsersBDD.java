package com.example.alexmao.tp2;

/**
 * Created by alexMAO on 14/03/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsersBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "Utilisateurs.db";

    private static final String TABLE_USERS = "table_Users";
    private static final String COL_ID = "ID";
    private static final String COL_NOM = "Nom";
    private static final String COL_PRENOM = "Prenom";


    private static final int NUM_COL_ID = 0;

    private static final int NUM_COL_NOM = 1;

    private static final int NUM_COL_PRENOM = 2;

    private SQLiteDatabase bdd;

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

    public long insertUser(User user){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_NOM, user.getNom_());
        values.put(COL_PRENOM, user.getPrenom_());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_USERS, null, values);
    }

    public int updateUser(int id, User user){
        //La mise à jour d'un utilisateur dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel utilisateur on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_NOM, user.getNom_());
        values.put(COL_PRENOM, user.getPrenom_());
        return bdd.update(TABLE_USERS, values, COL_ID + " = " +id, null);
    }

    public int removeUserWithID(int id){
        //Suppression d'un utilisateur de la BDD grâce à l'ID
        return bdd.delete(TABLE_USERS, COL_ID + " = " +id, null);
    }

    public User getUserWithNom(String nom){
        //Récupère dans un Cursor les valeurs correspondant à un utilisateur contenu dans la BDD (ici on sélectionne le utilisateur grâce à son nom)
        Cursor c = bdd.query(TABLE_USERS, new String[] {COL_ID, COL_NOM, COL_PRENOM}, COL_NOM + " LIKE \"" + nom +"\"", null, null, null, null);
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
        utilisateur.setNom_(c.getString(NUM_COL_NOM));
        utilisateur.setPrenom_(c.getString(NUM_COL_PRENOM));
        //On ferme le cursor
        c.close();

        //On retourne le utilisateur
        return utilisateur;
    }
}