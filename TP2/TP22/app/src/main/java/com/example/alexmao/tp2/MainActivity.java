package com.example.alexmao.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
        UsersBDD UserBDD = new UsersBDD(this);
        //Création d'un User
        User user = new User("Jean", "Paul");

        //On ouvre la base de données pour écrire dedans
        UserBDD.open();
        //On insère le livre que l'on vient de créer
        UserBDD.insertUser(user);

        //Pour vérifier que l'on a bien créé notre utilisateur dans la BDD
        //on extrait le livre de la BDD grâce au titre du livre que l'on a créé précédemment
        User userFromBdd = UserBDD.getUserWithNom(user.getNom_());
        //Si un utilisateur est retourné (donc si le utilisateur à bien été ajouté à la BDD)
        if( userFromBdd != null){
            //On affiche les infos de l'utilisateur dans un Toast
            Toast.makeText(this, userFromBdd.toString(), Toast.LENGTH_LONG).show();
            //On modifie le nom de l'utilisateur
            userFromBdd.setNom_("PlusDeNom");
            //Puis on met à jour la BDD
            UserBDD.updateUser(userFromBdd.getId(), userFromBdd);
        }

        //On extrait l'utilisateur de la BDD grâce au nouveau nom
        userFromBdd = UserBDD.getUserWithNom("PlusDeNom");
        //S'il existe un utilisateur possédant ce nom dans la BDD
        if(userFromBdd != null){
            //On affiche les nouvelles informations du user pour vérifier que le nom du user a bien été mis à jour
            Toast.makeText(this, userFromBdd.toString(), Toast.LENGTH_LONG).show();
            //on supprime l'utlisateur de la BDD grâce à son ID
            UserBDD.removeUserWithID(userFromBdd.getId());
        }

        //On essaye d'extraire de nouveau l'utilisateur de la BDD toujours grâce à son nouveau nom
        userFromBdd = UserBDD.getUserWithNom("PlusDeNom");
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

        UserBDD.close();

    }



}
