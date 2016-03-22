package com.example.alexmao.tp2final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by alexMAO on 15/03/2016.
 */
public abstract class AbstractBDD {

    // Nous sommes à la première version de la base
    // Si je décide de la mettre à jour, il faudra changer cet attribut
    protected final static int VERSION = 1;
    // Le nom du fichier qui représente ma base
    protected final static String NOM = "database.db";
    protected SQLiteDatabase database_ = null;
    protected MaBaseSQLite maBaseSQLite_ = null;

    public AbstractBDD(Context pContext) {
        this.maBaseSQLite_ = new MaBaseSQLite(pContext, NOM, null, VERSION);
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