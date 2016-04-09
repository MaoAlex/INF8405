package com.example.alexmao.modeledonnees.BDDInterne;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
* Classe permettant la création de la BDD interne en SQLite
* */

public class BaseSQLite extends SQLiteOpenHelper {
    //création du TAG pour le debuggage du fichier
    private static final String TAG = "MaBaseSQLLite";

    //constructeur pour la base de données
    public BaseSQLite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //Méthode à appeler pour l'ouverture de la BDD
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            //Permet l'utilisation des clés étrangères
            //avec l'obligation que l'élément existe dans l'autre table
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    //méthodes OnCreate qui permet de créer la base de données
    //Il faut lui passer les bons arguments dans les execSQL
    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée les tables à partir de la requête écrite dans les variables de la classe CreateTable
        db.execSQL(CreateTable.UTILISATEUR);
        Log.d(TAG, "onCreate: Creation de la table utilisateur réussie");
        db.execSQL(CreateTable.UTILISATEUR_CONNECTE);
        Log.d(TAG, "onCreate: Creation de la table utilisateur connecté réussie");
        db.execSQL(CreateTable.PARAMETRE_UTILISATEUR);
        Log.d(TAG, "onCreate: Creation de la table parametre utilisateur");
        db.execSQL(CreateTable.EVENEMENT);
        Log.d(TAG, "onCreate: Creation de la table evenement");
        db.execSQL(CreateTable.EVENEMENT_INTERESSE);
        Log.d(TAG, "onCreate: Creation de la table evenement interesse");
        db.execSQL(CreateTable.PARTICIPANT_EVENEMENT);
        Log.d(TAG, "onCreate: Creation de la table utilisateur evenement participant réussie");
        db.execSQL(CreateTable.INVITATION_EVENEMENT);
        Log.d(TAG, "onCreate: Creation de la table invitation evenement réussie");
        //db.execSQL(CreateTable.SPORT);
        Log.d(TAG, "onCreate: Creation de la table sport réussie");
        db.execSQL(CreateTable.SPORT_UTILISATEUR);
        Log.d(TAG, "onCreate: Creation de la table utilisateur réussie");
        db.execSQL(CreateTable.INVITATION_CONNEXION);
        Log.d(TAG, "onCreate: Creation de la table invitation connexion réussie");
        db.execSQL(CreateTable.MESSAGE);
        Log.d(TAG, "onCreate: Creation de la table Message réussie");
        db.execSQL(CreateTable.MESSAGE_CONVERSATION);
        Log.d(TAG, "onCreate: Creation de la table Message conversation réussie");
        db.execSQL(CreateTable.CONVERSATION);
        Log.d(TAG, "onCreate: Creation de la table Convers réussie");
        db.execSQL(CreateTable.GROUPE);
        Log.d(TAG, "onCreate: Creation de la table groupe réussie");
        db.execSQL(CreateTable.GROUPE_UTILISATEUR);
        Log.d(TAG, "onCreate: Creation de la table groupe utilisateur réussie");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On réinitialise les Tables pour les mettres à jour
        //cette méthode est utilisé en cas de mise à jour de l'application
        db.execSQL("DROP TABLE IF EXISTS " + Table.UTILISATEUR + ";");
        Log.d(TAG, "onUpgrade: Creation de la table utilisateur réussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.UTILISATEUR_CONNECTE + ";");
        Log.d(TAG, "onUpgrade: Creation de la table utilisateur connecté réussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.PARAMETRE_UTILISATEUR + ";");
        Log.d(TAG, "onUpgrade: Creation de la table parametre utilisateur réussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.EVENEMENT + ";");
        Log.d(TAG, "onUpgrade: Creation de la table evenement réussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.EVENEMENT_INTERESSE + ";");
        Log.d(TAG, "onUpgrade: Creation de la table evenement interesse réussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.PARTICIPANT_EVENEMENT + ";");
        Log.d(TAG, "onUpgrade: Creation de la table utilisateur participant evenement réussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.INVITATION_EVENEMENT + ";");
        Log.d(TAG, "onUpgrade: Creation de la table invitation evenement réussie");
//        db.execSQL("DROP TABLE IF EXISTS " + Table.SPORT + ";");
        Log.d(TAG, "onUpgrade: Creation de la table sport réussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.SPORT_UTILISATEUR + ";");
        Log.d(TAG, "onUpgrade: Creation de la table sport utilisateur réussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.INVITATION_CONNEXION + ";");
        Log.d(TAG, "onUpgrade: Creation de la table invitation connexion réussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.MESSAGE + ";");
        Log.d(TAG, "onUpgrade: Creation de la table message réussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.MESSAGE_CONVERSATION + ";");
        Log.d(TAG, "onUpgrade: Creation de la table message conversation réussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.CONVERSATION + ";");
        Log.d(TAG, "onUpgrade: Creation de la table conversation réussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.GROUPE + ";");
        Log.d(TAG, "onUpgrade: Creation de la table groupe réussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.GROUPE_UTILISATEUR + ";");
        Log.d(TAG, "onUpgrade: Creation de la table groupe utilisateur réussie");

        onCreate(db);
    }

}