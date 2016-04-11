package com.example.alexmao.projetfinal.BDDInterne;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Classe abstraite servant de structures pour les différentes requêtes sur la base de données
 * Les différents accès aux tables vont hériter de cette classe
 */
public abstract class AbstractBDD {

    // Nous sommes à la première version de la base
    //On peut modifier la version en changeant simplement cet attribut
    protected final static int VERSION = 1;
    // Le nom du fichier qui représente ma base de données
    protected final static String NOM = "database.db";
    protected static SQLiteDatabase database_ = null;
    protected static BaseSQLite maBaseSQLite_ = null;

    public AbstractBDD(Context pContext) {
        //initialisation de la base de données avec le contexte
        this.maBaseSQLite_ = new BaseSQLite(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        database_ = maBaseSQLite_.getWritableDatabase();
        return database_;
    }

    public void close() {
        database_.close();
    }

    public SQLiteDatabase getDatabase() {
        return database_;
    }

}