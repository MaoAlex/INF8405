package com.example.alexmao.tp2final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MaBaseSQLite db;
    final public String DEBUG_TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Action sur le clic du bouton login

        final Button loginButton = (Button) findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Action sur le clic du bouton register
        final Button registerButton = (Button) findViewById(R.id.button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        //Création d'une instance de ma classe usersBDD
        UsersBDD userBDD = new UsersBDD(this);
        GroupeBDD groupeBDD = new GroupeBDD(this);
        DisponibiliteBDD disponibiliteBDD = new DisponibiliteBDD(this);
        EvenementBDD evenementBDD = new EvenementBDD(this);
        LieuBDD lieuBDD = new LieuBDD(this);
        LieuChoisiBDD lieuChoisiBDD = new LieuChoisiBDD(this);
        LocalisationBDD localisationBDD = new LocalisationBDD(this);
        PreferenceBDD preferenceBDD = new PreferenceBDD(this);
        VoteBDD voteBDD = new VoteBDD(this);

        //userBDD.maBaseSQLite_.onUpgrade(userBDD.getDatabase(), VER);

        //Création d'un User
        User user = new User("Jean", "Paul", "jean.paul@gmail.com", null, true, null, null  );
        User user2 = new User("Ah", "DA", "daz@gmail.com", null, true, null, null  );
        User user3 = new User("B", "OK", "doh@gmail.com", null, true, null, null  );
        User user4 = new User("C", "CETA", "oij@gmail.com", null, true, null, null  );

        //On ouvre la base de données pour écrire dedans
        userBDD.open();
        groupeBDD.open();
        disponibiliteBDD.open();
        evenementBDD.open();
        lieuBDD.open();
        lieuChoisiBDD.open();
        localisationBDD.open();
        preferenceBDD.open();
        voteBDD.open();
        //On insère le livre que l'on vient de créer
        long a = userBDD.insertUser(user);
        User user1 = new User("Ah", "a", "jean.paul@gmail.com", null, true, null, null  );
        userBDD.insertUser(user1);
        userBDD.insertUser(user2);
        userBDD.insertUser(user3);
        userBDD.insertUser(user4);
        //userBDD.affichageUsers();
        //userBDD.maBaseSQLite_.onCreate(userBDD.database_);
        //Pour vérifier que l'on a bien créé notre utilisateur dans la BDD
        //on extrait le livre de la BDD grâce au titre du livre que l'on a créé précédemment
        User userFromBdd = userBDD.getUserWithNom("Jean");
        groupeBDD.insertInGroup("equipe1!", userFromBdd.getId(), true);

            Log.d(DEBUG_TAG, "valeur de l'id de l'utilisateur " + userFromBdd.getNom() + " : " + userFromBdd.getId() + " et la valeur de a : " + a);
        groupeBDD.affichageGroupes();
        //userBDD.updateUser(user.getId(), );
        Log.d(DEBUG_TAG, "affichage des elements");

        Groupe GroupeFromBdd = groupeBDD.getGroupeBDD("equipe1");
        ArrayList<Lieu> l = lieuBDD.getlieuBDD(1);
        //Si un utilisateur est retourné (donc si le utilisateur à bien été ajouté à la BDD)
        if( userFromBdd != null){
            //On affiche les infos de l'utilisateur dans un Toast
            Toast.makeText(this, userFromBdd.getId() + "nom : " + userFromBdd.getNom().toString(), Toast.LENGTH_LONG).show();
            //On modifie le nom de l'utilisateur
            userFromBdd.setNom("PlusDeNom");
            //Puis on met à jour la BDD
            userBDD.updateUser(userFromBdd.getId(), userFromBdd);
        }
        if( GroupeFromBdd != null){
            //On affiche les infos de l'utilisateur dans un Toast
            Toast.makeText(this, " Nom du groupe : " + GroupeFromBdd.getNomGroupe().toString() + " Utilisateur : " + GroupeFromBdd.getUsers().isEmpty(), Toast.LENGTH_LONG).show();

        }
        //On extrait l'utilisateur de la BDD grâce au nouveau nom
        userFromBdd = userBDD.getUserWithNom("PlusDeNom");
        //S'il existe un utilisateur possédant ce nom dans la BDD
        if(userFromBdd != null){
            //On affiche les nouvelles informations du user pour vérifier que le nom du user a bien été mis à jour
            Toast.makeText(this, userFromBdd.getId() + "nom : " +userFromBdd.getNom().toString(), Toast.LENGTH_LONG).show();
            //on supprime l'utlisateur de la BDD grâce à son ID
            //userBDD.removeUserWithID(userFromBdd.getId());
        }

        //On essaye d'extraire de nouveau l'utilisateur de la BDD toujours grâce à son nouveau nom
        userFromBdd = userBDD.getUserWithNom("PlusDeNom");
        //Si aucun utilisateur n'est retourné
        if(userFromBdd == null){
            //On affiche un message indiquant que l'utilisateur n'existe pas dans la BDD
            Toast.makeText(this, "Cet utilisateur n'existe pas dans la BDD", Toast.LENGTH_LONG).show();
        }
        //Si l'utilisateur existe (mais normalement il ne devrait pas)
        else{
            //on affiche un message indiquant que l'utilisateur existe dans la BDD
            Toast.makeText(this, "Cet utilisateur existe dans la BDD", Toast.LENGTH_LONG).show();
        }
        //groupeBDD.affichageGroupes();
        //lieuChoisiBDD.insertLieuChoisi(new LieuChoisi(null, "fzdazda", new Localisation(14f, 5f), 1));
        //lieuChoisiBDD.affichageLieuChoisi();
        //groupeBDD.removeUserFromGroupByID(0);
       // groupeBDD.getGroupeBDD("T1");
        userBDD.close();
        groupeBDD.close();
        disponibiliteBDD.close();
        evenementBDD.close();
        lieuBDD.close();

        lieuChoisiBDD.close();
        localisationBDD.close();
        preferenceBDD.close();
        voteBDD.close();
    }



}
