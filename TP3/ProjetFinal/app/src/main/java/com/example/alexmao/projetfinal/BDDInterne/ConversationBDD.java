package com.example.alexmao.projetfinal.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alexmao.projetfinal.classeApp.Conversation;
import com.example.alexmao.projetfinal.classeApp.Groupe;

import java.util.ArrayList;

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
    public static Conversation obtenirConversation(Groupe groupe){

        Conversation conversation = new Conversation();

        //requete pour récupérer la conversation associée à un groupe
        String query = "SELECT *"
                + " FROM "
                + Table.CONVERSATION + " WHERE " + Colonne.ID_GROUPE + " = " + groupe.getIdBDD() ;

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

    public Conversation obtenirConversationParId(long idConversation){

        Conversation conversation = new Conversation();

        //requete pour récupérer la conversation associée à un groupe
        String query = "SELECT *"
                + " FROM "
                + Table.CONVERSATION + " WHERE " + Colonne.ID_CONVERSATION + " = " + idConversation ;

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

    //Une unique conversation est associé à un groupe
    public ArrayList<Conversation> obtenirListeConversation(){

        ArrayList<Conversation> conversationListe = new ArrayList<>();

        //requete pour récupérer la conversation associée à un groupe
        String query = "SELECT *"
                + " FROM "
                + Table.CONVERSATION ;

        Log.d("query", query);

        Cursor c = database_.rawQuery(query, null);
        //On récupère la conversation associée à un groupe
        while (c.moveToNext()) {
            Log.d("ConvesationBDD", "On recupere  une conversation");
            Conversation conversation = new Conversation();
            long conversationId = c.getInt(NUM_COL_ID);
            conversation.setIdBDD(conversationId);
            conversation.setIdFirebase(c.getString(NUM_COL_ID_FIREBASE));
            conversation.setNomConversation(c.getString(NUM_COL_NOM_CONVERSATION));
            conversation.setListeMessage(MessageConversationBDD.obtenirMessageConversation(conversationId));
            conversationListe.add(conversation);
        }
        c.close();
        return conversationListe;
    }

    //On peut aussi récupérer une conversation entre plusieurs personnes qui forment juste un groupe
    public static void affichageConversation(){

        Groupe groupe = new Groupe();

        //requete pour récupérer la groupe associée à une conversation
        String query = "SELECT *"
                + " FROM "
                + Table.CONVERSATION;

        Log.d("query", query);

        Cursor cursor = database_.rawQuery(query, null);
        //On récupère la groupe associée à une conversation, ce groupe est unique
        while (cursor.moveToNext()) {
            Log.d(TAG, "L'id de la conversation est : " + cursor.getInt(NUM_COL_ID)
                    + ", son id firebase est : " + cursor.getString(NUM_COL_ID_FIREBASE)
//                    + ", son evenemnt est : " + cursor.getInt(NUM_COL_ID_EVENEMENT)
                    + ", son nom est : " + cursor.getString(NUM_COL_NOM_CONVERSATION));
        }
        cursor.close();
    }


}
