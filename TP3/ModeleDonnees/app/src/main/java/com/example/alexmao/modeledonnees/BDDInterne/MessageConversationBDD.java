package com.example.alexmao.modeledonnees.BDDInterne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alexmao.modeledonnees.classeApp.Conversation;
import com.example.alexmao.modeledonnees.classeApp.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alexMAO on 04/04/2016.
 */
public class MessageConversationBDD extends AbstractBDD {

    private static final String TAG = "MessageConversationBDD";

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_MESSAGE = 1;
    private static final int NUM_COL_DATE_MESSAGE = 2;
    private static final int NUM_COL_ID_EXPEDITEUR = 3;

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


    public ArrayList<Message> obtenirMessageConversation(Conversation conversation){
        ArrayList<Message> listeMessage = new ArrayList<>();

        //On crée la requete nous permettant de faire la jointure des tables
        //On récupère ici les message de la un conversation
        String query = "SELECT * " + " FROM "
                + Table.MESSAGE + " m INNER JOIN "
                + Table.MESSAGE_CONVERSATION + " mc ON mc."
                + Colonne.ID_MESSAGE + " = m."
                + Colonne.ID_MESSAGE + " WHERE mc." + Colonne.ID_CONVERSATION + " = " + conversation.getIdBDD();

        Cursor c = database_.rawQuery(query, null);
        while (c.moveToNext()) {

            //On créé un événement
            Message message = new Message();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            message.setIdBDD(c.getInt(NUM_COL_ID));
            message.setMessage(c.getString(NUM_COL_MESSAGE));
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            try {
                date = formatDate.parse(c.getString(NUM_COL_DATE_MESSAGE).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "Date: " + formatDate.format(date));
            message.setDate(date);
            //message.setExpediteur();
            //
            //!!!!!!!!!!!!!!!!!!!A COMPLETER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //Expediteur non créer
            //on l'ajoute à la liste d'message
            listeMessage.add(message);

        }
        c.close();

        return listeMessage;
    }


}
