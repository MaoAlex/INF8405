package com.example.alexmao.projetfinal.Activites;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.FromClassAppToEBDD;
import com.example.alexmao.projetfinal.BDDExterne.LocalUserProfilEBDD;
import com.example.alexmao.projetfinal.BDDExterne.Picture;
import com.example.alexmao.projetfinal.BDDExterne.Position;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDExterne.UserParamsEBDD;
import com.example.alexmao.projetfinal.BDDExterne.UtilisateurProfilEBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

/**
 * Created by alexMAO on 24/04/2016.
 */
public class ChoixSport extends AppCompatActivity {
    private RemoteBD remoteBD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_sports);
        //Récupération de la toolbar et mise en place
        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Mise en place de la flèche pour le retour en arrière
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        remoteBD = new FireBaseBD(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Gestion du clique sur le retour en arrière
        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }


    //appeler pour mettre le profil utilisateur à jour
    private void pushModifications(Utilisateur utilisateur) {
        LocalUserProfilEBDD profilEBDD = new LocalUserProfilEBDD();
        FromClassAppToEBDD.translateUtilisateur(utilisateur, profilEBDD, null, null);
        remoteBD.updateUserProfil(utilisateur.getIdFirebase(), profilEBDD);
    }
}
