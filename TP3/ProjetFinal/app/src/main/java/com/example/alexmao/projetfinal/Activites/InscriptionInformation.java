package com.example.alexmao.projetfinal.Activites;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.LocalUserProfilEBDD;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDExterne.UtilisateurProfilEBDD;
import com.example.alexmao.projetfinal.BDDInterne.UtilisateurBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;
import com.example.alexmao.projetfinal.custom.CustomActivity;
import com.example.alexmao.projetfinal.utils.Utils;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * The Class Inscription is the Activity class that shows user registration screen
 * that allows user to activity_inscription itself on Parse server for this Chat app.
 * La classe Inscription Information va permettre de recueillir les informations sur l'utilisateur
 * Elle s'occupera aussi une fois termine d'envoyer les informations sur le serveur externe
 */
public class InscriptionInformation extends CustomActivity
{
    //les différents éléments de la Vue
    private EditText nomVue;
    private EditText prenomVue;
    private EditText dateNaissanceVue;
    private ImageView photoProfilVue;

    private static int RESULT_LOAD_IMAGE = 1;

    //Variable pour récupérer et stocker la date
    private DatePicker datePicker;
    private int day;
    private int month;
    private int year;
    private long date;
    //Variable pour stocker la photo et l'afficher
    private Bitmap bp;

