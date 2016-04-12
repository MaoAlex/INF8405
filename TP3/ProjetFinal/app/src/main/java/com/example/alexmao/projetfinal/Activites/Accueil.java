package com.example.alexmao.projetfinal.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.alexmao.projetfinal.BDDInterne.GroupeBDD;
import com.example.alexmao.projetfinal.BDDInterne.UtilisateurBDD;
import com.example.alexmao.projetfinal.R;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        UtilisateurBDD utilisateurBDD = new UtilisateurBDD(this);
        utilisateurBDD.open();
        GroupeBDD groupeBDD = new GroupeBDD(this);
        groupeBDD.open();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    //méthode pour la création du menu, dans notre cas les éléments de la tab bar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    //méthode permettant la gestion des actions en fonctions des boutons
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_about:
//                // Comportement du bouton "A Propos"
//                return true;
//            case R.id.menu_help:
//                // Comportement du bouton "Aide"
//                return true;
//            case R.id.menu_refresh:
//                // Comportement du bouton "Rafraichir"
//                return true;
//            case R.id.menu_search:
//                // Comportement du bouton "Recherche"
//                return true;
//            case R.id.menu_settings:
//                // Comportement du bouton "Paramètres"
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

}
