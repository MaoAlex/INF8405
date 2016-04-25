package com.example.alexmao.projetfinal.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alexmao.projetfinal.classeApp.Conversation;
import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.Groupe;

/**
 * Created by alexMAO on 04/04/2016.
 */
public class GroupeBDD extends AbstractBDD {

    private static final String TAG = "GroupeBDD";

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_ID_CONVERSATION = 1;
    private static final int NUM_COL_ID_FIREBASE = 2;
    private static final int NUM_COL_ID_EVENEMENT = 3;


    public GroupeBDD(Context pContext) {
        super(pContext);
    }

    public long insererGroupe(Groupe groupe){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs nécessaire au stockage dans la table de la groupe
        ContentValues values = new ContentValues();
        values.put(Colonne.ID_CONVERSATION, groupe.getConversation().getIdBDD());
        values.put(Colonne.ID_FIREBASE, groupe.getIdFirebase());
        values.put(Colonne.ID_EVENEMENT, groupe.getEvenement().getIdBDD());

        return database_.insert(Table.GROUPE, null, values);
    }

    public int supprimerGroupe(int idGroupe){
        //cette groupe n'est plus utile, nous la supprimons
        return database_.delete(Table.GROUPE, Colonne.ID_GROUPE + " = " + idGroupe, null);
    }

    //Un unique groupe est associé à un événement
    public Groupe obtenirGroupe(Evenement evenement){

        Groupe groupe = new Groupe();

        //requete pour récupérer la groupe associée à un événement
        String query = "SELECT *"
                + " FROM "
                + Table.GROUPE + " WHERE " + Colonne.ID_EVENEMENT + " = " + evenement.getIdBDD() ;

        Log.d("query", query);

        Cursor c = database_.rawQuery(query, null);
        //On récupère la groupe associée à un événement, il est unique
        c.moveToFirst();
        groupe.setIdBDD(c.getInt(NUM_COL_ID));
        groupe.setEvenement(evenement);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!A completer!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        groupe.setIdFirebase(c.getString(NUM_COL_ID_FIREBASE));
        //groupe.setConversation();
        //groupe.setListeMembre();
        c.close();
        return groupe;
    }

    //On peut aussi récupérer une conversation entre plusieurs personnes qui forment juste un groupe
    public Groupe obtenirGroupe(Conversation conversation){

        Groupe groupe = new Groupe();



        //requete pour récupérer la groupe associée à une conversation
        String query = "SELECT *"
                + " FROM "
                + Table.GROUPE + " WHERE " + Colonne.ID_CONVERSATION + " = " + conversation.getIdBDD() ;

        Log.d("query", query);

        Cursor c = database_.rawQuery(query, null);
        //On récupère la groupe associée à une conversation, ce groupe est unique
        c.moveToFirst();
        groupe.setIdBDD(c.getInt(NUM_COL_ID));
        //groupe.setEvenement(c.getString(NUM_COL_NOM_CONVERSATION));
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!A completer!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        groupe.setIdFirebase(c.getString(NUM_COL_ID_FIREBASE));
        groupe.setConversation(conversation);
        groupe.setListeMembre(UtilisateurBDD.obtenirUtilisateurs(c.getInt(NUM_COL_ID)));
        //groupe.setListeMembre();
        c.close();
        return groupe;
    }


}
