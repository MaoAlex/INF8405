package com.example.alexmao.projetfinal.BDDInterne;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Classe abstraite servant de structures pour les diff�rentes requ�tes sur la base de donn�es
 * Les diff�rents acc�s aux tables vont h�riter de cette classe
 */
public abstract class AbstractBDD {

    // Nous sommes � la premi�re version de la base
    //On peut modifier la version en changeant simplement cet attribut
    protected final static int VERSION = 1;
    // Le nom du fichier qui repr�sente ma base de donn�es
    protected final static String NOM = "database.db";
    protected static SQLiteDatabase database_ = null;
    protected static BaseSQLite maBaseSQLite_ = null;

    public AbstractBDD(Context pContext) {
        //initialisation de la base de donn�es avec le contexte
        this.maBaseSQLite_ = new BaseSQLite(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la derni�re base puisque getWritableDatabase s'en charge
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