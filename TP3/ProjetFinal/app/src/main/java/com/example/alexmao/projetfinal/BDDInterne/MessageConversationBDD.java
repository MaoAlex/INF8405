package com.example.alexmao.projetfinal.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.alexmao.projetfinal.classeApp.Conversation;
import com.example.alexmao.projetfinal.classeApp.Message;

import java.util.ArrayList;

/**
 * Created by alexMAO on 04/04/2016.
 */
public class MessageConversationBDD extends AbstractBDD {

    private static final String TAG = "MessageConversationBDD";

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_ID_CONVERSATION = 1;

    public MessageConversationBDD(Context pContext) {
        super(pContext);
    }

    public long insererMessageConversation(Message message, Conversation conversation){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs l'id du message avec l'id de la conversation
        ContentValues values = new ContentValues();
        values.put(Colonne.ID_MESSAGE, message.getIdBDD());
        values.put(Colonne.ID_CONVERSATION, conversation.getIdBDD());

        return database_.insert(Table.MESSAGE_CONVERSATION, null, values);
    }

    //méthode permettant la suppresion d'un événement de la table message selon l'id
    public int supprimerMessageConversation(int idMessage, int idConversation){
        //Suppression d'un message d'une conversation de la BDD
        return database_.delete(Table.MESSAGE_CONVERSATION, Colonne.ID_MESSAGE + " = " + idMessage
                + " AND " + Colonne.ID_CONVERSATION + " = " + idConversation, null);
    }


    public static ArrayList<Message> obtenirMessageConversation(long idConversation){
        ArrayList<Message> listeMessage = new ArrayList<>();

        //On crée la requete nous permettant de faire la jointure des tables
        //On récupère ici les message de la un conversation
        String query = "SELECT * " + " FROM "
                + Table.MESSAGE_CONVERSATION + " WHERE "
                + Colonne.ID_CONVERSATION + " = " + idConversation;

        Cursor c = database_.rawQuery(query, null);
        while (c.moveToNext()) {

           Message message = MessageBDD.obtenirMessage(c.getInt(NUM_COL_ID));
           listeMessage.add(message);
        }
        c.close();

        return listeMessage;
    }

    public static ArrayList<Message> obtenirMessageConversation(String  idConversation){

        ArrayList<Message> listeMessage = new ArrayList<>();
        String query = "SELECT * " + " FROM "
                + Table.MESSAGE_CONVERSATION + " WHERE "
                + Colonne.ID_CONVERSATION + " = ?" ;

        Cursor c = database_.rawQuery(query, new String[]{idConversation});
        while (c.moveToNext()) {
            Message message = MessageBDD.obtenirMessage(c.getInt(NUM_COL_ID));
            listeMessage.add(message);
        }
        c.close();

        return listeMessage;
    }

}
