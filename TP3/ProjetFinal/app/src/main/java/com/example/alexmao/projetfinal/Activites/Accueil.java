package com.example.alexmao.projetfinal.Activites;

import android.app.Activity;
import android.os.Bundle;

import com.example.alexmao.projetfinal.BDDInterne.GroupeBDD;
import com.example.alexmao.projetfinal.BDDInterne.UtilisateurBDD;
import com.example.alexmao.projetfinal.R;

public class Accueil extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        UtilisateurBDD utilisateurBDD = new UtilisateurBDD(this);
        utilisateurBDD.open();
        GroupeBDD groupeBDD = new GroupeBDD(this);
        groupeBDD.open();
    }


}
