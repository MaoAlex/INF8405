package com.example.alexmao.projetfinal.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alexmao.projetfinal.classeApp.InvitationConnexion;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *Classe permettant de gérer les invitations de connexion entre utilisateurs
 */
public class InvitationConnexionBDD extends AbstractBDD {

    public static final String TAG = "InvitationConnexionBDD";

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_ID_EXPEDITEUR = 1;
    private static final int NUM_COL_ID_INVITE = 2;
    private static final int NUM_COL_DATE = 3;
    private static final int NUM_COL_ID_FIREBASE = 4;

    public InvitationConnexionBDD(Context pContext) {
        super(pContext);
    }

    public long insererInvitationConnexion(InvitationConnexion invitationConnexion){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs de l'invitation en fonction des colonnes
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Colonne.ID_INVITE, invitationConnexion.getInvite().getIdBDD());
        values.put(Colonne.DATE_INVITATION, invitationConnexion.getDate());
        values.put(Colonne.ID_FIREBASE, invitationConnexion.getIdFirebase());

        return database_.insert(Table.INVITATION_CONNEXION, null, values);
    }

    public int supprimerInvitationConnexion(InvitationConnexion invitationConnexion){
        //Suppression de l'invitation à l'événement de la BDD
        return database_.delete(Table.INVITATION_CONNEXION, Colonne.ID_INVITATION_CONNEXION + " = " + invitationConnexion.getIdBDD(), null);
    }

    //permet d'obtenir toutes les invitations pour un utilisateur donné
    public ArrayList<InvitationConnexion> obtenirInvitationUtilisateur(Utilisateur utilisateur){
        ArrayList<InvitationConnexion> listeInvitationConnexion = new ArrayList<>();

        //requete pour récupérer les différentes invitations à destination de l'utilisateur
        String query = "SELECT *"
                + " FROM "
                + Table.INVITATION_CONNEXION + " WHERE " + Colonne.ID_INVITE + " = " + utilisateur.getIdBDD() ;

        Log.d("query", query);

        Cursor c = database_.rawQuery(query, null);
        //On récupère toutes les invitations que notre requête a trouvé dans la table
        while (c.moveToNext()){
            InvitationConnexion invitationConnexion = new InvitationConnexion();
            //invitationConnexion.setExpediteur();
            invitationConnexion.setInvite(utilisateur);
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
//            Date date = new Date();
//            try {
//                date = formatDate.parse(c.getString(NUM_COL_DATE).toString());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Log.i(TAG, "Date: " + formatDate.format(date));
            invitationConnexion.setDate(c.getLong(NUM_COL_DATE));
            invitationConnexion.setIdFirebase(c.getString(NUM_COL_ID_FIREBASE));
            invitationConnexion.setIdBDD(c.getInt(NUM_COL_ID));
            invitationConnexion.setExpediteur(UtilisateurBDD.obtenirUtilisateurParId(c.getLong(NUM_COL_ID_EXPEDITEUR)));
            listeInvitationConnexion.add(invitationConnexion);
        }
        c.close();
        return listeInvitationConnexion;

    }

    //fonction de debuggage pour afficher les invitations connexion présents dans la BDD interne
    public void affichageInvitationConnexion() {
        String query = "SELECT * "
                + " FROM "
                + Table.INVITATION_CONNEXION ;

        Log.d("query", query);
        Cursor cursor = database_.rawQuery(query, null);
        //
        //on insère l'objet dans la BDD via le ContentValues
        while (cursor.moveToNext()) {
            Log.d(TAG, "L'id de l''invitation connexion est : " + cursor.getLong(NUM_COL_ID)
                    + ", l'id de l'expediteur est : " + cursor.getLong(NUM_COL_ID_EXPEDITEUR)
                    + ", l'id de l'invite est : " + cursor.getLong(NUM_COL_ID_INVITE)
                    + ", la date est : " + cursor.getLong(NUM_COL_DATE)
                    + ", l'id firebase est : " + cursor.getString(NUM_COL_ID_FIREBASE));
        }
        cursor.close();
    }
}
