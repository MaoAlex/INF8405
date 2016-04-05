package com.example.alexmao.chat.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alexmao.chat.classeApp.ParametresUtilisateur;

/**
 * Created by alexMAO on 03/04/2016.
 */
public class ParametreUtilisateurBDD extends AbstractBDD{
    private static final String TAG = "ParametreUtilisateurBDD" ;

    private static final int NUM_COL_ID_PARAMETRE_UTILISATEUR = 0;
    private static final int NUM_COL_RAYON = 1;
    private static final int NUM_COL_DONNER_LOCALISATION = 2;
    private static final int NUM_COL_MASQUER_NOM = 3;

    private static final int TRUE = 1;
    private static final int FALSE = 0;

    public ParametreUtilisateurBDD(Context pContext) {
        super(pContext);
    }

    //insererParametreUtilisateur permet d'inserer les parametres de l'utilisateur courant dans la BDD interne
    //Elle renvoie l'id de l'utilisateur dans la BDD interne mais il ne doit y en avoir qu'un à la fois
    public long insererParametreUtilisateur(ParametresUtilisateur parametresUtilisateur){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs de l'utilisateur en fonction des colonnes
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Colonne.RAYON, parametresUtilisateur.getRayon());
        if(parametresUtilisateur.isLocalisation())
            values.put(Colonne.DONNER_LOCALISATION, TRUE);
        else
            values.put(Colonne.DONNER_LOCALISATION, FALSE);
        if(parametresUtilisateur.isMasquerNom())
            values.put(Colonne.MASQUER_NOM, TRUE);
        else
            values.put(Colonne.MASQUER_NOM, FALSE);

        //on insère l'objet dans la BDD via le ContentValues,
        return database_.insert(Table.PARAMETRE_UTILISATEUR, null, values);
    }

    //méthode permettant la mise à jour des paramètres utilisateur
    public long modifierParametreUtilisateur(ParametresUtilisateur parametresUtilisateur){
        //La mise à jour des parametres utilisateurs dans la BDD fonctionne plus ou moins comme une insertion
        //On supprime ce qu'il y avait avant
        database_.delete(Table.PARAMETRE_UTILISATEUR, null, null);
      return insererParametreUtilisateur(parametresUtilisateur);
    }

    public ParametresUtilisateur obtenirParametreUtilisateur(){
        //Récupère dans un Cursor les valeurs correspondant à un utilisateur contenu dans la BDD (ici on sélectionne le utilisateur grâce à son nom)
        Cursor c = database_.query(Table.UTILISATEUR_CONNECTE, null, null, null, null, null, null);
        //Il est nécessaire de récupérer que la première ligne
        //Selon notre logique il ne peut y avoir qu'un utilisateur à la fois
        return cursorAParametre(c);
    }

    private ParametresUtilisateur cursorAParametre(Cursor c){

        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un utilisateur
        ParametresUtilisateur parametresUtilisateur = new ParametresUtilisateur();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        parametresUtilisateur.setRayon(c.getInt(NUM_COL_RAYON));
        if(c.getInt(NUM_COL_DONNER_LOCALISATION)==FALSE)
            parametresUtilisateur.setLocalisation(false);
        else
            parametresUtilisateur.setLocalisation(true);
        if(c.getInt(NUM_COL_MASQUER_NOM)==FALSE)
            parametresUtilisateur.setMasquerNom(false);
        else
            parametresUtilisateur.setMasquerNom(true);

        //On ferme le cursor
        c.close();

        //On retourne les parametres utilisateur de la BDD interne
        return parametresUtilisateur;
    }

    public void deconnexion() {
        //On supprime les données apres deconnexion
        //Il n'y a plus d'information dans la table des parametres utilisateur
        database_.delete(Table.PARAMETRE_UTILISATEUR, null, null);

    }

    //fonction de debuggage pour afficher les paramètres utilisateur présents dans la BDD interne
    public void affichageParametreUtilisateur() {
        String query = "SELECT * "
                + " FROM "
                + Table.PARAMETRE_UTILISATEUR ;

        //String query = "SELECT * FROM " + maBaseSQLite_.TABLE_LIEU;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //
        //on insère l'objet dans la BDD via le ContentValues
        while (cursor.moveToNext()) {
            Log.d(TAG, "l'id est : " + cursor.getInt(NUM_COL_ID_PARAMETRE_UTILISATEUR)
                    + ", le rayon est : " + cursor.getInt(NUM_COL_RAYON)
                    + ", l'option localisation est à : " + cursor.getInt(NUM_COL_DONNER_LOCALISATION)
                    + ", l'option masquer nom est à : " + cursor.getInt(NUM_COL_MASQUER_NOM));
        }
        cursor.close();
    }


}
