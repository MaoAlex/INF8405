package com.example.alexmao.chat.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.alexmao.chat.classeApp.Utilisateur;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *Classe permettant la manipulation des tables utilisateur et utilisateur connect�
 *
 */

public class UtilisateurBDD extends AbstractBDD {
    private static final String TAG = "UtilisateurBDD" ;

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_NOM = 1;
    private static final int NUM_COL_PRENOM = 2;
    private static final int NUM_COL_DATE_NAISSANCE = 3;
    private static final int NUM_COL_MAIL = 4;
    private static final int NUM_COL_PHOTO = 5;
    private static final int NUM_COL_LATITUDE = 6;
    private static final int NUM_COL_LONGITUDE = 7;
    private static final int NUM_COL_ID_FIREBASE = 8;

    public UtilisateurBDD(Context pContext) {
        super(pContext);
    }

    //insererUtilisateur permet d'inserer l'utilisateur dans la BDD interne
    //Elle renvoie l'id de l'utilisateur dans la BDD interne
    public long insererUtilisateur(Utilisateur utilisateur){
        //Cr�ation d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs de l'utilisateur en fonction des colonnes
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associ�e � une cl� (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Colonne.NOM, utilisateur.getNom());
        values.put(Colonne.PRENOM, utilisateur.getPrenom());
        values.put(Colonne.DATE_NAISSANTE, utilisateur.getDateNaissance().toString());
        values.put(Colonne.MAIL, utilisateur.getMail());
        if(utilisateur.getPhoto()!=null)
            values.put(Colonne.PHOTO, utilisateur.getPhoto().toString());
        else
            values.put(Colonne.PHOTO, "");
        values.put(Colonne.LATITUDE, utilisateur.getLatitude());
        values.put(Colonne.LONGITUDE, utilisateur.getLongitude());
        values.put(Colonne.ID_FIREBASE, utilisateur.getIdFirebase());

        //on ins�re l'objet dans la BDD via le ContentValues,
        return database_.insert(Table.UTILISATEUR, null, values);

    }

    //A verifier la valeur de retour
    public long modifierUtilisateur(int id, Utilisateur utilisateur){
        //La mise � jour d'un utilisateur dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement pr�ciser quel utilisateur on doit mettre � jour gr�ce � l'ID
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associ�e � une cl� (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Colonne.NOM, utilisateur.getNom());
        values.put(Colonne.PRENOM, utilisateur.getPrenom());
        values.put(Colonne.DATE_NAISSANTE, utilisateur.getDateNaissance().toString());
        values.put(Colonne.MAIL, utilisateur.getMail());
        if(utilisateur.getPhoto()!=null)
            values.put(Colonne.PHOTO, utilisateur.getPhoto().toString());
        else
            values.put(Colonne.PHOTO, "");
        values.put(Colonne.LATITUDE, utilisateur.getLatitude());
        values.put(Colonne.LONGITUDE, utilisateur.getLongitude());
        values.put(Colonne.ID_FIREBASE, utilisateur.getIdFirebase());
        return database_.update(Table.UTILISATEUR, values, Colonne.ID_UTILISATEUR + " = " + id, null);
    }

    public int supprimerUtilisateurParId(int id){
        //Suppression d'un utilisateur de la BDD gr�ce � l'ID
        return database_.delete(Table.UTILISATEUR, Colonne.ID_UTILISATEUR + " = " + id, null);
    }

    public Utilisateur obtenirUtilisateurParNom(String nom){
        //R�cup�re dans un Cursor les valeurs correspondant � un utilisateur contenu dans la BDD (ici on s�lectionne le utilisateur gr�ce � son nom)
        Cursor c = database_.query(Table.UTILISATEUR, null, Colonne.NOM + " LIKE \"" + nom +"\"", null, null, null, null);
        return cursorToUtilisateur(c);
    }

