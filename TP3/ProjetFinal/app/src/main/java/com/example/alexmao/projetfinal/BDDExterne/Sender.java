package com.example.alexmao.projetfinal.BDDExterne;

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
        eventEBDD.setDataBaseId(remoteBD.addEvent(eventEBDD));
        conversationEBDD.setDataBaseId(remoteBD.addDiscussion(conversationEBDD));
        groupEBDD.setEventID(eventEBDD.getId());
        groupEBDD.setConversationID(conversationEBDD.getDataBaseId());
        groupEBDD.setDatabaseID(remoteBD.addGroup(groupEBDD));
        eventEBDD.setGroupID(groupEBDD.getDatabaseID());
        conversationEBDD.setGroupID(groupEBDD.getDatabaseID());
        remoteBD.updateDiscussion(conversationEBDD.getDataBaseId(), conversationEBDD);
        remoteBD.updateEvent(eventEBDD.getId(), eventEBDD);

        groupe.setIdFirebase(groupEBDD.getDatabaseID());
        evenement.setIdFirebase(eventEBDD.getDataBaseId());
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
