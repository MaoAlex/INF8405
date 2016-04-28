package com.example.alexmao.projetfinal.Activites;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.FromClassAppToEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MyLocalEventEBDD;
import com.example.alexmao.projetfinal.BDDExterne.NotificationBDD;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDExterne.Sender;
import com.example.alexmao.projetfinal.BDDInterne.ConversationBDD;
import com.example.alexmao.projetfinal.BDDInterne.EvenementBDD;
import com.example.alexmao.projetfinal.BDDInterne.GroupeBDD;
import com.example.alexmao.projetfinal.BDDInterne.UtilisateurBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Conversation;
import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.Groupe;
import com.example.alexmao.projetfinal.classeApp.InvitationEvenement;
import com.example.alexmao.projetfinal.classeApp.Message;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class CreerEvenement extends AppCompatActivity {

    private EditText nomVue;
    private EditText lieuVue;
    private EditText dateVue;
    private EditText sportVue;
    private EditText maxParticipantsVue;
    private Button boutonCreer;
    private LatLng lieuChoisi;
    private String sportChoisi;

    //Variable pour récupérer et stocker la date
    private DatePicker datePicker;
    private int day;
    private int month;
    private int year;
    private int hours;
    private int minutes;
    private Utilisateur utilisateurConnecte;
    //BD Externe
    private static RemoteBD remoteBD;

    //BDD Interne
    private EvenementBDD evenementBDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_evenement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Mise en place de la flèche pour le retour en arrière
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        evenementBDD = new EvenementBDD(this);
        evenementBDD.open();
        evenementBDD.affichageEvenements();
        evenementBDD.close();
        nomVue = (EditText) findViewById(R.id.creer_evt_nomEvement);
        lieuVue = (EditText) findViewById(R.id.creer_evt_lieu);
        sportVue = (EditText) findViewById(R.id.creer_evt_sport);
        dateVue = (EditText) findViewById(R.id.creer_evt_dateEvenement);
        maxParticipantsVue = (EditText) findViewById(R.id.creer_evt_maxParticipants);
        boutonCreer = (Button) findViewById(R.id.creer_evt_bouton);

        dateVue.setInputType(InputType.TYPE_NULL);
        dateVue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                affichageChoixDate();
            }
        });

        sportVue.setInputType(InputType.TYPE_NULL);
        sportVue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                affichageChoixSport();
            }
        });

        boutonCreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });
        boutonCreer.setText("Choisir le lieu");
        remoteBD = new FireBaseBD(this);
    }

    private void affichageChoixSport() {
        Intent intent=new Intent(CreerEvenement.this,ChoixSport.class);
        intent.putExtra("estEvenement", true);
        startActivityForResult(intent, ChoixSport.ASK_SPORT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ChoisirLieu.ASK_LIEU)
        {
            if(resultCode == RESULT_OK) {
                lieuChoisi = (LatLng) data.getParcelableExtra("lieu");
                Log.d("Lieu choisi", "Lat : " + String.valueOf(lieuChoisi.latitude) + ", Lng : " + String.valueOf(lieuChoisi.longitude));
                boutonCreer.setText("Créer l'événement");
            } else {
                Toast.makeText(this, "Vous devez choisir un lieu", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(CreerEvenement.this,ChoisirLieu.class);
                startActivityForResult(intent, ChoisirLieu.ASK_LIEU);
            }
        }
        if(requestCode==ChoixSport.ASK_SPORT)
        {
            if(resultCode == RESULT_OK) {

                ArrayList<String> listeSports = data.getStringArrayListExtra("sports");
                if(listeSports.size() == 1) {
                    sportChoisi = listeSports.get(0);
                }
                Log.d("Sport", sportChoisi);
                sportVue.setText(sportChoisi);
            } else {
                Toast.makeText(this, "Vous devez choisir un sport", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(CreerEvenement.this,ChoixSport.class);
                intent.putExtra("estEvenement", true);
                startActivityForResult(intent, ChoixSport.ASK_SPORT);
            }
        }
    }

    private void onButtonClick() {
        if(lieuChoisi == null) {
            Intent intent=new Intent(CreerEvenement.this,ChoisirLieu.class);
            startActivityForResult(intent, ChoisirLieu.ASK_LIEU);
            return;
        }
        if(sportChoisi == null) {
            Intent intent=new Intent(CreerEvenement.this,ChoixSport.class);
            intent.putExtra("estEvenement", true);
            startActivityForResult(intent, ChoixSport.ASK_SPORT);
            return;
        }
        // TODO : Créer l'événement / vérifier les champs ...
        UtilisateurBDD utilisateurBDD = new UtilisateurBDD(this);
        utilisateurBDD.open();
        utilisateurConnecte = utilisateurBDD.obtenirProfil();

        String nom = nomVue.getText().toString();
        String lieu = lieuVue.getText().toString();
        int maxParticipants = Integer.parseInt(maxParticipantsVue.getText().toString());
        GregorianCalendar date = new GregorianCalendar(year, month, day, hours, minutes, 0);


        Evenement evenement = new Evenement();
        evenement.setNomEvenement(nom);
        evenement.setLieu(lieu);
        evenement.setSport(sportChoisi);
        evenement.setLatitude(lieuChoisi.latitude);
        evenement.setLongitude(lieuChoisi.longitude);
        evenement.setNbreMaxParticipants(maxParticipants);
        evenement.setDate(date.getTimeInMillis());
        ArrayList<Utilisateur> listUtilisateur = new ArrayList<>();
        evenement.setOrganisateur(utilisateurConnecte);

        Groupe groupe = new Groupe();
        listUtilisateur.add(utilisateurConnecte);
        groupe.setListeMembre(listUtilisateur);
        //Données test à modifier
        GroupeBDD groupeBDD = new GroupeBDD(this);
        groupeBDD.open();
        groupe.setEvenement(evenement);
        Conversation conversation = new Conversation();
        conversation.setNomConversation(evenement.getNomEvenement());
        conversation.setListeMessage(new ArrayList<Message>());
        evenement.setPhoto(null);
        groupeBDD.affichageGroupe();


        evenement.setVisibilite("public");
//        MyLocalEventEBDD myLocalEventEBDD = FromClassAppToEBDD.translateEvenement(evenement, null);
//        String idFirebaseEve = remoteBD.addEvent(myLocalEventEBDD);
        //addOnEBDD(evenement);
        //Envoie du groupe, de l'evenement et de la conversation sur la BD externe
        Sender.addGroupDiscussionEvent(groupe, evenement, conversation, remoteBD);
        ConversationBDD conversationBDD = new ConversationBDD(this);
        conversationBDD.open();

        long idConversation = conversationBDD.insererConversation(conversation);
        Log.d("CreationEvenement", "l'id de la coenversation est : "+ groupe.getConversation());

        long idGroupe = groupeBDD.insererGroupe(groupe);

        groupe.setIdBDD(idGroupe);
        evenement.setGroupeAssocie(groupe);
        evenementBDD.open();
        long idEvenement = evenementBDD.insererEvenement(evenement);
        evenementBDD.affichageEvenements();
        evenement.setIdBDD(idEvenement);


        groupeBDD.affichageGroupe();
        evenementBDD.affichageEvenements();
        conversationBDD.affichageConversation();
        conversationBDD.close();

        evenementBDD.close();
        groupeBDD.close();
        utilisateurBDD.close();
        // TODO : Event is ready !
        Log.d("CreerEvenement","Bouton cliqué");
    }

    //méthode permettant l'affichage spécifique du choix de la date de naissance
    private void affichageChoixDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePickerListener, year, month, day);
        datePickerDialog.updateDate(2016, 04, 21);
        datePickerDialog.show();
    }

    //Méthode permettant l'écriture sur le champ correspondant à la date choisie
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            dateVue.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            affichageChoixHeure();
        }
    };

    //Méthode permettant l'écriture sur le champ correspondant à la date choisie
    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour,
                              int selectedMinute) {
            String dateAct = dateVue.getText().toString();
            if(selectedHour<10 && selectedMinute<10) {
                dateAct = dateAct + " 0" + String.valueOf(selectedHour) + ":0" + String.valueOf(selectedMinute);
            } else if(selectedHour<10) {
                dateAct = dateAct + " 0" + String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute);
            } else if(selectedMinute<10) {
                dateAct = dateAct + " " + String.valueOf(selectedHour) + ":0" + String.valueOf(selectedMinute);
            } else {
                dateAct = dateAct + " " + String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute);
            }

            dateVue.setText(dateAct);
            GregorianCalendar test = new GregorianCalendar(year, month, day, selectedHour, selectedMinute);
            hours = selectedHour;
            minutes = selectedMinute;

        }
    };

    private void affichageChoixHeure() {
        //méthode permettant l'affichage spécifique du choix de l'heure
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, timePickerListener, hours, minutes, true);
            timePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Gestion du clique sur le retour en arrière
        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    //Envoie un événement sur la EBDD
    private void addOnEBDD(Evenement evenement) {
        MyLocalEventEBDD eventEBDD = FromClassAppToEBDD.translateEvenement(evenement, null);
        String eventID = remoteBD.addEvent(eventEBDD);
        eventEBDD.setDataBaseId(eventID);
        evenement.setIdFirebase(eventID);
        evenement.setVisibilite("public");
        //TODO: Alex vérifie que la visibilité public correnpond bien à un événement public
        if (evenement.getVisibilite().equals(null) || evenement.getVisibilite().equals("public"))
            addOnEBDDPublicPart(evenement);
    }

    //S'il l'événement est public on l'ajoute églement dans la partie temporaire
    private void addOnEBDDPublicPart(Evenement evenement) {
        remoteBD.addEventToTemporary(evenement.getIdFirebase());
    }

    private void sendInvitation(InvitationEvenement invitationEvenement) {
        NotificationBDD notificationBDD = new NotificationBDD();
        notificationBDD.setDate(invitationEvenement.getDate());
        notificationBDD.setAskerID(invitationEvenement.getExpediteur().getIdFirebase());
        notificationBDD.setDestID(invitationEvenement.getInvite().getIdFirebase());
        Map<String, String> map = new HashMap<>();
        notificationBDD.setParams(map);

        remoteBD.addNotificationToUser(notificationBDD.getDestID(), notificationBDD);
    }
}
