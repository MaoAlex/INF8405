package com.example.alexmao.chat.BDDExterne;

import com.example.alexmao.chat.classeApp.Evenement;
import com.example.alexmao.chat.classeApp.Groupe;
import com.example.alexmao.chat.classeApp.Message;
import com.example.alexmao.chat.classeApp.ParametresUtilisateur;
import com.example.alexmao.chat.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by filou on 07/04/16.
 */
public class FromClassAppToEBDD {

    public static MessageBDD transalateMessage(Message message) {
        MessageBDD ebddClass = new MessageBDD();
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
}
