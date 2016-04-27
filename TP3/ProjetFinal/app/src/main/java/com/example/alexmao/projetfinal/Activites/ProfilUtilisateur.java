package com.example.alexmao.projetfinal.Activites;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.NotificationBDD;
import com.example.alexmao.projetfinal.BDDExterne.NotificationTypes;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDInterne.InvitationConnexionBDD;
import com.example.alexmao.projetfinal.BDDInterne.UtilisateurBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.InvitationConnexion;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;
import com.example.alexmao.projetfinal.custom.CustomActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfilUtilisateur extends CustomActivity {

    private Utilisateur utilisateur;
    private Utilisateur utilisateurConnecte;
    private boolean sonProfil = false;
    private long idUtilisateur;
    //Element de la page
    ImageView vPhotoProfil;
    TextView vNom;
    TextView vEmail;
    TextView vDate;
    TextView vSports;
    Button vConnexion;
    Button vMessage;

    //BDD interne
    private UtilisateurBDD utilisateurBDD;
    InvitationConnexionBDD invitationConnexionBDD;// = new InvitationConnexionBDD(getApplicationContext());



    //BD ecterne
    private RemoteBD remoteBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_utilisateur);
        utilisateurBDD = new UtilisateurBDD(this);
        utilisateurBDD.open();
        invitationConnexionBDD = new InvitationConnexionBDD(this);
        invitationConnexionBDD.open();
        vConnexion = (Button) findViewById(R.id.profil_ajouter_connexion);
        vMessage = (Button) findViewById(R.id.profil_envoyer_message);
        //chargerUtilisateur();
        idUtilisateur = getIntent().getLongExtra("id", 0);
        Log.d("Profil Utilisateur", "id de l'utilisateur : "  + idUtilisateur);

        utilisateurConnecte = utilisateurBDD.obtenirProfil();
//        getIntent().getBooleanExtra("sonProfil", sonProfil);
        sonProfil = idUtilisateur == utilisateurConnecte.getIdBDD();
        //getIntent().getLongExtra("idUtilisateur", idUtilisateur);
        if(sonProfil)
            Log.d("Profil Utilisateur", "Accès à son profil");
        else {
            Log.d("Profil Utilisateur", "Accès au profil d'un autre utilisateur");
            Log.d("Profil Utilisateur", "id de l'utilisateur : "  + idUtilisateur);
        }
//        initialiserProfil();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Mise en place de la flèche pour le retour en arrière
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Mise en place de l'image qui va se réduire
//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setTitle("Evenement");

        if(sonProfil) {
            vConnexion.setVisibility(View.INVISIBLE);
            vMessage.setVisibility(View.INVISIBLE);

            utilisateur = utilisateurBDD.obtenirProfil();

            //TODO modifier la photo de profil
            //if(utilisateur.getPhoto()!=null)
            //vPhotoProfil.setImageBitmap();
        }else {
            utilisateur = utilisateurBDD.obtenirUtilisateurParId(idUtilisateur);
            remoteBD = new FireBaseBD(this);
            vConnexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InvitationConnexion invitationConnexion = new InvitationConnexion();
                    invitationConnexion.setExpediteur(utilisateurConnecte);
                    invitationConnexion.setInvite(utilisateur);
                    GregorianCalendar calendar = new GregorianCalendar();
                    invitationConnexion.setDate(calendar.getTimeInMillis());

                    //Ajout dans la BD Externe
                    sendConnexionInvitation(invitationConnexion, utilisateur.getIdFirebase());
//                    invitationConnexion()
//                    remoteBD.add
                    desactiverConnexion();

                }
            });
        }
        utilisateurBDD.close();
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
        Log.d("Profil Utilisateur", "valeur de la date de naissance : " + utilisateur.getDateNaissance());

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

    //Envoie une invitation connexion
    private void sendConnexionInvitation(InvitationConnexion invitationConnexion, String userID) {
        Map<String,String> params = new HashMap<>();
        params.put("something", "exemple");
        NotificationBDD notificationBDD = new NotificationBDD(NotificationTypes.conctactInvitation,
                params, invitationConnexion.getExpediteur().getIdFirebase(),
                invitationConnexion.getInvite().getIdFirebase());
        notificationBDD.setDate(invitationConnexion.getDate());
        String idFirebase = remoteBD.addNotificationToUser(userID, notificationBDD);
        //Ajout dans la BD interne
        invitationConnexion.setIdFirebase(idFirebase);
        invitationConnexionBDD.insererInvitationConnexion(invitationConnexion);
        invitationConnexionBDD.close();
    }

    private void desactiverConnexion(){
        vConnexion.setText("Demande envoyée");
        vConnexion.setAlpha((float)0.5);
        //vConnexion.getBackground().setAlpha(2);
        vConnexion.setClickable(false);
    }
}
