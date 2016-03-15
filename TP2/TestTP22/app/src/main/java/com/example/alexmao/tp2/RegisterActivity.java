package com.example.alexmao.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity{
    final String EXTRA_LOGIN = "user_login";
    final String EXTRA_PASSWORD = "user_password";
    final String EXTRA_GROUP = "user_group";
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
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, RegisterInformation.class);
                intent.putExtra(EXTRA_LOGIN, login.getText().toString());
                intent.putExtra(EXTRA_PASSWORD, pass.getText().toString());
                intent.putExtra(EXTRA_GROUP, group.getText().toString());

                startActivity(intent);
            }
        });


    }

}

