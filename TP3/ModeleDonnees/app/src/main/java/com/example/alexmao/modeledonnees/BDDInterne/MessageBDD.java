package com.example.alexmao.modeledonnees.BDDInterne;

import android.content.ContentValues;
import android.content.Context;

import com.example.alexmao.modeledonnees.classeApp.Message;

/**
 *Classe permettant de manipuler les messages dans la BDD
 */
public class MessageBDD extends AbstractBDD {

    private static final String TAG = "MessageBDD";

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_MESSAGE = 1;
    private static final int NUM_COL_DATE_MESSAGE = 2;
    private static final int NUM_COL_ID_EXPEDITEUR = 3;

    public MessageBDD(Context pContext) {
        super(pContext);
    }

    public long insererMessage(Message message){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les valeurs l'id de l'évenement avec l'id de l'utilisateur interessé
        ContentValues values = new ContentValues();
        values.put(Colonne.MESSAGE, message.getMessage());
        values.put(Colonne.DATE_MESSAGE, message.getDate().toString());
        values.put(Colonne.ID_EXPEDITEUR, message.getExpediteur().getIdBDD());

        return database_.insert(Table.MESSAGE, null, values);
    }

    public int supprimerMessage(int idMessage){
        //l'utilisateur n'aime plus ce message, nous le supprimons donc
        return database_.delete(Table.MESSAGE, Colonne.ID_MESSAGE + " = " + idMessage, null);
    }

}
