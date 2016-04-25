package com.example.alexmao.projetfinal.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.LocalUserProfilEBDD;
import com.example.alexmao.projetfinal.BDDExterne.OnStringReceived;
import com.example.alexmao.projetfinal.BDDExterne.OnUserParamReceived;
import com.example.alexmao.projetfinal.BDDExterne.OnUserProfilReceived;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDExterne.UserParamsEBDD;
import com.example.alexmao.projetfinal.BDDExterne.UtilisateurProfilEBDD;
import com.example.alexmao.projetfinal.BDDInterne.UtilisateurBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.ParametresUtilisateur;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;
import com.example.alexmao.projetfinal.custom.CustomActivity;
import com.example.alexmao.projetfinal.utils.Utils;

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


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
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
                    if (s != null && s.equals(p)){
                        //connexionReussi(email);
                        startActivity(new Intent(Authentification.this, ChatList.class));
                        finish();
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
		Utilisateur utilisateur;
        LocalUserProfilEBDD localUserProfilEBDD = new LocalUserProfilEBDD();
		remoteBD.getUserProfilFromMail(mail, localUserProfilEBDD, new OnUserProfilReceived() {
            @Override
            public void onUserProfilReceived(UtilisateurProfilEBDD userProfilEBDD) {
                //On n'a pas besoin de faire quelque chose ici
                //On ne récupère que l'utilisateur pour le mettre dans la BDD interne
            }
        });
        final ParametresUtilisateur parametres;
        remoteBD.getUserParam(localUserProfilEBDD.getDataBaseId(), new OnUserParamReceived() {
            @Override
            public void onUserParamReceived(UserParamsEBDD userParamsEBDD) {
           //     parametres = FromEBDDToLocalClassTranslator.translateUserParam(userParamsEBDD);
            }
        });

        //utilisateur = new FromEBDDToLocalClassTranslator(localUserProfilEBDD, );
		UtilisateurBDD utilisateurBDD = new UtilisateurBDD(this);
        utilisateurBDD.open();
        //utilisateurBDD.insererUtilisateur(utilisateur);
        utilisateurBDD.close();
        
	}
}