    public Utilisateur obtenirUtilisateurParMail(String mail){
        //R�cup�re dans un Cursor les valeurs correspondant � un utilisateur contenu dans la BDD (ici on s�lectionne le utilisateur gr�ce � son nom)
        Cursor c = database_.query(Table.UTILISATEUR, null, Colonne.MAIL + " LIKE \"" + mail +"\"", null, null, null, null);
        return cursorToUtilisateur(c);
    }
    //Cette m�thode permet de convertir un cursor en un utilisateur
    private Utilisateur cursorToUtilisateur(Cursor c){

        //si aucun �l�ment n'a �t� retourn� dans la requ�te, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier �l�ment
        c.moveToFirst();
        //On cr�� un utilisateur
        Utilisateur utilisateur = new Utilisateur();
        //on lui affecte toutes les infos gr�ce aux infos contenues dans le Cursor
        utilisateur.setIdBDD(c.getInt(NUM_COL_ID));
        utilisateur.setNom(c.getString(NUM_COL_NOM));
        utilisateur.setPrenom(c.getString(NUM_COL_PRENOM));
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
             date = formatDate.parse(c.getString(NUM_COL_DATE_NAISSANCE).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Date: " + formatDate.format(date));
        utilisateur.setDateNaissance(date);
        utilisateur.setMail(c.getString(NUM_COL_MAIL));
        if(c.getString(NUM_COL_PHOTO)!=""||c.getString(NUM_COL_PHOTO)!=null)
            utilisateur.setPhoto(Uri.parse(c.getString(NUM_COL_PHOTO)));
        utilisateur.setLatitude(c.getDouble(NUM_COL_LATITUDE));
        utilisateur.setLatitude(c.getDouble(NUM_COL_LONGITUDE));
        utilisateur.setIdFirebase(c.getString(NUM_COL_ID_FIREBASE));
        //On ferme le cursor
        c.close();

        //On retourne l'utilisateur r�cup�r� de la BDD interne
        return utilisateur;
    }

    //fonction de debuggage pour afficher les utilisateurs pr�sents dans la BDD interne
    public void affichageUtilisateurs() {
       String query = "SELECT * "
               + " FROM "
               + Table.UTILISATEUR ;

       //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

       Log.d("query", query);
       Cursor cursor = database_.rawQuery(query, null);
       //
        //on ins�re l'objet dans la BDD via le ContentValues
       while (cursor.moveToNext()) {
           Log.d(TAG, "L'id de l'utilisateur est : " + cursor.getInt(NUM_COL_ID)
                   + ", son nom est : " + cursor.getString(NUM_COL_NOM)
                   + ", son prenom est : " + cursor.getString(NUM_COL_PRENOM)
                   + ", son mail est : " + cursor.getString(NUM_COL_MAIL)
                   + ", sa date de naissance est : " + cursor.getString(NUM_COL_DATE_NAISSANCE)
                   + ", son photo est : " + cursor.getString(NUM_COL_PHOTO)
                   + ", sa position est : (" + cursor.getDouble(NUM_COL_LATITUDE) + ", " + cursor.getDouble(NUM_COL_LONGITUDE)
                   + ", son id firebase est : " + cursor.getString(NUM_COL_ID_FIREBASE));
       }
       cursor.close();
    }

    //m�thode permettant de r�cup�rer tous les utilisateurs stock�s dans la table utilisateur
    public ArrayList<Utilisateur> obtenirUtilisateurs() {
        ArrayList<Utilisateur> listeUtilisateur = new ArrayList<>();
        String query = "SELECT *"
                + " FROM "
                + Table.UTILISATEUR ;

        Log.d("query", query);
        Cursor c = database_.rawQuery(query, null);
        //
        //on ins�re l'objet dans la BDD via le ContentValues
        while (c.moveToNext()) {

            //On cr�� un utilisateur
            Utilisateur utilisateur = new Utilisateur();
            //on lui affecte toutes les infos gr�ce aux infos contenues dans le Cursor
            utilisateur.setIdBDD(c.getInt(NUM_COL_ID));
            utilisateur.setNom(c.getString(NUM_COL_NOM));
            utilisateur.setPrenom(c.getString(NUM_COL_PRENOM));
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            try {
                date = formatDate.parse(c.getString(NUM_COL_DATE_NAISSANCE).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "Date: " + formatDate.format(date));
            utilisateur.setDateNaissance(date);
            utilisateur.setMail(c.getString(NUM_COL_MAIL));
            if(c.getString(NUM_COL_PHOTO)!=""||c.getString(NUM_COL_PHOTO)!=null)
                utilisateur.setPhoto(Uri.parse(c.getString(NUM_COL_PHOTO)));
            utilisateur.setLatitude(c.getDouble(NUM_COL_LATITUDE));
            utilisateur.setLatitude(c.getDouble(NUM_COL_LONGITUDE));
            utilisateur.setIdFirebase(c.getString(NUM_COL_ID_FIREBASE));

            //on l'ajoute � la liste d'utilisateur
            listeUtilisateur.add(utilisateur);

        }
        c.close();
        return listeUtilisateur;
    }

    //fonction permettant d'ins�rant l'utilisateur connect� dans la table correspondante
    public void connexion(Utilisateur utilisateur) {
        //Cr�ation d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs de l'utilisateur en fonction des colonnes
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associ�e � une cl� (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Colonne.NOM, utilisateur.getNom());
        values.put(Colonne.PRENOM, utilisateur.getPrenom());
        values.put(Colonne.DATE_NAISSANTE, utilisateur.getDateNaissance().toString());
        values.put(Colonne.MAIL, utilisateur.getMail());
        if(utilisateur.getPhoto()!=null)
            values.put(Colonne.PHOTO, utilisateur.getPhoto().toString());
        else
            values.put(Colonne.PHOTO, "");
        values.put(Colonne.LATITUDE, utilisateur.getLatitude());
        values.put(Colonne.LONGITUDE, utilisateur.getLongitude());
        values.put(Colonne.ID_FIREBASE, utilisateur.getIdFirebase());
        //On insert l'utilisateur dans la table pour savoir qu'il y a bien un utilisateur connect�
        //Il est ainsi plus simple d'acc�der aux donn�es
        database_.insert(Table.UTILISATEUR_CONNECTE, null, values);
    }

    public void deconnexion() {
        //On supprime les donn�es apres deconnexion
        //Il n'y a plus d'information dans la table utilisateur connecte
        database_.delete(Table.UTILISATEUR_CONNECTE, null, null);

    }


    public Utilisateur obtenirProfil(){
        //R�cup�re dans un Cursor les valeurs correspondant � un utilisateur contenu dans la BDD (ici on s�lectionne le utilisateur gr�ce � son nom)
        Cursor c = database_.query(Table.UTILISATEUR_CONNECTE, null, null, null, null, null, null);
        //Il est n�cessaire de r�cup�rer que la premi�re ligne
        //Selon notre logique il ne peut y avoir qu'un utilisateur � la fois
        return cursorToUtilisateur(c);
    }


    public void affichageUtilisateurConnecte() {
        String query = "SELECT *" + " FROM "
                + Table.UTILISATEUR_CONNECTE ;

        Log.d("query", query);
        Log.d(TAG, "Affichage de l'utilisateur connect�");
        Cursor cursor = database_.rawQuery(query, null);

        cursor.moveToFirst();
        //On affiche les diff�rents �l�ments
        Log.d(TAG, "L'id de l'utilisateur est : " + cursor.getInt(NUM_COL_ID)
                    + ", son nom est : " + cursor.getString(NUM_COL_NOM)
                    + ", son prenom est : " + cursor.getString(NUM_COL_PRENOM)
                    + ", son mail est : " + cursor.getString(NUM_COL_MAIL)
                    + ", sa date de naissance est : " + cursor.getString(NUM_COL_DATE_NAISSANCE)
                    + ", son photo est : " + cursor.getString(NUM_COL_PHOTO)
                    + ", son position est : (" + cursor.getDouble(NUM_COL_LATITUDE) + ", " + cursor.getDouble(NUM_COL_LONGITUDE)
                    + ", son id firebase est : " + cursor.getString(NUM_COL_ID_FIREBASE));

        cursor.close();

    }
}