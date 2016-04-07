package com.example.alexmao.chat.BDDExterne;

import com.example.alexmao.chat.classeApp.Evenement;
import com.example.alexmao.chat.classeApp.Utilisateur;

import java.util.Date;

/**
 * Created by filou on 07/04/16.
 */
public class FromEBDDToLocalClassTranslator {

    public static Evenement translateEvent(MyLocalEvent myLocalEvent) {
        Evenement localClass = new Evenement();
        localClass.setDate(new Date(myLocalEvent.getDate()));
        localClass.setIdFirebase(myLocalEvent.getId());
        localClass.setLatitude(myLocalEvent.getLatitude());
        localClass.setLongitude(myLocalEvent.getLongitude());
        localClass.setLieu(myLocalEvent.getLieu());
        localClass.setSport(myLocalEvent.getSport());
        localClass.setVisibilite(myLocalEvent.getVisibilite());

        return localClass;
    }

    public static Utilisateur translateUserProfil(LocalUserProfil userProfil, Position position) {
        Utilisateur localClass = new Utilisateur();
        localClass.setLatitude(position.getLatitude());
        localClass.setLongitude(position.getLongitude());
        localClass.setIdFirebase(userProfil.getDataBaseId());
        localClass.setDateNaissance(new  Date(userProfil.getDateBirth()));
        localClass.setMail(userProfil.getMailAdr());
        localClass.setNom(userProfil.getLastName());
        localClass.setPrenom(userProfil.getFirstName());

        return localClass;
    }
}
