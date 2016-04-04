package com.example.alexmao.modeledonnees.BDDInterne;

/**
 * Created by alexMAO on 03/04/2016.
 * Fichier contenant les différentes requêtes pour la création des différentes tables de la BDD interne
 */
public class CreateTable {

    //Requete pour la création de la table utilisateur
    public static final String UTILISATEUR = "CREATE TABLE " + Table.UTILISATEUR + " ("
            + Colonne.ID_UTILISATEUR + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Colonne.NOM + " TEXT NOT NULL, "
            + Colonne.PRENOM + " TEXT NOT NULL, "
            + Colonne.DATE_NAISSANTE + " INTEGER,"
            + Colonne.MAIL + " TEXT NOT NULL, "
            + Colonne.PHOTO + " TEXT "
            + Colonne.LATITUDE + " REAL, "
            + Colonne.LONGITUDE + " REAL,"
            + Colonne.ID_FIREBASE + " TEXT NOT NULL );";

    //Requete pour la création de la table utilisateur connecté
    public static final String UTILISATEUR_CONNECTE = "CREATE TABLE " + Table.UTILISATEUR_CONNECTE + " ("
            + Colonne.ID_UTILISATEUR_CONNECTE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Colonne.NOM + " TEXT NOT NULL, "
            + Colonne.PRENOM + " TEXT NOT NULL, "
            + Colonne.DATE_NAISSANTE + " INTEGER,"
            + Colonne.MAIL + " TEXT NOT NULL, "
            + Colonne.PHOTO + " TEXT "
            + Colonne.LATITUDE + " REAL, "
            + Colonne.LONGITUDE + " REAL,"
            + Colonne.ID_UTILISATEUR + "INTEGER, FOREIGN KEY(" + Colonne.ID_UTILISATEUR + ") REFERENCES " + Table.UTILISATEUR + "( " + Colonne.ID_UTILISATEUR + " ), "
            + Colonne.ID_FIREBASE + " TEXT NOT NULL );";

    //Requete pour la création de la table parametre utilisateur
    public static final String PARAMETRE_UTILISATEUR = "CREATE TABLE " + Table.PARAMETRE_UTILISATEUR + " ("
            + Colonne.ID_PARAMETRE_UTILISATEUR + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Colonne.RAYON + " INTEGER, "
            + Colonne.DONNER_LOCALISATION + " INTEGER, "
            + Colonne.MASQUER_NOM + " INTEGER );";

    //Requete pour la création de la table evenement
    public static final String EVENEMENT = "CREATE TABLE " + Table.EVENEMENT + " ("
            + Colonne.ID_EVENEMENT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Colonne.NOMBRE_PARTICIPANTS + " INTEGER, "
            + Colonne.DATE_EVENEMENT + " INTEGER, "
            + Colonne.PHOTO + " TEXT, "
            + Colonne.ID_SPORT_ASSOCIE + "INTEGER, FOREIGN KEY(" + Colonne.ID_SPORT_ASSOCIE + ") REFERENCES " + Table.SPORT + "( " + Colonne.ID_SPORT_ASSOCIE + " ), "
            + Colonne.NOM_EVENEMENT + " TEXT, "
            + Colonne.PHOTO + " STRING, "
            + Colonne.ID_FIREBASE + " TEXT NOT NULL, "
            + Colonne.ID_GROUPE + "INTEGER, FOREIGN KEY(" + Colonne.ID_GROUPE + ") REFERENCES " + Table.GROUPE + "( " + Colonne.ID_GROUPE + " ), "
            + Colonne.LATITUDE + " REAL, "
            + Colonne.LONGITUDE + " REAL,"
            + Colonne.NOM_LIEU + " TEXT );";

    //Requete pour la création de la table evenement interesse
    public static final String EVENEMENT_INTERESSE = "CREATE TABLE " + Table.EVENEMENT_INTERESSE + " ("
            + Colonne.ID_UTILISATEUR + " INTEGER PRIMARY KEY AND FOREIGN KEY (" + Colonne.ID_UTILISATEUR + ") REFERENCES " + Table.UTILISATEUR + "( " + Colonne.ID_UTILISATEUR + " ), "
            + Colonne.ID_EVENEMENT + " INTEGER PRIMARY KEY AND FOREIGN KEY (" + Colonne.ID_EVENEMENT + ") REFERENCES " + Table.EVENEMENT + "( " + Colonne.ID_EVENEMENT + " ); ";

    //Requete pour la création de la table utilisateur evenement participant
    public static final String UTILISATEUR_EVENEMENT_PARTICIPANT = "CREATE TABLE " + Table.UTILISATEUR_PARTICIPANT_EVENEMENT + " ("
            + Colonne.ID_UTILISATEUR + " INTEGER PRIMARY KEY AND FOREIGN KEY (" + Colonne.ID_UTILISATEUR + ") REFERENCES " + Table.UTILISATEUR + "( " + Colonne.ID_UTILISATEUR + " ), "
            + Colonne.ID_EVENEMENT + " INTEGER PRIMARY KEY AND FOREIGN KEY (" + Colonne.ID_EVENEMENT + ") REFERENCES " + Table.EVENEMENT + "( " + Colonne.ID_EVENEMENT + " ); ";

