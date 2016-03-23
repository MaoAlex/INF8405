package com.example.alexmao.tp2final;

/**
 * Created by alexMAO on 14/03/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class MaBaseSQLite extends SQLiteOpenHelper {
    private static final String TAG = "MaBaseSQLLite";

    public static final String TABLE_GROUPE = "table_groupe";
    public static final String TABLE_USERS = "table_users";
    public static final String TABLE_PREFERENCE = "table_preference";
    public static final String TABLE_DISPONIBILITITE = "table_disponibilite";
    public static final String TABLE_EVENEMENT = "table_evenement";
    public static final String TABLE_LIEU = "table_lieu";

    public static final String TABLE_LOCALISATION = "table_localisation";
    public static final String TABLE_VOTE = "table_vote";
    public static final String TABLE_LIEU_CHOISI = "table_lieu_choisi";

    public static final String TABLE_INDEX = "table_index";
    public static final String TABLE_CURRENT_USER = "table_current_user";


    public static final String COL_ID = "id";
    public static final String COL_NOM = "Nom";
    public static final String COL_PRENOM = "Prenom";
    public static final String COL_MAIL = "mail";
    public static final String COL_KEY = "id";
    public static final String COL_GROUP_NAME = "nom_du_groupe";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_EST_ORGANISATEUR = "est_organisateur";
    public static final String COL_PREFERENCE = "preference";
    public static final String COL_DISPONIBILITE_DATE = "date";
    public static final String COL_DISPONIBILITE_HEURE_DEBUT = "heure_debut";
    public static final String COL_DISPONIBILITE_HEURE_FIN = "heure_fin";
    public static final String COL_ID_FIREBASE = "id_firebase";
    public static final String COL_DESCRIPTION = "description";

    public static final String COL_PHOTO = "photo";
    public static final String COL_LIEU_ID = "lieu_id";
    private static final String COL_EVENEMENT_ID = "evenement_id";
    private static final String COL_EVENEMENT_NAME = "evenement_nom";
    private static final String COL_TYPE = "type";
    private static final String COL_NOM_LIEU = "nom_lieu";
    private static final String COL_POSITION_X = "position_x";
    private static final String COL_POSITION_Y = "position_y";
    public static final String COL_DATE = "date";


    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NOM + " TEXT NOT NULL, "
            + COL_PRENOM + " TEXT NOT NULL, " + COL_MAIL + " TEXT NOT NULL,"
            + COL_ID_FIREBASE + " TEXT, "
            + COL_PHOTO + " TEXT );";


    private static final String CREATE_TABLE_CURRENT_USER = "CREATE TABLE " + TABLE_CURRENT_USER + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NOM + " TEXT NOT NULL, "
            + COL_PRENOM + " TEXT NOT NULL, " + COL_MAIL + " TEXT NOT NULL,"
            + COL_ID_FIREBASE + " TEXT, "
            + COL_PHOTO + " TEXT );";

   /* private static final String CREATE_TABLE_GROUPES = "CREATE TABLE " + TABLE_GROUPE + " ("
            + COL_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_GROUP_NAME + " TEXT NOT NULL, " + COL_EST_ORGANISATEUR + " INTEGER, "
            + COL_USER_ID + " INTEGER);";//, " + "FOREIGN KEY(" + COL_USER_ID + ") REFERENCES " + COL_ID + "(ID) );";*/

    private static final String CREATE_TABLE_GROUPES = "CREATE TABLE " + TABLE_GROUPE + " ("
            + COL_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_GROUP_NAME + " TEXT NOT NULL, "
            + COL_EST_ORGANISATEUR + " INTEGER, "
            + COL_ID_FIREBASE + " TEXT, "
            + COL_USER_ID + " INTEGER, FOREIGN KEY(" + COL_USER_ID + ") REFERENCES " + TABLE_USERS + "(id) );";

    private static final String CREATE_TABLE_PREFERENCES = "CREATE TABLE " + TABLE_PREFERENCE + " ("
            + COL_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_PREFERENCE + " TEXT NOT NULL, "
            + COL_USER_ID + " INTEGER, FOREIGN KEY(" + COL_USER_ID + ") REFERENCES " + TABLE_USERS + "(id) );";

    private static final String CREATE_TABLE_DISPONIBILITES  = "CREATE TABLE " + TABLE_DISPONIBILITITE  + " ("
            + COL_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_DISPONIBILITE_DATE + " DATE," + COL_DISPONIBILITE_HEURE_DEBUT + " TIME,"
            + COL_DISPONIBILITE_HEURE_FIN + " TIME," +
            COL_USER_ID + " INTEGER, FOREIGN KEY(" + COL_USER_ID + ") REFERENCES " + TABLE_USERS + "(id) );";

    private static final String CREATE_TABLE_EVENEMENTS  = "CREATE TABLE " + TABLE_EVENEMENT  + " ("
            + COL_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_GROUP_NAME + " TEXT, "
            + COL_EVENEMENT_ID + " INTEGER, "
            + COL_EVENEMENT_NAME + " TEXT);";

    private static final String CREATE_TABLE_LIEUX  = "CREATE TABLE " + TABLE_LIEU  + " ("
            + COL_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TYPE + " TEXT, "
            + COL_NOM_LIEU + " TEXT," + COL_POSITION_X + " REAL," + COL_POSITION_Y + " REAL,"
            + COL_EVENEMENT_ID + " INTEGER, FOREIGN KEY(" + COL_EVENEMENT_ID + ") REFERENCES " + TABLE_EVENEMENT + "(id) );";

    private static final String CREATE_TABLE_LIEU_CHOISI  = "CREATE TABLE " + TABLE_LIEU_CHOISI  + " ("
            + COL_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_DESCRIPTION + " TEXT, "
            + COL_POSITION_X + " REAL," + COL_POSITION_Y + " REAL," + COL_PHOTO + " TEXT,"
            + COL_EVENEMENT_ID + " INTEGER, FOREIGN KEY(" + COL_EVENEMENT_ID + ") REFERENCES " + TABLE_EVENEMENT + "(id) );";

    private static final String CREATE_TABLE_LOCALISATIONS  = "CREATE TABLE " + TABLE_LOCALISATION  + " ("
            + COL_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_POSITION_X + " REAL," + COL_POSITION_Y + " REAL," + COL_USER_ID + " INTEGER);";

    private static final String CREATE_TABLE_VOTES  = "CREATE TABLE " + TABLE_VOTE  + " ("
            + COL_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_USER_ID + " INTEGER, "
            + COL_EVENEMENT_ID + " INTEGER," + COL_NOM_LIEU + " TEXT," + COL_DATE + " DATE);";

    private static final String CREATE_TABLE_INDEX  = "CREATE TABLE " + TABLE_INDEX  + " ("
            + COL_KEY + " INTEGER PRIMARY KEY, " + COL_USER_ID + " INTEGER, "
            + COL_EVENEMENT_ID + " INTEGER);";




    public MaBaseSQLite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée les tables à partir de la requête écrite dans la variable CREATE_X
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CURRENT_USER);

        Log.d(TAG, "onCreate: Creation de la table user réussie");
        db.execSQL(CREATE_TABLE_GROUPES);
        Log.d(TAG, "onCreate: Creation de la table groupe réussie");
        db.execSQL(CREATE_TABLE_DISPONIBILITES);
        Log.d(TAG, "onCreate: Creation de la table disponibilités réussie");
        db.execSQL(CREATE_TABLE_EVENEMENTS);
        Log.d(TAG, "onCreate: Creation de la table evenement réussie");
        db.execSQL(CREATE_TABLE_LIEU_CHOISI);
        Log.d(TAG, "onCreate: Creation de la table lieu_choisi réussie");
        db.execSQL(CREATE_TABLE_LIEUX);
        Log.d(TAG, "onCreate: Creation de la table lieux réussie");
        db.execSQL(CREATE_TABLE_LOCALISATIONS);
        Log.d(TAG, "onCreate: Creation de la table localisations réussie");
        db.execSQL(CREATE_TABLE_PREFERENCES);
        Log.d(TAG, "onCreate: Creation de la table preferences réussie");
        db.execSQL(CREATE_TABLE_VOTES);
        Log.d(TAG, "onCreate: Creation de la table votes réussie");
        db.execSQL(CREATE_TABLE_INDEX);
        Log.d(TAG, "onCreate: Creation de la table index réussie");
        //Initialisation de la table Index
        ContentValues values = new ContentValues();
        values.put(COL_KEY, 1);
        values.put(COL_USER_ID, 0);
        values.put(COL_EVENEMENT_ID, 0);
        //on insère l'objet dans la BDD via le ContentValues

        db.insert(TABLE_INDEX, null, values);
        //database_.execSQL("INSERT INTO table_groupe(est_organisateur,user_id,nom_du_groupe, id) VALUES (1,1,'Teiouiug', 90)");
        Log.d("GroupeBDD", "Creation de l'index ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On réinitialise les Tables pour les mettres à jour
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENT_USER + ";");
        Log.d(TAG, "onUpgrade: Creation de la table user réussie");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPE + ";");
        Log.d(TAG, "onUpgrade: Creation de la table user réussie");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIEU_CHOISI + ";");
        Log.d(TAG, "onUpgrade: Creation de la table user réussie");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIEU + ";");
        Log.d(TAG, "onUpgrade: Creation de la table user réussie");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOTE + ";");
        Log.d(TAG, "onUpgrade: Creation de la table user réussie");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCALISATION + ";");
        Log.d(TAG, "onUpgrade: Creation de la table user réussie");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFERENCE + ";");
        Log.d(TAG, "onUpgrade: Creation de la table user réussie");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISPONIBILITITE + ";");
        Log.d(TAG, "onUpgrade: Creation de la table user réussie");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENEMENT + ";");
        Log.d(TAG, "onUpgrade: Creation de la table user réussie");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDEX + ";");
        Log.d(TAG, "onUpgrade: Creation de la table index réussie");

        onCreate(db);
    }

}