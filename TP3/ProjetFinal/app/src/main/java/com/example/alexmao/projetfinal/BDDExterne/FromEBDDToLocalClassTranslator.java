package com.example.alexmao.projetfinal.BDDExterne;

import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.Groupe;
import com.example.alexmao.projetfinal.classeApp.InvitationConnexion;
import com.example.alexmao.projetfinal.classeApp.InvitationEvenement;
import com.example.alexmao.projetfinal.classeApp.Message;
import com.example.alexmao.projetfinal.classeApp.ParametresUtilisateur;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.Date;

/**
 * Created by filou on 07/04/16.
 */
/*
 *TODO voir s'il est possible de traduire les ids firebase en données locales via la BDD interne
 * (Demander à Alex)
 * Dans toutes les classes ci-dessous, il y a des id firebase qu'il faudrait traduire,
 * voir comment on fait ça
 */
public class FromEBDDToLocalClassTranslator {

    public static Utilisateur utilisateurFromEBDD(LocalUserProfilEBDD userProfil) {
        Utilisateur localClass = new Utilisateur();

        localClass.setIdFirebase(userProfil.getDataBaseId());
        localClass.setDateNaissance(userProfil.getDateBirth());
        localClass.setMail(userProfil.getMailAdr());
        localClass.setNom(userProfil.getLastName());
        localClass.setPrenom(userProfil.getFirstName());
        localClass.setListeConnexion(userProfil.getListeConnexion());
        localClass.setListeInteretsID(userProfil.getListeInteretsID());
        localClass.setSports(userProfil.getSports());
        localClass.setPhoto(null);

        return localClass;
    }

    public static Evenement translateEvent(MyLocalEventEBDD myLocalEvent) {
        //Par exemple dans cette fonction il faudrait convertir organisateur ID en une classe
        //possible en SQL?
        Evenement localClass = new Evenement();
        localClass.setDate((myLocalEvent.getDate()));
        localClass.setIdFirebase(myLocalEvent.getId());
        localClass.setLatitude(myLocalEvent.getLatitude());
        localClass.setLongitude(myLocalEvent.getLongitude());
        localClass.setLieu(myLocalEvent.getLieu());
        localClass.setSport(myLocalEvent.getSport());
        localClass.setVisibilite(myLocalEvent.getVisibilite());

        return localClass;
    }

    //Création d'un utilisateur à partir de la BDD externe
    public static Utilisateur translateUserProfil(LocalUserProfilEBDD userProfil, Position position, String idFirebase, ParametresUtilisateur parametresUtilisateur) {
        Utilisateur localClass = new Utilisateur();
        localClass.setLatitude(position.getLatitude());
        localClass.setLongitude(position.getLongitude());
        localClass.setIdFirebase(userProfil.getDataBaseId());
        localClass.setDateNaissance(userProfil.getDateBirth());
        localClass.setMail(userProfil.getMailAdr());
        localClass.setNom(userProfil.getLastName());
        localClass.setPrenom(userProfil.getFirstName());
        localClass.setIdFirebase(idFirebase);
        localClass.setParametres(parametresUtilisateur);
        localClass.setListeConnexion(userProfil.getListeConnexion());
        localClass.setListeInteretsID(userProfil.getListeInteretsID());
        localClass.setSports(userProfil.getSports());
        localClass.setPhoto(null);
        return localClass;
    }

    public static Message translateMessageBDD(MessageEBDD messageEBDD) {
        Message localClass = new Message();
        localClass.setMessage(messageEBDD.getMessage());
        localClass.setDate(new Date(messageEBDD.getDate()));

        return localClass;
    }

    public static Groupe translateGroupBDD(MyLocalGroupEBDD myGroup) {
        Groupe localClass = new Groupe();
        localClass.setIdFirebase(myGroup.getDatabaseID());

        return localClass;
    }

    public static ParametresUtilisateur translateUserParam(UserParamsEBDD userParamsEBDD) {
        ParametresUtilisateur localClass = new ParametresUtilisateur();
        localClass.setRayon(userParamsEBDD.getRayon());
        localClass.setMasquerNom(userParamsEBDD.isMasquerNom());
        localClass.setLocalisation(userParamsEBDD.isLocalisation());

        return localClass;
    }

    public static InvitationConnexion translateInvitationConnexionEBDD(InvitationConnexionEBDD invitationConnexionEBDD) {
        InvitationConnexion localClass = new InvitationConnexion();
        localClass.setDate(new Date(invitationConnexionEBDD.getDate()));
        localClass.setIdFirebase(invitationConnexionEBDD.getIdFirebase());

        return localClass;
    }

    public static InvitationEvenement translateInvitationEvenementEBDD(InvitationEvenementEBDD invitationEvenementEBDD) {
        InvitationEvenement localClass = new InvitationEvenement();
        localClass.setIdFirebase(invitationEvenementEBDD.getIdFirebase());
        localClass.setDate(new Date(invitationEvenementEBDD.getDate()));

        return localClass;
    }
}
