package com.example.alexmao.modeledonnees.BDDInterne;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
* Classe permettant la cr�ation de la BDD interne en SQLite
* */

public class BaseSQLite extends SQLiteOpenHelper {
    //cr�ation du TAG pour le debuggage du fichier
    private static final String TAG = "MaBaseSQLLite";

    //constructeur pour la base de donn�es
    public BaseSQLite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //M�thode � appeler pour l'ouverture de la BDD
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            //Permet l'utilisation des cl�s �trang�res
            //avec l'obligation que l'�l�ment existe dans l'autre table
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    //m�thodes OnCreate qui permet de cr�er la base de donn�es
    //Il faut lui passer les bons arguments dans les execSQL
    @Override
    public void onCreate(SQLiteDatabase db) {
        //on cr�e les tables � partir de la requ�te �crite dans les variables de la classe CreateTable
        db.execSQL(CreateTable.UTILISATEUR);
        Log.d(TAG, "onCreate: Creation de la table utilisateur r�ussie");
        db.execSQL(CreateTable.UTILISATEUR_CONNECTE);
        Log.d(TAG, "onCreate: Creation de la table utilisateur connect� r�ussie");
        db.execSQL(CreateTable.PARAMETRE_UTILISATEUR);
        Log.d(TAG, "onCreate: Creation de la table parametre utilisateur");
        db.execSQL(CreateTable.EVENEMENT);
        Log.d(TAG, "onCreate: Creation de la table evenement");
        db.execSQL(CreateTable.EVENEMENT_INTERESSE);
        Log.d(TAG, "onCreate: Creation de la table evenement interesse");
        db.execSQL(CreateTable.PARTICIPANT_EVENEMENT);
        Log.d(TAG, "onCreate: Creation de la table utilisateur evenement participant r�ussie");
        db.execSQL(CreateTable.INVITATION_EVENEMENT);
        Log.d(TAG, "onCreate: Creation de la table invitation evenement r�ussie");
        //db.execSQL(CreateTable.SPORT);
        Log.d(TAG, "onCreate: Creation de la table sport r�ussie");
        db.execSQL(CreateTable.SPORT_UTILISATEUR);
        Log.d(TAG, "onCreate: Creation de la table utilisateur r�ussie");
        db.execSQL(CreateTable.INVITATION_CONNEXION);
        Log.d(TAG, "onCreate: Creation de la table invitation connexion r�ussie");
        db.execSQL(CreateTable.MESSAGE);
        Log.d(TAG, "onCreate: Creation de la table Message r�ussie");
        db.execSQL(CreateTable.MESSAGE_CONVERSATION);
        Log.d(TAG, "onCreate: Creation de la table Message conversation r�ussie");
        db.execSQL(CreateTable.CONVERSATION);
        Log.d(TAG, "onCreate: Creation de la table Convers r�ussie");
        db.execSQL(CreateTable.GROUPE);
        Log.d(TAG, "onCreate: Creation de la table groupe r�ussie");
        db.execSQL(CreateTable.GROUPE_UTILISATEUR);
        Log.d(TAG, "onCreate: Creation de la table groupe utilisateur r�ussie");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On r�initialise les Tables pour les mettres � jour
        //cette m�thode est utilis� en cas de mise � jour de l'application
        db.execSQL("DROP TABLE IF EXISTS " + Table.UTILISATEUR + ";");
        Log.d(TAG, "onUpgrade: Creation de la table utilisateur r�ussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.UTILISATEUR_CONNECTE + ";");
        Log.d(TAG, "onUpgrade: Creation de la table utilisateur connect� r�ussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.PARAMETRE_UTILISATEUR + ";");
        Log.d(TAG, "onUpgrade: Creation de la table parametre utilisateur r�ussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.EVENEMENT + ";");
        Log.d(TAG, "onUpgrade: Creation de la table evenement r�ussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.EVENEMENT_INTERESSE + ";");
        Log.d(TAG, "onUpgrade: Creation de la table evenement interesse r�ussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.PARTICIPANT_EVENEMENT + ";");
        Log.d(TAG, "onUpgrade: Creation de la table utilisateur participant evenement r�ussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.INVITATION_EVENEMENT + ";");
        Log.d(TAG, "onUpgrade: Creation de la table invitation evenement r�ussie");
//        db.execSQL("DROP TABLE IF EXISTS " + Table.SPORT + ";");
        Log.d(TAG, "onUpgrade: Creation de la table sport r�ussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.SPORT_UTILISATEUR + ";");
        Log.d(TAG, "onUpgrade: Creation de la table sport utilisateur r�ussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.INVITATION_CONNEXION + ";");
        Log.d(TAG, "onUpgrade: Creation de la table invitation connexion r�ussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.MESSAGE + ";");
        Log.d(TAG, "onUpgrade: Creation de la table message r�ussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.MESSAGE_CONVERSATION + ";");
        Log.d(TAG, "onUpgrade: Creation de la table message conversation r�ussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.CONVERSATION + ";");
        Log.d(TAG, "onUpgrade: Creation de la table conversation r�ussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.GROUPE + ";");
        Log.d(TAG, "onUpgrade: Creation de la table groupe r�ussie");
        db.execSQL("DROP TABLE IF EXISTS " + Table.GROUPE_UTILISATEUR + ";");
        Log.d(TAG, "onUpgrade: Creation de la table groupe utilisateur r�ussie");

        onCreate(db);
    }

}