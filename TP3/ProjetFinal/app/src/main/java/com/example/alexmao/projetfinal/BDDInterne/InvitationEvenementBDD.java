package com.example.alexmao.projetfinal.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alexmao.projetfinal.classeApp.InvitationEvenement;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by alexMAO on 04/04/2016.
 */
public class InvitationEvenementBDD extends AbstractBDD {

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_ID_EVENEMENT = 1;
    private static final int NUM_COL_ID_EXPEDITEUR = 2;
    private static final int NUM_COL_ID_INVITE = 3;
    private static final int NUM_COL_DATE = 4;
    private static final int NUM_COL_ID_FIREBASE = 5;
    private static final String TAG = "InvitationEvenementBDD";

    public InvitationEvenementBDD(Context pContext) {
        super(pContext);
    }

    public long insererInvitationEvenement(InvitationEvenement invitationEvenement){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs de l'invitation en fonction des colonnes
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(Colonne.ID_EXPEDITEUR, invitationEvenement.getExpediteur().getIdFirebase());
        values.put(Colonne.ID_INVITE, invitationEvenement.getInvite().getIdBDD());
        values.put(Colonne.DATE_INVITATION, invitationEvenement.getDate());
        values.put(Colonne.ID_FIREBASE, invitationEvenement.getIdFirebase());

        return database_.insert(Table.INVITATION_EVENEMENT, null, values);
    }

    public int supprimerInvitationEvenement(InvitationEvenement invitationEvenement){
        //Suppression de l'invitation à l'événement de la BDD
        return database_.delete(Table.INVITATION_EVENEMENT, Colonne.ID_FIREBASE + " = ? ", new String[]{invitationEvenement.getIdFirebase()});
    }

    //permet d'obtenir toutes les invitations pour un utilisateur donné
    public ArrayList<InvitationEvenement> obtenirInvitationUtilisateur(Utilisateur utilisateur){
        ArrayList<InvitationEvenement> listeInvitationEvenement = new ArrayList<>();

        //requete pour récupérer les différentes invitations à destination de l'utilisateur
        String query = "SELECT *"
                + " FROM "
                + Table.INVITATION_EVENEMENT + " WHERE " + Colonne.ID_INVITE + " = " + utilisateur.getIdBDD() ;

        Log.d("query", query);

        Cursor c = database_.rawQuery(query, null);
        //On récupère toutes les invitations que notre requête a trouvé dans la table
        while (c.moveToNext()){
            InvitationEvenement invitationEvenement = new InvitationEvenement();
            //invitationEvenement.setExpediteur();
            invitationEvenement.setInvite(utilisateur);
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
//            Date date = new Date();
//            try {
//                date = formatDate.parse(c.getString(NUM_COL_DATE).toString());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Log.i(TAG, "Date: " + formatDate.format(date));
            invitationEvenement.setDate(c.getLong(NUM_COL_DATE));
            invitationEvenement.setEvenement(EvenementBDD.obtenirEvenement(c.getLong(NUM_COL_ID_EVENEMENT)));
            invitationEvenement.setIdFirebase(c.getString(NUM_COL_ID_FIREBASE));
            invitationEvenement.setIdBDD(c.getInt(NUM_COL_ID));
            invitationEvenement.setExpediteur(UtilisateurBDD.obtenirUtilisateurParIdFirebase(c.getString(NUM_COL_ID_EXPEDITEUR)));

            listeInvitationEvenement.add(invitationEvenement);
        }
        c.close();
        return listeInvitationEvenement;

    }

}
