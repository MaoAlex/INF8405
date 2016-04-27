package com.example.alexmao.projetfinal.BDDExterne;

import android.graphics.Bitmap;

import com.example.alexmao.projetfinal.classeApp.Conversation;
import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.Groupe;
import com.example.alexmao.projetfinal.classeApp.InvitationConnexion;
import com.example.alexmao.projetfinal.classeApp.InvitationEvenement;
import com.example.alexmao.projetfinal.classeApp.Message;
import com.example.alexmao.projetfinal.classeApp.ParametresUtilisateur;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by filou on 07/04/16.
 */
public class FromClassAppToEBDD {

    public static MessageEBDD transalateMessage(Message message) {
        MessageEBDD ebddClass = new MessageEBDD();
        ebddClass.setMessage(message.getMessage());
        ebddClass.setDate(message.getDate());
        ebddClass.setExpediteurID(message.getExpediteur().getIdFirebase());

        return ebddClass;
    }

    public static MyLocalGroupEBDD translateGroupe(Groupe  groupe) {
        MyLocalGroupEBDD ebddClass = new MyLocalGroupEBDD();
        ebddClass.setDatabaseID(groupe.getIdFirebase());
        ebddClass.setConversationID(groupe.getConversation());
        List<String> userIDs = new ArrayList<>();
        for (Utilisateur utilisateur: groupe.getListeMembre()) {
            userIDs.add(utilisateur.getIdFirebase());
        }
        ebddClass.setMembersID(userIDs);
        ebddClass.setEventID(groupe.getEvenement().getIdFirebase());

        return ebddClass;
    }

    public static void translateUtilisateur(Utilisateur utilisateur
            , LocalUserProfilEBDD localUserProfilEBDD, Position position, Bitmap bitmap) {
        localUserProfilEBDD.setDataBaseId(utilisateur.getIdFirebase());
        localUserProfilEBDD.setMailAdr(utilisateur.getMail());
        long birth = utilisateur.getDateNaissance();
        localUserProfilEBDD.setDateBirth(birth);
        localUserProfilEBDD.setFirstName(utilisateur.getPrenom());
        localUserProfilEBDD.setFirstName(utilisateur.getNom());
        localUserProfilEBDD.setLastName(utilisateur.getPrenom());
        localUserProfilEBDD.setSports(utilisateur.getSports());
        if (bitmap != null) {
            localUserProfilEBDD.setPicture(new Picture(bitmap));
        }
        List<String> connexionID = new ArrayList<>();
        for (String remoteUser : utilisateur.getListeConnexion()) {
            connexionID.add(remoteUser);
        }
        localUserProfilEBDD.setListeConnexion(connexionID);

        if (position != null) {
            position.setLatitude(utilisateur.getLatitude());
            position.setLongitude(utilisateur.getLongitude());
        }
    }

    public static MyLocalEventEBDD translateEvenement(Evenement evenement, Bitmap bitmap) {
        MyLocalEventEBDD ebddClass = new MyLocalEventEBDD(evenement.getNbreMaxParticipants(),
                evenement.getSport(), evenement.getNomEvenement(),
                evenement.getOrganisateur().getIdFirebase(),
                evenement.getVisibilite());

        ebddClass.setLongitude(evenement.getLongitude());
        ebddClass.setLatitude(evenement.getLatitude());
        ebddClass.setDataBaseId(evenement.getIdFirebase());
        ebddClass.setDate(evenement.getDate());
        ebddClass.setLieu(evenement.getLieu());
        ebddClass.setGroupID(evenement.getGroupeAssocie().getIdFirebase());
        if (bitmap != null)
            ebddClass.setPicture(new Picture(bitmap));

        return ebddClass;
    }

    public static UserParamsEBDD translateParametres(ParametresUtilisateur parametresUtilisateur) {
        UserParamsEBDD ebddClass = new UserParamsEBDD();
        ebddClass.setLocalisation(parametresUtilisateur.isLocalisation());
        ebddClass.setMasquerNom(parametresUtilisateur.isMasquerNom());
        ebddClass.setRayon(parametresUtilisateur.getRayon());

        return ebddClass;
    }

    public static InvitationConnexionEBDD translateInvitationConnexion(InvitationConnexion invitationConnexion) {
        InvitationConnexionEBDD ebddClass = new InvitationConnexionEBDD();
        ebddClass.setIdFirebase(invitationConnexion.getIdFirebase());
        ebddClass.setDate(invitationConnexion.getDate());
        ebddClass.setExpediteur(invitationConnexion.getExpediteur().getIdFirebase());
        ebddClass.setInvite(invitationConnexion.getInvite().getIdFirebase());

        return ebddClass;
    }

    public static InvitationEvenementEBDD translateInvitationEvenement(InvitationEvenement invitation) {
        InvitationEvenementEBDD ebddClass = new InvitationEvenementEBDD();

        ebddClass.setIdFirebase(invitation.getIdFirebase());
        ebddClass.setInvite(invitation.getInvite().getIdFirebase());
        ebddClass.setDate(invitation.getDate());
        ebddClass.setExpediteur(invitation.getIdFirebase());
        ebddClass.setEvenementIdFirebase(invitation.getEvenement().getIdFirebase());

        return ebddClass;
    }

    public static ConversationEBDD translateConversation(Conversation conversation) {
        ConversationEBDD ebddClass = new ConversationEBDD();

        ebddClass.setDataBaseId(conversation.getIdFirebase());
        ebddClass.setNomConversation(conversation.getNomConversation());
        ebddClass.setGroupID(conversation.getGroupID());

        List<MessageEBDD> messageEBDDs = new LinkedList<>();
        for (Message msg :conversation.getListeMessage()) {
            messageEBDDs.add(FromClassAppToEBDD.transalateMessage(msg));
        }

        ebddClass.setListeMessage(messageEBDDs);

        return ebddClass;
    }
}
