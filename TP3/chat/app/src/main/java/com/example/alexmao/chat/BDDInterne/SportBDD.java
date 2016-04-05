package com.example.alexmao.chat.BDDInterne;

/**
 * Created by alexMAO on 04/04/2016.
 */
/*
public class SportBDD extends AbstractBDD {

    public static final String TAG = "SportBDD";

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_NOM_SPORT = 1;

    public SportBDD(Context pContext) {
        super(pContext);
    }

    public long insererSport(String sport){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        //On va mettre les sports dans la BDD
        ContentValues values = new ContentValues();
        values.put(Colonne.NOM_SPORT, sport);

        return database_.insert(Table.SPORT, null, values);
    }

    public int supprimerSport(String sport){
        //On supprime le sport de la BDD
        //Le troisième argument de delete remplace le ? du deuxième argument
        return database_.delete(Table.SPORT, Colonne.NOM_SPORT + " = ?", new String[]{sport});
    }
}*/
