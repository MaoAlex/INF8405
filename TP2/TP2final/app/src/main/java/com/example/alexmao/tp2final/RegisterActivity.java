package com.example.alexmao.tp2final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Activité permettant l'inscription d'un utilisateur
 */
public class RegisterActivity extends AppCompatActivity{
    final String EXTRA_MAIL = "user_mail";
    final String EXTRA_PASSWORD = "user_password";
    final String EXTRA_GROUP = "user_group";
    final String EXTRA_NOM = "user_nom";
    final String EXTRA_PRENOM = "user_prenom";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        // Creation du bouton pour passer à la suite de l'inscription
        final Button loginButton = (Button) findViewById(R.id.inscription);
        //recuperation des éléments entrés
        final EditText login = (EditText) findViewById(R.id.user_email);
        final EditText pass = (EditText) findViewById(R.id.user_password);
        final EditText group = (EditText) findViewById(R.id.group_name);
        final EditText nom = (EditText) findViewById(R.id.nom);
        final EditText prenom = (EditText) findViewById(R.id.prenom);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, RegisterInformation.class);
                intent.putExtra(EXTRA_MAIL, login.getText().toString());
                intent.putExtra(EXTRA_PASSWORD, pass.getText().toString());
                intent.putExtra(EXTRA_GROUP, group.getText().toString());
                intent.putExtra(EXTRA_NOM, nom.getText().toString());
                intent.putExtra(EXTRA_PRENOM, prenom.getText().toString());
                startActivity(intent);
            }
        });


    }

}

