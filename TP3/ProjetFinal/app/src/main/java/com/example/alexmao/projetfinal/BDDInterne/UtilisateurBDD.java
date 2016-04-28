package com.example.alexmao.projetfinal.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;

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
    private static final int NUM_COL_ID_UTILISATEUR= 0;
    private static final int NUM_COL_ID_CONNEXION= 1;
    public UtilisateurBDD(Context pContext) {
        super(pContext);
    }

    //insererUtilisateur permet d'inserer l'utilisateur dans la BDD interne
    //Elle renvoie l'id de l'utilisateur dans la BDD interne
    public static long insererUtilisateur(Utilisateur utilisateur){
        //Cr�ation d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs de l'utilisateur en fonction des colonnes
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée � une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Colonne.NOM, utilisateur.getNom());
        values.put(Colonne.PRENOM, utilisateur.getPrenom());
        values.put(Colonne.DATE_NAISSANTE, utilisateur.getDateNaissance());
        values.put(Colonne.MAIL, utilisateur.getMail());
        if(utilisateur.getPhoto()!=null)
            values.put(Colonne.PHOTO, utilisateur.getPhoto().toString());
        else
            values.put(Colonne.PHOTO, "");
        values.put(Colonne.LATITUDE, utilisateur.getLatitude());
        values.put(Colonne.LONGITUDE, utilisateur.getLongitude());

        Log.d(TAG, "Insertion Table Utilisateur de l'utilisateur connecté, sont id Firebase est : " + utilisateur.getIdFirebase());

        values.put(Colonne.ID_FIREBASE, utilisateur.getIdFirebase());
        Log.d("UtilisateurBDD", "insertion en cours");
        //on insère l'objet dans la BDD via le ContentValues,
        long id;
            Log.d(TAG, "l'utilisateur n'est pas présent");
            id = database_.insert(Table.UTILISATEUR, null, values);
            if (utilisateur.getListeConnexion() != null) {
                for (String idFirebase : utilisateur.getListeConnexion()) {
                    insererConnexion(id, idFirebase);
                }
            }
        utilisateur.setIdBDD(id);
        SportUtilisateurBDD.insererListeSport(utilisateur);
        return id;

    }

    //A verifier la valeur de retour
    public static long modifierUtilisateur(int id, Utilisateur utilisateur){
        //La mise à jour d'un utilisateur dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel utilisateur on doit mettre à jour grace à l'ID
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Colonne.NOM, utilisateur.getNom());
        values.put(Colonne.PRENOM, utilisateur.getPrenom());
        values.put(Colonne.DATE_NAISSANTE, utilisateur.getDateNaissance());
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
    //A verifier la valeur de retour
    public static long modifierUtilisateur(String idFirebase, Utilisateur utilisateur){
        //La mise à jour d'un utilisateur dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel utilisateur on doit mettre à jour grace à l'ID
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Colonne.NOM, utilisateur.getNom());
        values.put(Colonne.PRENOM, utilisateur.getPrenom());
        values.put(Colonne.DATE_NAISSANTE, utilisateur.getDateNaissance());
        values.put(Colonne.MAIL, utilisateur.getMail());
        if(utilisateur.getPhoto()!=null)
            values.put(Colonne.PHOTO, utilisateur.getPhoto().toString());
        else
            values.put(Colonne.PHOTO, "");
        values.put(Colonne.LATITUDE, utilisateur.getLatitude());
        values.put(Colonne.LONGITUDE, utilisateur.getLongitude());
        values.put(Colonne.ID_FIREBASE, utilisateur.getIdFirebase());
        if(utilisateur.getIdBDD()!=0) {
            SportUtilisateurBDD.supprimerSportUtilisateur(utilisateur.getIdBDD());
            SportUtilisateurBDD.insererListeSport(utilisateur);
        }
        return database_.update(Table.UTILISATEUR, values, Colonne.ID_FIREBASE + " = ?", new String[]{idFirebase});
    }

    public static int supprimerUtilisateurParId(int id){
        //Suppression d'un utilisateur de la BDD grace a l'ID
        return database_.delete(Table.UTILISATEUR, Colonne.ID_UTILISATEUR + " = " + id, null);
    }

    public static Utilisateur obtenirUtilisateurParNom(String nom){
        //Racupare dans un Cursor les valeurs correspondant a un utilisateur contenu dans la BDD (ici on sélectionne le utilisateur grace a son nom)
        Cursor c = database_.query(Table.UTILISATEUR, null, Colonne.NOM + " LIKE \"" + nom +"\"", null, null, null, null);
        return cursorToUtilisateur(c);
    }

    public static Utilisateur obtenirUtilisateurParMail(String mail){
        //Récupére dans un Cursor les valeurs correspondant à un utilisateur contenu dans la BDD (ici on sélectionne le utilisateur grace a son nom)
        Cursor c = database_.query(Table.UTILISATEUR, null, Colonne.MAIL + " LIKE \"" + mail +"\"", null, null, null, null);
        return cursorToUtilisateur(c);
    }

    public static Utilisateur obtenirUtilisateurParIdFirebase(String idFirebase){
        //Récupére dans un Cursor les valeurs correspondant à un utilisateur contenu dans la BDD (ici on sélectionne le utilisateur grace a son nom)
        Cursor c = database_.query(Table.UTILISATEUR, null, Colonne.ID_FIREBASE + " LIKE \"" + idFirebase +"\"", null, null, null, null);
        return cursorToUtilisateur(c);
    }

    public static Utilisateur obtenirUtilisateurParId(long id){
        //Récupére dans un Cursor les valeurs correspondant à un utilisateur contenu dans la BDD (ici on sélectionne le utilisateur grace a son nom)
        Cursor c = database_.query(Table.UTILISATEUR, null, Colonne.ID_UTILISATEUR + " LIKE \"" + id +"\"", null, null, null, null);
        return cursorToUtilisateur(c);
    }

    //Cette méthode permet de convertir un cursor en un utilisateur
    public static Utilisateur cursorToUtilisateur(Cursor c){

        //si aucun élément n'a été retourné dans la requéte, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un utilisateur
        Utilisateur utilisateur = new Utilisateur();
        //on lui affecte toutes les infos grace aux infos contenues dans le Cursor
        long id = c.getLong(NUM_COL_ID);
        utilisateur.setIdBDD(id);
        utilisateur.setNom(c.getString(NUM_COL_NOM));
        utilisateur.setPrenom(c.getString(NUM_COL_PRENOM));
//        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        /*Date date = new Date();
        try {
             date = formatDate.parse(c.getString(NUM_COL_DATE_NAISSANCE).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Date: " + formatDate.format(date));*/
        utilisateur.setDateNaissance(c.getLong(NUM_COL_DATE_NAISSANCE));
        utilisateur.setMail(c.getString(NUM_COL_MAIL));
        if(c.getString(NUM_COL_PHOTO)!=""||c.getString(NUM_COL_PHOTO)!=null)
            utilisateur.setPhoto(c.getString(NUM_COL_PHOTO));
        utilisateur.setLatitude(c.getDouble(NUM_COL_LATITUDE));
        utilisateur.setLatitude(c.getDouble(NUM_COL_LONGITUDE));
        utilisateur.setIdFirebase(c.getString(NUM_COL_ID_FIREBASE));
        //On ferme le cursor

        String query = "SELECT * "
                + " FROM "
                + Table.UTILISATEUR_CONNEXIONS + " WHERE " + Colonne.ID_UTILISATEUR + " = " + id;
        ;
        Cursor cursor = database_.rawQuery(query, null);
        ArrayList<String> connexions = new ArrayList<>();
        while (cursor.moveToNext()) {
            connexions.add(cursor.getString(NUM_COL_ID_CONNEXION));
        }
        utilisateur.setListeConnexion(connexions);

        query = "SELECT * "
                + " FROM "
                + Table.PARTICIPANT_EVENEMENT + " WHERE " + Colonne.ID_UTILISATEUR + " = " + id;
        ;
        cursor = database_.rawQuery(query, null);
        ArrayList<String> evenementId = new ArrayList<>();
        while (cursor.moveToNext()) {
            evenementId.add(cursor.getString(1));
        }

        ArrayList<String> sports = SportUtilisateurBDD.obtenirSportUtilisateur(id);
        utilisateur.setSports(sports);
        utilisateur.setListeParticipationsID(evenementId);
        cursor.close();

        //On retourne l'utilisateur récupéré de la BDD interne
        return utilisateur;
    }

    //fonction de debuggage pour afficher les utilisateurs présents dans la BDD interne
    public void affichageUtilisateurs() {
       String query = "SELECT * "
               + " FROM "
               + Table.UTILISATEUR ;

       //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

       Log.d("query", query);
       Cursor cursor = database_.rawQuery(query, null);
       Log.d(TAG, "Affichage des differents utilisateurs");

        //on insére l'objet dans la BDD via le ContentValues
       while (cursor.moveToNext()) {
           Log.d(TAG, "L'id de l'utilisateur est : " + cursor.getInt(NUM_COL_ID)
                   + ", son nom est : " + cursor.getString(NUM_COL_NOM)
                   + ", son prenom est : " + cursor.getString(NUM_COL_PRENOM)
                   + ", son mail est : " + cursor.getString(NUM_COL_MAIL)
                   + ", sa date de naissance est : " + cursor.getLong(NUM_COL_DATE_NAISSANCE)
                   + ", son photo est : " + cursor.getString(NUM_COL_PHOTO)
                   + ", sa position est : (" + cursor.getDouble(NUM_COL_LATITUDE) + ", " + cursor.getDouble(NUM_COL_LONGITUDE)
                   + ", son id firebase est : " + cursor.getString(NUM_COL_ID_FIREBASE));
       }
       cursor.close();
    }

    //insererUtilisateur permet d'inserer l'utilisateur dans la BDD interne
    //Elle renvoie l'id de l'utilisateur dans la BDD interne
    public long insererUtilisateurConnecte(Utilisateur utilisateur){
        //Cr�ation d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs de l'utilisateur en fonction des colonnes
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée � une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Colonne.NOM, utilisateur.getNom());
        values.put(Colonne.PRENOM, utilisateur.getPrenom());
        values.put(Colonne.DATE_NAISSANTE, utilisateur.getDateNaissance());
        values.put(Colonne.MAIL, utilisateur.getMail());
        if(utilisateur.getPhoto()!=null)
            values.put(Colonne.PHOTO, utilisateur.getPhoto().toString());
        else
            values.put(Colonne.PHOTO, "");
        values.put(Colonne.LATITUDE, utilisateur.getLatitude());
        values.put(Colonne.LONGITUDE, utilisateur.getLongitude());
        values.put(Colonne.ID_FIREBASE, utilisateur.getIdFirebase());
        Log.d("UtilisateurBDD", "insertion en cours");
        //on insère l'objet dans la BDD via le ContentValues,
        database_.insert(Table.UTILISATEUR_CONNECTE, null, values);
        return database_.insert(Table.UTILISATEUR, null, values);

    }

    //méthode permettant de récupérer tous les utilisateurs stockés dans la table utilisateur
    public ArrayList<Utilisateur> obtenirUtilisateurs() {
        ArrayList<Utilisateur> listeUtilisateur = new ArrayList<>();
        String query = "SELECT *"
                + " FROM "
                + Table.UTILISATEUR ;

        Log.d("query", query);
        Cursor c = database_.rawQuery(query, null);
        //
        //on insére l'objet dans la BDD via le ContentValues
        while (c.moveToNext()) {

            //On créé un utilisateur
            Utilisateur utilisateur = new Utilisateur();
            //on lui affecte toutes les infos grace aux infos contenues dans le Cursor
            utilisateur.setIdBDD(c.getInt(NUM_COL_ID));
            utilisateur.setNom(c.getString(NUM_COL_NOM));
            utilisateur.setPrenom(c.getString(NUM_COL_PRENOM));
            /*SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            try {
                date = formatDate.parse(c.getString(NUM_COL_DATE_NAISSANCE).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "Date: " + formatDate.format(date));*/
            utilisateur.setDateNaissance(c.getLong(NUM_COL_DATE_NAISSANCE));
            utilisateur.setMail(c.getString(NUM_COL_MAIL));
            if(c.getString(NUM_COL_PHOTO)!=""||c.getString(NUM_COL_PHOTO)!=null)
                utilisateur.setPhoto(c.getString(NUM_COL_PHOTO));
            utilisateur.setLatitude(c.getDouble(NUM_COL_LATITUDE));
            utilisateur.setLatitude(c.getDouble(NUM_COL_LONGITUDE));
            utilisateur.setIdFirebase(c.getString(NUM_COL_ID_FIREBASE));

            //on l'ajoute a la liste d'utilisateur
            listeUtilisateur.add(utilisateur);

        }
        c.close();
        return listeUtilisateur;
    }

    //méthode permettant de récupérer tous les utilisateurs stockés dans la table utilisateur
    public static ArrayList<Utilisateur> obtenirUtilisateurs(long groupeID) {
        ArrayList<Utilisateur> listeUtilisateur = new ArrayList<>();
        ArrayList<Integer> listId = new ArrayList<>();
        String query = "SELECT *"
                + " FROM "
                + Table.GROUPE_UTILISATEUR + " WHERE " + Colonne.ID_GROUPE + " = " + groupeID;

        Cursor c = database_.rawQuery(query, null);

        while (c.moveToNext()) {
            //on l'ajoute l'id à la liste
            listId.add(c.getInt(NUM_COL_ID_UTILISATEUR));
        }
        for (int i = 0; i< listId.size(); i++){
            query = "SELECT *"
                    + " FROM "
                    + Table.UTILISATEUR + " WHERE " + Colonne.ID_UTILISATEUR + " = " + listId.get(i);
            Log.d("query", query);
            c = database_.rawQuery(query, null);
            Utilisateur utilisateurTemp = cursorToUtilisateur(c);
            listeUtilisateur.add(utilisateurTemp);
        }

        c.close();
        return listeUtilisateur;
    }

    //fonction permettant d'insérant l'utilisateur connecté dans la table correspondante
    public void connexion(Utilisateur utilisateur) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs de l'utilisateur en fonction des colonnes
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée a une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Colonne.ID_UTILISATEUR_CONNECTE, utilisateur.getIdBDD());
        values.put(Colonne.NOM, utilisateur.getNom());
        values.put(Colonne.PRENOM, utilisateur.getPrenom());
        values.put(Colonne.DATE_NAISSANTE, utilisateur.getDateNaissance());
        values.put(Colonne.MAIL, utilisateur.getMail());
        if(utilisateur.getPhoto()!=null)
            values.put(Colonne.PHOTO, utilisateur.getPhoto().toString());
        else
            values.put(Colonne.PHOTO, "");
        values.put(Colonne.LATITUDE, utilisateur.getLatitude());
        values.put(Colonne.LONGITUDE, utilisateur.getLongitude());
        Log.d(TAG, "Insertion Table Utilisateur connecte de l'utilisateur connecté, sont id Firebase est : " + utilisateur.getIdFirebase());
        values.put(Colonne.ID_FIREBASE, utilisateur.getIdFirebase());

        //On insert l'utilisateur dans la table pour savoir qu'il y a bien un utilisateur connecté
        //Il est ainsi plus simple d'accéder aux données
        database_.insert(Table.UTILISATEUR_CONNECTE, null, values);
    }

    public void deconnexion() {
        //On supprime les données apres deconnexion
        //Il n'y a plus d'information dans la table utilisateur connecte
//        maBaseSQLite_.onUpgrade(database_, VERSION, VERSION);
        database_.delete(Table.UTILISATEUR_CONNECTE, null, null);
        //TODO A supprimer toutes les tables
        database_.delete(Table.UTILISATEUR_CONNEXIONS, null, null);

        database_.delete(Table.PARAMETRE_UTILISATEUR, null, null);
        database_.delete(Table.EVENEMENT_INTERESSE, null, null);
        database_.delete(Table.PARTICIPANT_EVENEMENT, null, null);
        database_.delete(Table.INVITATION_EVENEMENT, null, null);
        database_.delete(Table.SPORT_UTILISATEUR, null, null);
        database_.delete(Table.INVITATION_CONNEXION, null, null);
        database_.delete(Table.MESSAGE, null, null);
        database_.delete(Table.MESSAGE_CONVERSATION, null, null);
        database_.delete(Table.CONVERSATION, null, null);
        database_.delete(Table.GROUPE, null, null);
        database_.delete(Table.GROUPE_UTILISATEUR, null, null);
        database_.delete(Table.EVENEMENT, null, null);
        database_.delete(Table.UTILISATEUR, null, null);

    }


    public Utilisateur obtenirProfil(){
        //Récupére dans un Cursor les valeurs correspondant é un utilisateur contenu dans la BDD (ici on sélectionne le utilisateur gréce é son nom)
        Cursor c = database_.query(Table.UTILISATEUR_CONNECTE, null, null, null, null, null, null);
        //Il est nécessaire de récupérer que la premiére ligne
        //Selon notre logique il ne peut y avoir qu'un utilisateur a la fois
        return cursorToUtilisateur(c);
    }


    public void affichageUtilisateurConnecte() {
        String query = "SELECT *" + " FROM "
                + Table.UTILISATEUR_CONNECTE ;

        Log.d("query", query);
        Log.d(TAG, "Affichage de l'utilisateur connecté");
        Cursor cursor = database_.rawQuery(query, null);
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
                //On affiche les différents éléments
                Log.d(TAG, "L'id de l'utilisateur est : " + cursor.getInt(NUM_COL_ID)
                        + ", son nom est : " + cursor.getString(NUM_COL_NOM)
                        + ", son prenom est : " + cursor.getString(NUM_COL_PRENOM)
                        + ", son mail est : " + cursor.getString(NUM_COL_MAIL)
                        + ", sa date de naissance est : " + cursor.getLong(NUM_COL_DATE_NAISSANCE)
                        + ", son photo est : " + cursor.getString(NUM_COL_PHOTO)
                        + ", son position est : (" + cursor.getDouble(NUM_COL_LATITUDE) + ", " + cursor.getDouble(NUM_COL_LONGITUDE)
                        + ", son id firebase est : " + cursor.getString(NUM_COL_ID_FIREBASE));

        }

        cursor.close();

    }

    public boolean estConnecte() {
        String query = "SELECT *" + " FROM "
                + Table.UTILISATEUR_CONNECTE ;

        Log.d("query", query);
        Log.d(TAG, "Affichage de l'utilisateur connecté");
        Cursor cursor = database_.rawQuery(query, null);

        return (cursor.getCount()>0);

    }

    public static boolean estPresentUtilisateur(String idFirebase) {
        String query = "SELECT *" + " FROM "
                + Table.UTILISATEUR + " WHERE " + Colonne.ID_FIREBASE + " = ?"  ;

        Log.d("query", query);
        Log.d(TAG, "Presence de l'utilisateur dans la BD Interne");
        Cursor cursor = database_.rawQuery(query, new String[]{idFirebase});
        cursor.moveToFirst();
        return (cursor.getCount()>0);

    }

    public static long insererConnexion(long idUtilisateur, String idConnexion){
        //Cr�ation d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs de l'utilisateur en fonction des colonnes
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée � une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Colonne.ID_UTILISATEUR, idUtilisateur);
        values.put(Colonne.ID_CONNEXION, idConnexion);

        Log.d(TAG, "Insertion Table Utilisateur Connexion de la connexion de lutilisateur, utilisateur id : " + idUtilisateur);
        Log.d("UtilisateurBDD", "insertion connexion en cours");
        //on insère l'objet dans la BDD via le ContentValues,
        return database_.insert(Table.UTILISATEUR_CONNEXIONS, null, values);

    }

}