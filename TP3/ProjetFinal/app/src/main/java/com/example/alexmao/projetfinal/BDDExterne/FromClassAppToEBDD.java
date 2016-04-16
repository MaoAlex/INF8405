package com.example.alexmao.projetfinal.BDDExterne;

import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.Groupe;
import com.example.alexmao.projetfinal.classeApp.InvitationConnexion;
import com.example.alexmao.projetfinal.classeApp.Message;
import com.example.alexmao.projetfinal.classeApp.ParametresUtilisateur;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by filou on 07/04/16.
 */
public class FromClassAppToEBDD {

    public static MessageEBDD transalateMessage(Message message) {
        MessageEBDD ebddClass = new MessageEBDD();
        ebddClass.setMessage(message.getMessage());
        ebddClass.setDate(message.getDate().getTime());
        ebddClass.setExpediteurID(message.getExpediteur().getIdFirebase());

        return ebddClass;
    }

    public static MyLocalGroupEBDD translateGroupe(Groupe  groupe) {
        MyLocalGroupEBDD ebddClass = new MyLocalGroupEBDD();
        ebddClass.setDatabaseID(groupe.getIdFirebase());
//        ebddClass.setConversationID(groupe.getConversation().getIdFirebase());
        List<String> userIDs = new ArrayList<>();
        for (Utilisateur utilisateur: groupe.getListeMembre()) {
            userIDs.add(utilisateur.getIdFirebase());
        }
        ebddClass.setMembersID(userIDs);

        return ebddClass;
    }

    public static void translateUtilisateur(Utilisateur utilisateur
            , LocalUserProfilEBDD localUserProfilEBDD, Position position) {
        localUserProfilEBDD.setDataBaseId(utilisateur.getIdFirebase());
        localUserProfilEBDD.setMailAdr(utilisateur.getMail());
        localUserProfilEBDD.setDateBirth(utilisateur.getDateNaissance().getTime());
        localUserProfilEBDD.setFirstName(utilisateur.getPrenom());
        localUserProfilEBDD.setFirstName(utilisateur.getNom());
        localUserProfilEBDD.setLastName(utilisateur.getPrenom());
        localUserProfilEBDD.setSports(utilisateur.getSports());

        position.setLatitude(utilisateur.getLatitude());
        position.setLongitude(utilisateur.getLongitude());
    }

    public static MyLocalEventEBDD translateEvenement(Evenement evenement) {
        MyLocalEventEBDD ebddClass = new MyLocalEventEBDD(evenement.getNbreMaxParticipants(),
                evenement.getSport(), evenement.getNomEvenement(),
                evenement.getOrganisateur().getIdFirebase(),
                evenement.getVisibilite());

        ebddClass.setLongitude(evenement.getLongitude());
        ebddClass.setLatitude(evenement.getLatitude());
        ebddClass.setDataBaseId(evenement.getIdFirebase());
        ebddClass.setDate(evenement.getDate() == null ? 0 : evenement.getDate().getTime());
        ebddClass.setLieu(evenement.getLieu());
        ebddClass.setGroupID(evenement.getGroupeAssocie().getIdFirebase());

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
        ebddClass.setDate(invitationConnexion.getDate().getTime());
        ebddClass.setExpediteur(invitationConnexion.getExpediteur().getIdFirebase());
        ebddClass.setInvite(invitationConnexion.getInvite().getIdFirebase());

        return ebddClass;
    }
}