    //base de données externe
    private RemoteBD remoteBD;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_information);
        //Connection des différents éléments
        remoteBD = new FireBaseBD(this);
        setTouchNClick(R.id.btnReg);
        nomVue = (EditText) findViewById(R.id.nom);
        prenomVue = (EditText) findViewById(R.id.prenom);
        dateNaissanceVue = (EditText) findViewById(R.id.dateNaissance);
        photoProfilVue = (ImageView) findViewById(R.id.photoProfil);

        //On met en place le choix de la date de naissance sur le champ date de naissance
        dateNaissanceVue.setInputType(InputType.TYPE_NULL);
        dateNaissanceVue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                affichageChoixDate();
            }
        });

        //On met en place la possibilité de prendre une photo ou d'en choisir une sur le clic de l'image de l'image de profil
        photoProfilVue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choixPhotoProfil();
            }
        });
    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        //On récupére les différents éléments entrés par l'utilisateur
        String nom = nomVue.getText().toString();
        String prenom = prenomVue.getText().toString();
        String dateAffiche = dateNaissanceVue.getText().toString();
        //On vérifie que l'utilisateur a bien remplis les champs
        if (nom.length() == 0 || prenom.length() == 0 || dateAffiche.length() == 0)
        {
            Utils.showDialog(this, R.string.err_fields_empty);
            return;
        }
        //Affichage du processus d'attente
        final ProgressDialog dia = ProgressDialog.show(this, null,
                getString(R.string.alert_wait));
        //Création de la variable utilisateur qui va être envoyé au serveur
        final UtilisateurProfilEBDD user = new UtilisateurProfilEBDD();
        Utilisateur utilisateur = new Utilisateur();
        Intent intent = getIntent();
        String email = intent.getStringExtra("mail");
        utilisateur.setMail(email);
        utilisateur.setPrenom(prenom);
        utilisateur.setNom(nom);
        utilisateur.setDateNaissance(date);
        user.setMailAdr(email);
        user.setFirstName(prenom);
        user.setLastName(nom);
        user.setDateBirth(date);
        //Ajout de l'utilisateur dans la BDD externe
        final String idFirebase = remoteBD.addUserProfil(user);
        Log.d("Firebase", "Id Firebse : " + idFirebase);

        //Ajout du mot de passe associé à cet utilisateur et son adresse mail
        String mdp = intent.getStringExtra("mdp");
        remoteBD.addMdpToUser(email, mdp);
        utilisateur.setIdFirebase(idFirebase);

        Log.d("Firebase", "Id Firebse : " + utilisateur.getIdFirebase());

        UtilisateurBDD utilisateurBDD = new UtilisateurBDD(this);
        utilisateurBDD.open();
        long idUtilisateur = utilisateurBDD.insererUtilisateur(utilisateur);
        utilisateur.setIdBDD(idUtilisateur);
        utilisateurBDD.connexion(utilisateur);
        Log.d("Id Utilisateur", "Id utilisateur : " + utilisateur.getIdBDD());

        utilisateurBDD.affichageUtilisateurs();
        utilisateurBDD.affichageUtilisateurConnecte();
        utilisateurBDD.close();
        //sendToEBDD(utilisateur);
        //Une fois l'envoie des données réussi sur le serveur, on passe à la page d'accueil
        startActivity(new Intent(InscriptionInformation.this, Accueil.class));
        setResult(RESULT_OK);
        //On termine l'activité
        finish();

    }

    //méthode permettant l'affichage spécifique du choix de la date de naissance
    private void affichageChoixDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePickerListener, year, month, day);
        datePickerDialog.updateDate(2016,02,21);
        datePickerDialog.show();
    }

    //Méthode permettant l'écriture sur le champ correspondant à la date choisie
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
//            dateNaissanceVue.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
//                    + selectedYear);
            GregorianCalendar calendar = new GregorianCalendar(selectedYear, selectedMonth, selectedDay);
            date = calendar.getTimeInMillis();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            dateNaissanceVue.setText(day + " / " + (month + 1) + " / "
                    + year);
            Log.d("Information inscription", "valeur de la date de naissance : " + date);
        }
    };

    //Méthode appelée après le choix d'un photo de profil
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //vérification de la non-nullité de la photo récupérée
        if(data!=null) {
            super.onActivityResult(requestCode, resultCode, data);
            if (data.getExtras() != null) {
                //On transforme la photo en bitmap
                bp = (Bitmap) data.getExtras().get("data");

                if (bp != null) {
                   //A compléter, ajout de la photo au profil
                    //on remplace l'image de profil par la photo
                    photoProfilVue.setImageBitmap(bp);
                } else {
                    //Cas où l'utilisateur a choisi une photo de sa gallerie photo
                    Uri u = data.getData();
                    //on remplace l'image de profil par la photo
                    photoProfilVue.setImageURI(u);
                    //A compléter, ajout de la photo au profil et envoie ua serveur

                }
            }
        }
    }

    //Méthode affichant le choix de prendre une photo ou d'aller dans la gallerie photo
    private void choixPhotoProfil(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Choisissez une photo de profil :");
        builder.setCancelable(true);
        builder.setPositiveButton("Prendre une photo", new TakePictureOnClickListener());
        builder.setNegativeButton("Choisir une photo", new ChoosePictureOnClickListener());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //méthode permettant de lancer la gallerie photo
    private final class ChoosePictureOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            /*Toast.makeText(getApplicationContext(), "Bon courage",
                    Toast.LENGTH_LONG).show();*/
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //ligne suivante inutile pour le moment
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, RESULT_LOAD_IMAGE);
        }
    }

    //méthode permettant de lancer l'appareil photo
    private final class TakePictureOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            //ligne suivante inutile pour le moment
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, 0);

        }
    }

    private void sendToEBDD(Utilisateur utilisateur) {
        LocalUserProfilEBDD profilEBDD = new LocalUserProfilEBDD(utilisateur.getPrenom(),
                utilisateur.getNom(), utilisateur.getMail());
        //TODO gérer la photo
        //Bitmap bitmap = la photo en bitmap
//        Picture picture = new Picture(bitmap);
//        profilEBDD.setPicture(new Picture());
        profilEBDD.setDateBirth(utilisateur.getDateNaissance());
        profilEBDD.setSports(utilisateur.getSports());
        profilEBDD.setListeInteretsID(utilisateur.getListeInteretsID());
        profilEBDD.setListeParticipationsID(utilisateur.getListeParticipationsID());
        profilEBDD.setListeConnexion(utilisateur.getListeConnexion());
        String idFirebase = remoteBD.addUserProfil(profilEBDD);
        profilEBDD.setDataBaseId(idFirebase);
        //TODO: insert in data base
        Log.d("DOnnes", "On stocke l'utilisateur dans la BDD interne");


    }
}
