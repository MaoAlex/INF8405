package com.example.alexmao.projetfinal.Activites;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.FromClassAppToEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MyLocalEventEBDD;
import com.example.alexmao.projetfinal.BDDExterne.NotificationBDD;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.InvitationEvenement;

import java.util.Date;
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

    //Variable pour récupérer et stocker la date
    private DatePicker datePicker;
    private int day;
    private int month;
    private int year;
    private int hours;
    private int minutes;

    //BD Externe
    private RemoteBD remoteBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_evenement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Mise en place de la flèche pour le retour en arrière
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

        boutonCreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });

        remoteBD = new FireBaseBD(this);
    }

    private void onButtonClick() {
        // TODO : Créer l'événement / vérifier les champs ...

        String nom = nomVue.getText().toString();
        String sport = sportVue.getText().toString();
        String lieu = lieuVue.getText().toString();
        int maxParticipants = Integer.parseInt(maxParticipantsVue.getText().toString());
        GregorianCalendar date = new GregorianCalendar(year, month, day, hours, minutes, 0);

        Evenement evenement = new Evenement();
        evenement.setNomEvenement(nom);
        evenement.setLieu(lieu);
        evenement.setSport(sport);
        evenement.setNbreMaxParticipants(maxParticipants);
        evenement.setDate(date.getTimeInMillis());
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

    private void sendOnEBDD(Evenement evenement) {
        MyLocalEventEBDD eventEBDD = FromClassAppToEBDD.translateEvenement(evenement, null);
        String eventID = remoteBD.addEvent(eventEBDD);
        eventEBDD.setDataBaseId(eventID);
    }

    private void sendInvitation(InvitationEvenement invitationEvenement) {
        NotificationBDD notificationBDD = new NotificationBDD();
        notificationBDD.setDate(invitationEvenement.getDate().getTime());
        notificationBDD.setAskerID(invitationEvenement.getExpediteur().getIdFirebase());
        notificationBDD.setDestID(invitationEvenement.getInvite().getIdFirebase());
        Map<String, String> map = new HashMap<>();
        notificationBDD.setParams(map);

        remoteBD.addNotificationToUser(notificationBDD.getDestID(), notificationBDD);
    }
}
