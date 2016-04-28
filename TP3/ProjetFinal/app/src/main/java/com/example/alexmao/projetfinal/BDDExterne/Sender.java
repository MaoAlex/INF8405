package com.example.alexmao.projetfinal.BDDExterne;

import android.util.Log;

import com.example.alexmao.projetfinal.classeApp.Conversation;
import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.Groupe;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

/**
 * Created by filou on 27/04/16.
 */
public class Sender {
    public static void addGroupDiscussionEvent(Groupe groupe,
                                                Evenement evenement,
                                                Conversation conversation,
                                                RemoteBD remoteBD) {
        MyLocalGroupEBDD groupEBDD = FromClassAppToEBDD.translateGroupe(groupe);
        MyLocalEventEBDD eventEBDD = FromClassAppToEBDD.translateEvenement(evenement, null);
        ConversationEBDD conversationEBDD = FromClassAppToEBDD.translateConversation(conversation);
        String idEvent = remoteBD.addEvent(eventEBDD);
        eventEBDD.setDataBaseId(idEvent);
        Log.d("Sender", "l'id event : " + idEvent);
        Log.d("Sender", "l'id event dans eventEBDD : " + eventEBDD.getDataBaseId());
        String idConversation = remoteBD.addDiscussion(conversationEBDD);
        conversationEBDD.setDataBaseId(idConversation);
        Log.d("Sender", "l'id conversation : " + idConversation);
        Log.d("Sender", "l'id conversation dans  : " + conversationEBDD.getDataBaseId());
        groupEBDD.setEventID(eventEBDD.getDataBaseId());
        groupEBDD.setConversationID(conversationEBDD.getDataBaseId());
        groupEBDD.setDatabaseID(remoteBD.addGroup(groupEBDD));
        eventEBDD.setGroupID(groupEBDD.getDatabaseID());
        conversationEBDD.setGroupID(groupEBDD.getDatabaseID());
        if(eventEBDD!=null)
            Log.d("Sender", "l'id event : " + eventEBDD.getDataBaseId());
        remoteBD.updateDiscussion(conversationEBDD.getDataBaseId(), conversationEBDD);
        remoteBD.updateEvent(eventEBDD.getDataBaseId(), eventEBDD);
        Log.d("Sender", "l'id groupe : " + groupEBDD.getDatabaseID());
        groupe.setIdFirebase(groupEBDD.getDatabaseID());
        groupe.setConversation(conversationEBDD.getDataBaseId());
        Log.d("Sender", "l'id groupe : " + groupe.getIdFirebase());
        evenement.setIdFirebase(eventEBDD.getDataBaseId());
        Log.d("Sender", "l'id event : " + eventEBDD.getDataBaseId());
        conversation.setGroupID(groupEBDD.getDatabaseID());
        conversation.setIdFirebase(conversationEBDD.getDataBaseId());

        remoteBD.addEventToGroup(eventEBDD.getId(), groupEBDD.getDatabaseID());
    }

    public static void addGroupeDiscussion(Groupe groupe, Conversation conversation,
                                           RemoteBD remoteBD) {
        MyLocalGroupEBDD groupEBDD = FromClassAppToEBDD.translateGroupe(groupe);
        ConversationEBDD conversationEBDD = FromClassAppToEBDD.translateConversation(conversation);
        conversationEBDD.setDataBaseId(remoteBD.addDiscussion(conversationEBDD));
        groupEBDD.setConversationID(conversationEBDD.getDataBaseId());

        groupEBDD.setDatabaseID(remoteBD.addGroup(groupEBDD));
        conversationEBDD.setGroupID(groupEBDD.getDatabaseID());

        remoteBD.updateDiscussion(conversationEBDD.getDataBaseId(), conversationEBDD);
    }

    //add utilisateur dans Groupe
    public static void addUserToGroupOnEBDD(Utilisateur utilisateur, Groupe groupe, RemoteBD remoteBD) {
        groupe.addMember(utilisateur);
        LocalUserProfilEBDD profilEBDD = new LocalUserProfilEBDD();
        FromClassAppToEBDD.translateUtilisateur(utilisateur, profilEBDD, null, null);
        MyLocalGroupEBDD groupEBDD = FromClassAppToEBDD.translateGroupe(groupe);
        remoteBD.addUserToGroup(groupEBDD.getDatabaseID(), profilEBDD.getDataBaseId());

        remoteBD.updateGroup(groupEBDD.getDatabaseID(), groupEBDD);
    }
}
