package com.example.alexmao.projetfinal.Activites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ProfilUtilisateur extends FragmentActivity {

    private Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_utilisateur);

        chargerUtilisateur();

        initialiserProfil();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
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

}
