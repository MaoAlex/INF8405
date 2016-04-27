package com.example.alexmao.projetfinal.Activites;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.alexmao.projetfinal.BDDExterne.FetchFullDataFromEBDD;
import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.FromEBDDToLocalClassTranslator;
import com.example.alexmao.projetfinal.BDDExterne.LocalUserProfilEBDD;
import com.example.alexmao.projetfinal.BDDExterne.OnFullUserData;
import com.example.alexmao.projetfinal.BDDExterne.OnStringReceived;
import com.example.alexmao.projetfinal.BDDExterne.Picture;
import com.example.alexmao.projetfinal.BDDExterne.Position;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDExterne.UserParamsEBDD;
import com.example.alexmao.projetfinal.BDDInterne.UtilisateurBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;
import com.example.alexmao.projetfinal.custom.CustomActivity;
import com.example.alexmao.projetfinal.utils.Utils;

import java.io.FileOutputStream;

/**
 *
 * La classe authentificaiton est l'activité qui est celle affiché lors du lancement de l'application
 * Elle permet à un utilisateur de s'authentifier s'il possède un compte valide, sinon de s'inscrire
 *
 */
public class Authentification extends CustomActivity
{
    //interface avec la bddExterne
    private RemoteBD remoteBD;

    private static final String TAG = "Authentification";

    //champ pour renter le mail
	private EditText mail;

	// Champ pour rentrer le mot de passe
	private EditText motDePasse;
    private boolean estConnecte = false ;
	private boolean batteryRead = false;
    private static Utilisateur utilisateur;

	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context ctxt, Intent intent) {
			if(batteryRead) {
				return;
			}
			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
			double batteryPct = level/(float)scale;
			String filename = "batteryinfo";
			String string = String.valueOf(batteryPct);
			FileOutputStream outputStream;

			try {
				outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
				outputStream.write(string.getBytes());
				outputStream.close();
				Log.d("Fichier", "Fichier créé");
			} catch (Exception e) {
				e.printStackTrace();
			}
		batteryRead = true;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        UtilisateurBDD utilisateurBDD = new UtilisateurBDD(this);
        utilisateurBDD.open();
        utilisateurBDD.affichageUtilisateurConnecte();
        estConnecte = utilisateurBDD.estConnecte();
        utilisateurBDD.close();
        if(!estConnecte) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.acitivity_authentification);
            //connection de la BDD externe
            remoteBD = new FireBaseBD(this);
            //Mise en forme des clics sur les boutons
            setTouchNClick(R.id.btnLogin);
            setTouchNClick(R.id.btnReg);
            //Connection des champs
            mail = (EditText) findViewById(R.id.user);
            motDePasse = (EditText) findViewById(R.id.motDePasse);
        }else{
            super.onCreate(savedInstanceState);
            Intent intent = new Intent(this, Accueil.class);
            startActivity(intent);
            finish();
        }
    }

    //Méthode gérant le clic sur les différents boutons de cette activité
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
        //Cas du clic sur le bouton Inscription
		if (v.getId() == R.id.btnReg)
		{
//			startActivityForResult(new Intent(this, Inscription.class), 10);
            startActivity(new Intent(this, Inscription.class));
        }
		else
		{//Cas du bouton Authentification

			final String email = mail.getText().toString();
			final String p = motDePasse.getText().toString();
            //On vérifie que les champs ont bien été remplis
            if (email.length() == 0 || p.length() == 0)
			{
				Utils.showDialog(this, R.string.err_fields_empty);
				return;
			}
            //affichage de l'animation d'attente
			final ProgressDialog dia = ProgressDialog.show(this, null,
					getString(R.string.alert_wait));
		    //On va chercher le mot de passe sur le serveur externe
            remoteBD.getMdp(email, new OnStringReceived() {
				@Override
				public void onStringReceived(String s) {
					dia.dismiss();
					//on vérifie que le mot de passe est bien celui associé à ce mail
					if (s != null && s.equals(p)) {
						connexionReussi(email);
						Log.d("Test", "recuperation reussi du mail");

					} else {//Sinon on l'annonce à l'utilisateur
						Log.d("Authentification", "erreur de mdp");
						//notification de l'echec de acitivity_authentification
						motDePasse.setError(getString(R.string.mdp_incorrect));
						motDePasse.requestFocus();
					}
				}
			});
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
        //Cette activité attend un résultat de l'activité fille, si tout s'est bien passé elle est détruire
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 10 && resultCode == RESULT_OK)
			finish();

	}

    //fonction permettant de récupérer l'utilisateur depuis la BDD externe
    //Une fois fois l'authentification réussi, insère l'utilisateur dans la BDD interne
	private void connexionReussi(String mail){
        Log.d("Authentification", "On est dans connexionReussi");

        remoteBD.getIDFromMail(mail, new OnStringReceived() {
			@Override
			public void onStringReceived(String s) {
				Log.d("Authentification", "On recoit un string");

				onIDreceived(s);
			}
		});
        
	}

	private void onIDreceived(final String userID) {
		FetchFullDataFromEBDD.fetchUser(userID, remoteBD, new OnFullUserData() {
			@Override
			public void onFullUserData(LocalUserProfilEBDD localUserProfilEBDD,
									   Position position,
									   Picture picture, UserParamsEBDD params) {
				FetchFullDataFromEBDD.fetchUser(userID, remoteBD, new OnFullUserData() {
					@Override
					public void onFullUserData(LocalUserProfilEBDD localUserProfilEBDD,
											   Position position,
											   Picture picture,
											   UserParamsEBDD params) {
						Log.d("Essaie", "valeur id firebase : " + localUserProfilEBDD.getDataBaseId());
						utilisateur = FromEBDDToLocalClassTranslator.utilisateurFromEBDD(localUserProfilEBDD,
								position, params, picture);
						onUserReceived(utilisateur);
						startActivity(new Intent(Authentification.this, Accueil.class));
						finish();
					}
				});
			}
		});
	}

	protected void onPause() {
		super.onPause();
		this.unregisterReceiver(mBatInfoReceiver);
	}

	protected void onResume() {
		super.onResume();
		this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

	private void onUserReceived(Utilisateur utilisateur) {
		//TODO fais toi plèz Alex!!!!
		UtilisateurBDD utilisateurBDD = new UtilisateurBDD(this);
        Log.d("Essaie", "On stocke l'utilisateur dans la BDD interne");

        utilisateurBDD.open();
        long id = utilisateurBDD.insererUtilisateur(utilisateur);
        utilisateur.setIdBDD(id);
        utilisateurBDD.connexion(utilisateur);
        utilisateurBDD.affichageUtilisateurConnecte();
        utilisateurBDD.close();
        //TODO remove test

	}
}
