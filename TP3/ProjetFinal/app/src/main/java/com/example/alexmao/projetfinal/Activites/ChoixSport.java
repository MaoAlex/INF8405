package com.example.alexmao.projetfinal.Activites;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

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

import java.lang.ref.WeakReference;

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

        loadImages();
        remoteBD = new FireBaseBD(this);
    }

    private void loadImages() {
        ImageView artMartial = (ImageView) findViewById(R.id.art_martial);
        WeakReference wArtMartial = new WeakReference(artMartial);
        artMartial.setImageDrawable(getResources().getDrawable(R.drawable.art_martial));
        ImageView athletisme = (ImageView) findViewById(R.id.athletisme);
        athletisme.setImageDrawable(getResources().getDrawable(R.drawable.athletisme));
        ImageView badminton = (ImageView) findViewById(R.id.badminton);
        artMartial.setImageDrawable(getResources().getDrawable(R.drawable.art_martial));
        ImageView baseball = (ImageView) findViewById(R.id.baseball);
        baseball.setImageDrawable(getResources().getDrawable(R.drawable.baseball));
        ImageView basketball = (ImageView) findViewById(R.id.basketball);
        basketball.setImageDrawable(getResources().getDrawable(R.drawable.basketball));
        ImageView boxe = (ImageView) findViewById(R.id.boxe);
        boxe.setImageDrawable(getResources().getDrawable(R.drawable.boxe));
        ImageView fitness = (ImageView) findViewById(R.id.fitness);
        fitness.setImageDrawable(getResources().getDrawable(R.drawable.fitness));
        ImageView football = (ImageView) findViewById(R.id.football);
        football.setImageDrawable(getResources().getDrawable(R.drawable.football));
        ImageView golf = (ImageView) findViewById(R.id.golf);
        golf.setImageDrawable(getResources().getDrawable(R.drawable.golf));
        ImageView hockey = (ImageView) findViewById(R.id.hockey);
        hockey.setImageDrawable(getResources().getDrawable(R.drawable.hockey));
        ImageView natation = (ImageView) findViewById(R.id.natation);
        natation.setImageDrawable(getResources().getDrawable(R.drawable.natation));
        ImageView pingpong = (ImageView) findViewById(R.id.pingpong);
        pingpong.setImageDrawable(getResources().getDrawable(R.drawable.pingpong));
        ImageView rugby = (ImageView) findViewById(R.id.rugby);
        rugby.setImageDrawable(getResources().getDrawable(R.drawable.rugby));
        ImageView tennis = (ImageView) findViewById(R.id.tennis);
        tennis.setImageDrawable(getResources().getDrawable(R.drawable.tennis));
        ImageView voleyball = (ImageView) findViewById(R.id.voleyball);
        voleyball.setImageDrawable(getResources().getDrawable(R.drawable.voleyball));
        ImageView waterpolo = (ImageView) findViewById(R.id.waterpolo);
        waterpolo.setImageDrawable(getResources().getDrawable(R.drawable.waterpolo));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Gestion du clique sur le retour en arrière
        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    private void pushModifications(Utilisateur utilisateur) {
        LocalUserProfilEBDD profilEBDD = new LocalUserProfilEBDD();
        FromClassAppToEBDD.translateUtilisateur(utilisateur, profilEBDD, null, null);
        remoteBD.updateUserProfil(utilisateur.getIdFirebase(), profilEBDD);
    }
}
