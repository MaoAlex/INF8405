package com.example.alexmao.projetfinal.BDDInterne;

/**
 * Created by alexMAO on 02/04/2016.
 * Classe contenant les différents nom des colonnes pour les différentes tables
 * Les lignes en commentaires signifient que cette colonne est déjà présente dans une autre table
 */
public class Colonne {

    //Nom des colonnes pour la table utilisateur
    public static final String ID_UTILISATEUR = "id_utilisateur";
    public static final String NOM = "nom";
    public static final String PRENOM = "prenom";
    public static final String DATE_NAISSANTE = "date_naissance";
    public static final String MAIL = "mail";
    public static final String PHOTO = "photo";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String ID_FIREBASE = "id_firebase";

    //Nom des colonnes pour la table utilisateur connecte
    public static final String ID_UTILISATEUR_CONNECTE = "id_utilisateur_connecte";
//    public static final String NOM = "nom";
//    public static final String PRENOM = "prenom";
//    public static final String DATE_NAISSANTE = "date_naissance";
//    public static final String MAIL = "mail";
//    public static final String PHOTO = "photo";
//    public static final String LATITUDE = "latitude";
//    public static final String LONGITUDE = "longitude";
//    public static final String ID_FIREBASE = "id_firebase";

    //Nom des colonnes pour la table parametre utilisateur
    public static final String ID_PARAMETRE_UTILISATEUR = "id_parametre_utilisateur";
    public static final String RAYON = "rayon";
    public static final String DONNER_LOCALISATION = "donner_localisation";
    public static final String MASQUER_NOM = "masquer_nom";

    //Nom des colonnes pour la table evenement
    public static final String ID_EVENEMENT = "id_evenement";
    public static final String NOMBRE_PARTICIPANTS = "nombre_participants";
    public static final String DATE_EVENEMENT = "date_evenement";
//    public static final String PHOTO = "photo";
    public static final String SPORT_ASSOCIE = "sport_associe";
    public static final String NOM_EVENEMENT = "nom_evenement";
    //    public static final String ID_FIREBASE = "id_firebase";
    //    public static final String ID_GROUPE = "id_groupe";
//    public static final String LATITUDE = "latitude";
//    public static final String LONGITUDE = "longitude";
    public static final String NOM_LIEU = "nom_lieu";
    public static final String ID_ORGANISATEUR = "id_organisateur";
    public static final String VISIBILITE = "visibilite";

    //Nom des colonnes pour la table evenement interesse
//    public static final String ID_UTILISATEUR = "id_utilisateur";
//    public static final String ID_EVENEMENT = "id_evenement";

    //Nom des colonnes pour la table utilisateur evenement participant
//    public static final String ID_UTILISATEUR = "id_utilisateur";
//    public static final String ID_EVENEMENT = "id_evenement";

    //Nom des colonnes pour la table invitation evenement
    public static final String ID_INVITATION_EVENEMENT = "id_invitation_evenement";
//    public static final String ID_EVENEMENT = "id_evenement";
    public static final String ID_EXPEDITEUR = "id_expediteur";
    public static final String ID_INVITE = "id_invite";
    public static final String DATE_INVITATION = "date_invitation";
//    public static final String ID_FIREBASE = "id_firebase";

    //Nom des colonnes pour la table invitation sport
   /* public static final String ID_SPORT = "id_sport";
    public static final String NOM_SPORT = "nom_sport";*/

    //Nom des colonnes pour la table sport utilisateur
    public static final String ID_SPORT_UTILISATEUR = "id_sport_utilisateur";
//    public static final String ID_UTILISATEUR = "id_utilisateur";
    public static final String SPORT = "sport";

    //Nom des colonnes pour la table invitation connexion
    public static final String ID_INVITATION_CONNEXION = "id_invitation_connexion";
//    public static final String ID_EXPEDITEUR = "id_expediteur";
//    public static final String ID_INVITE = "id_invite";
//    public static final String DATE_INVITATION = "date_invitation";
//    public static final String ID_FIREBASE = "id_firebase";

    //Nom des colonnes pour la table message
    public static final String ID_MESSAGE = "id_message";
    public static final String MESSAGE = "message";
    public static final String DATE_MESSAGE = "date_message";
//    public static final String ID_EXPEDITEUR = "id_expediteur";

    //Nom des colonnes pour la table message conversation
//    public static final String ID_MESSAGE = "id_message";
//    public static final String ID_SPORT = "id_sport";

    //Nom des colonnes pour la table conversation
    public static final String ID_CONVERSATION = "id_conversation";
    public static final String NOM_CONVERSATION = "nom_conversation";

    //Nom des colonnes pour la table groupe
    public static final String ID_GROUPE = "id_groupe";
    //    public static final String ID_CONVERSATION = "id_conversation";
    //    public static final String ID_FIREBASE = "id_firebase";
    //    public static final String ID_EVENEMENT = "id_evenement";

    //Nom des colonnes pour la table groupe utilisateur
//    public static final String ID_GROUPE = "id_groupe";
//    public static final String ID_UTILISATEUR = "id_utilisateur";

    //Nom des colonnes pour la table utilisateurs connexions
//    public static final String ID_UTILISATEUR = "id_utilisateur";
    public static final String ID_CONNEXION = "id_connexion";

}
