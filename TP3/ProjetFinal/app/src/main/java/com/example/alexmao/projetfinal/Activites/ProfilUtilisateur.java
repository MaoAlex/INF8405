package com.example.alexmao.projetfinal.Activites;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexmao.projetfinal.BDDInterne.UtilisateurBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;
import com.example.alexmao.projetfinal.custom.CustomActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ProfilUtilisateur extends CustomActivity {

    private Utilisateur utilisateur;

    //Element de la page
    ImageView vPhotoProfil;
    TextView vNom;
    TextView vEmail;
    TextView vDate;
    TextView vSports;

    //BDD interne
    private UtilisateurBDD utilisateurBDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_utilisateur);

        //chargerUtilisateur();

//        initialiserProfil();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Mise en place de la flèche pour le retour en arrière
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Mise en place de l'image qui va se réduire
//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setTitle("Evenement");
        utilisateurBDD = new UtilisateurBDD(this);
        utilisateurBDD.open();
        utilisateur = utilisateurBDD.obtenirProfil();
        utilisateurBDD.close();

        //TODO modifier la photo de profil
        //if(utilisateur.getPhoto()!=null)
            //vPhotoProfil.setImageBitmap();
        initialiserProfil();
    }

    private String construireNom() {
        String identite = new String(utilisateur.getPrenom());
        identite = identite.concat(" ");
        identite = identite.concat(utilisateur.getNom());
        return identite;
    }

    private String construireDDN() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(utilisateur.getDateNaissance());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dateNaissance = new String(String.valueOf(day));
        dateNaissance = dateNaissance.concat("/");
        dateNaissance = dateNaissance.concat(String.valueOf(month));
        dateNaissance = dateNaissance.concat("/");
        dateNaissance = dateNaissance.concat(String.valueOf(year));

        return dateNaissance;
    }

    private String construireSports() {
        List<String> listeSports = utilisateur.getSports();
        if(listeSports.size() > 0) {
            String sportsStr = listeSports.get(0);
            for(int i=1; i<listeSports.size(); i++) {
                sportsStr = sportsStr.concat(", ");
                sportsStr = sportsStr.concat(listeSports.get(i));
            }

            return sportsStr;
        }
        else {
            return "";
        }
    }
    private void initialiserProfil() {
        // Récupération des TextViews
        TextView identiteVue = (TextView) findViewById(R.id.nom_profil);
        TextView emailVue = (TextView) findViewById(R.id.email_profil);
        TextView ddnVue = (TextView) findViewById(R.id.ddn_profil);
        TextView sportsVue = (TextView) findViewById(R.id.sports_profil);

        // Initialisation des champs
        identiteVue.setText(construireNom());
        emailVue.setText(utilisateur.getMail());
        ddnVue.setText(construireDDN());
        sportsVue.setText(construireSports());
    }

    private void chargerUtilisateur() {
        this.utilisateur = new Utilisateur();
        GregorianCalendar test = new GregorianCalendar(2000, 6, 21);
        utilisateur.setNom("Golo");
        utilisateur.setPrenom("Henri");
        utilisateur.setDateNaissance(test.getTimeInMillis());
        utilisateur.setMail("henri.golo@test.com");
        List<String> sports = new ArrayList<String>();
        sports.add("football");
        sports.add("tennis");
        utilisateur.setSports(sports);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        //Gestion du clique sur le retour en arrière
        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