    //Requete pour la création de la table invitation evenement
    public static final String INVITATION_EVENEMENT = "CREATE TABLE " + Table.INVITATION_EVENEMENT + " ("
            + Colonne.ID_INVITATION_EVENEMENT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Colonne.ID_EXPEDITEUR + " INTEGER, FOREIGN KEY(" + Colonne.ID_UTILISATEUR + ") REFERENCES " + Table.UTILISATEUR + "( " + Colonne.ID_UTILISATEUR + " ), "
            + Colonne.ID_INVITE + " INTEGER, FOREIGN KEY(" + Colonne.ID_UTILISATEUR + ") REFERENCES " + Table.UTILISATEUR + "( " + Colonne.ID_UTILISATEUR + " ), "
            + Colonne.DATE_INVITATION + " INTEGER, "
            + Colonne.ID_FIREBASE + " TEXT NOT NULL );";

    //Requete pour la création de la table sport
    public static final String SPORT = "CREATE TABLE " + Table.SPORT + " ("
            + Colonne.ID_SPORT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Colonne.NOM_SPORT + " TEXT NOT NULL ); ";

    //Requete pour la création de la table sport utilisateur
    public static final String SPORT_UTILISATEUR = "CREATE TABLE " + Table.SPORT_UTILISATEUR + " ("
            + Colonne.ID_UTILISATEUR + " INTEGER PRIMARY KEY AND FOREIGN KEY (" + Colonne.ID_UTILISATEUR + ") REFERENCES " + Table.UTILISATEUR + "( " + Colonne.ID_UTILISATEUR + " ), "
            + Colonne.ID_SPORT + " INTEGER PRIMARY KEY AND FOREIGN KEY (" + Colonne.ID_SPORT + ") REFERENCES " + Table.SPORT + "( " + Colonne.ID_SPORT + " ); ";

    //Requete pour la création de la table invitation connexion
    public static final String INVITATION_CONNEXION = "CREATE TABLE " + Table.INVITATION_CONNEXION + " ("
            + Colonne.ID_INVITATION_EVENEMENT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Colonne.ID_EXPEDITEUR + " INTEGER, FOREIGN KEY(" + Colonne.ID_UTILISATEUR + ") REFERENCES " + Table.UTILISATEUR + "( " + Colonne.ID_UTILISATEUR + " ), "
            + Colonne.ID_INVITE + " INTEGER, FOREIGN KEY(" + Colonne.ID_UTILISATEUR + ") REFERENCES " + Table.UTILISATEUR + "( " + Colonne.ID_UTILISATEUR + " ), "
            + Colonne.DATE_INVITATION + " INTEGER, "
            + Colonne.ID_FIREBASE + " TEXT NOT NULL );";

    //Requete pour la création de la table message
    public static final String MESSAGE = "CREATE TABLE " + Table.MESSAGE + " ("
            + Colonne.ID_MESSAGE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Colonne.MESSAGE + " TEXT, "
            + Colonne.DATE_MESSAGE + " INTEGER );";

    //Requete pour la création de la table message conversation
    public static final String MESSAGE_CONVERSATION = "CREATE TABLE " + Table.MESSAGE_CONVERSATION + " ("
            + Colonne.ID_MESSAGE + " INTEGER PRIMARY KEY AND FOREIGN KEY (" + Colonne.ID_MESSAGE + ") REFERENCES " + Table.MESSAGE + "( " + Colonne.ID_MESSAGE + " ), "
            + Colonne.ID_CONVERSATION + " INTEGER PRIMARY KEY AND FOREIGN KEY (" + Colonne.ID_SPORT + ") REFERENCES " + Table.CONVERSATION + "( " + Colonne.ID_CONVERSATION + " ); ";


    //Requete pour la création de la table conversation
    public static final String CONVERSATION = "CREATE TABLE " + Table.CONVERSATION + " ("
            + Colonne.ID_CONVERSATION + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Colonne.NOM_CONVERSATION + " TEXT, "
            + Colonne.ID_GROUPE + " INTEGER, FOREIGN KEY(" + Colonne.ID_GROUPE + ") REFERENCES " + Table.GROUPE + "( " + Colonne.ID_GROUPE + " ); ";

    //Requete pour la création de la table groupe
    public static final String GROUPE = "CREATE TABLE " + Table.GROUPE + " ("
            + Colonne.ID_GROUPE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Colonne.ID_CONVERSATION + " INTEGER, FOREIGN KEY (" + Colonne.ID_CONVERSATION+ ") REFERENCES " + Table.CONVERSATION + "( " + Colonne.ID_CONVERSATION + " ), "
            + Colonne.ID_EVENEMENT + " INTEGER ); ";

    //Requete pour la création de la table groupe utilisateur
    public static final String GROUPE_UTILISATEUR = "CREATE TABLE " + Table.GROUPE_UTILISATEUR + " ("
            + Colonne.ID_GROUPE + " INTEGER PRIMARY KEY AND FOREIGN KEY (" + Colonne.ID_GROUPE + ") REFERENCES " + Table.GROUPE + "( " + Colonne.ID_GROUPE + " ), "
            + Colonne.ID_UTILISATEUR + " INTEGER PRIMARY KEY AND FOREIGN KEY (" + Colonne.ID_UTILISATEUR + ") REFERENCES " + Table.UTILISATEUR + "( " + Colonne.ID_UTILISATEUR + " ); ";

}
