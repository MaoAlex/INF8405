package com.example.alexmao.projetfinal.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alexmao.projetfinal.classeApp.Conversation;

/**
 *Classe permettant de gérer la conversation associée à un groupe
 * A COMPLETER
 */
public class ConversationBDD extends AbstractBDD
{
    private static final String TAG = "ConversationBDD";

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_ID_FIREBASE = 1;
    private static final int NUM_COL_NOM_CONVERSATION = 2;

    public ConversationBDD(Context pContext) {
        super(pContext);
    }

    public long insererConversation(Conversation conversation){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs nécessaire au stockage dans la table de la conversation
        ContentValues values = new ContentValues();
        values.put(Colonne.ID_FIREBASE, conversation.getIdFirebase());
        values.put(Colonne.NOM_CONVERSATION, conversation.getNomConversation());

        return database_.insert(Table.CONVERSATION, null, values);
    }

    public int supprimerConversation(int idConversation){
        //cette conversation n'est plus utile, nous la supprimons
        return database_.delete(Table.CONVERSATION, Colonne.ID_CONVERSATION + " = " + idConversation, null);
    }


    //Une unique conversation est associé à un groupe
    public Conversation obtenirConversation(long idGroupe){

        Conversation conversation = new Conversation();

        //requete pour récupérer la conversation associée à un groupe
        String query = "SELECT *"
                + " FROM "
                + Table.CONVERSATION + " WHERE " + Colonne.ID_GROUPE + " = " + idGroupe ;

        Log.d("query", query);

        Cursor c = database_.rawQuery(query, null);
        //On récupère la conversation associée à un groupe
        c.moveToFirst();
        long conversationId = c.getInt(NUM_COL_ID);
        conversation.setIdBDD(conversationId);
        conversation.setIdFirebase(c.getString(NUM_COL_ID_FIREBASE));
        conversation.setNomConversation(c.getString(NUM_COL_NOM_CONVERSATION));
        conversation.setListeMessage(MessageConversationBDD.obtenirMessageConversation(conversationId));

        c.close();
        return conversation;
    }


}
