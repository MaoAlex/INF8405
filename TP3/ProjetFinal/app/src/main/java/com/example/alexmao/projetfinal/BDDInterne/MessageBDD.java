package com.example.alexmao.projetfinal.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alexmao.projetfinal.classeApp.Message;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

/**
 *Classe permettant de manipuler les messages dans la BDD
 */
public class MessageBDD extends AbstractBDD {

    private static final String TAG = "MessageEBDD";

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_MESSAGE = 1;
    private static final int NUM_COL_DATE_MESSAGE = 2;
    private static final int NUM_COL_ID_EXPEDITEUR = 3;

    public MessageBDD(Context pContext) {
        super(pContext);
    }

    public static long insererMessage(Message message){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs l'id de l'évenement avec l'id de l'utilisateur interessé
        ContentValues values = new ContentValues();
        values.put(Colonne.MESSAGE, message.getMessage());
        values.put(Colonne.DATE_MESSAGE, message.getDate());
        values.put(Colonne.ID_EXPEDITEUR, message.getExpediteur().getIdBDD());

        return database_.insert(Table.MESSAGE, null, values);
    }

    public static int supprimerMessage(int idMessage){
        //l'utilisateur n'aime plus ce message, nous le supprimons donc
        return database_.delete(Table.MESSAGE, Colonne.ID_MESSAGE + " = " + idMessage, null);
    }

    //Une unique conversation est associé à un groupe
    public static Message obtenirMessage(long idMessage){

        //requete pour récupérer la conversation associée à un groupe
        String query = "SELECT *"
                + " FROM "
                + Table.MESSAGE + " WHERE " + Colonne.ID_MESSAGE + " = " + idMessage ;

        Log.d("query", query);

        Cursor c = database_.rawQuery(query, null);
        if(c.getCount()==0)
            return null;
        //On récupère la conversation associée à un groupe
        c.moveToFirst();
        Message message = new Message();
        message.setIdBDD(c.getLong(NUM_COL_ID));
        message.setDate(c.getLong(NUM_COL_DATE_MESSAGE));
        message.setMessage(c.getString(NUM_COL_MESSAGE));

        Utilisateur expediteur = UtilisateurBDD.obtenirUtilisateurParId(c.getLong(NUM_COL_ID_EXPEDITEUR));
        message.setExpediteur(expediteur);
        c.close();
        return message;
    }
}
