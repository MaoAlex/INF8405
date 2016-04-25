package com.example.alexmao.projetfinal.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.OnBooleanReceived;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.custom.CustomActivity;
import com.example.alexmao.projetfinal.utils.Utils;


/**
 * La classe Inscription est l'activité qui permet à l'utilisateur de s'inscrire sur l'application
 * Il lui est alors demandé son mail et il peut choisir un mot de passe
 * Une fois un compte valide choisi, cette activité va lancer celle permettant de récupérer et définir les différentes informations sur l'utilsiateur
 */
public class Inscription extends CustomActivity
{
    //Interface avec la BDD externe
	private RemoteBD remoteBD;

    //Champ pour entrer l'adresse mail
    private EditText email;
    //Champ pour entrer le mot de passe
    private EditText motDePasse ;
    //Champ pour entrer la confirmation du mot de passe
	private EditText confirmation;
    //Boolean permettant de savoir si l'adresse mail a déjà été associé à un compte
    private boolean profilExistant = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);

        //Connection des différents éléments
        remoteBD = new FireBaseBD(this);
        setTouchNClick(R.id.btnReg);
		email = (EditText) findViewById(R.id.email);
		motDePasse = (EditText) findViewById(R.id.mdp);
		confirmation = (EditText) findViewById(R.id.mdpConfirmation);
	}


    //Action lors du clic
	@Override
	public void onClick(View v)
	{
		super.onClick(v);

        //On récupère le contenu des différents champs
		String mdpConfirmation = confirmation.getText().toString();
		final String mdp = motDePasse .getText().toString();
		final String mail = email.getText().toString();

        //On vérifie que les champs ne sont pas vides
		if (mdpConfirmation.length() == 0 || mdp.length() == 0 || mail.length() == 0) {
            // On vérifie que l'adresse mail rentré est valide, c'est-à-dire avec un "@"
            if (!isEmailValid(mail))
                email.setError(getString(R.string.error_invalid_email));
			else
                Utils.showDialog(this, R.string.err_fields_empty);
			return;
		} else if (mdpConfirmation.length() != mdp.length()) {
		    //Vérification que les mdps rentrés sont identiques
            Utils.showDialog(this, R.string.err_mdp);
            return;
        } else {
            //On vérifie que l'adresse mail n'est pas déjà associé à un compte
            remoteBD.getExistUser(mail, new OnBooleanReceived() {
                @Override
                public void onBooleanReceived(boolean b) {
                        profilExistant = b;
                    if (!profilExistant)
                        onProfilNonExistant(mail, mdp);
                }

            });
        }
    }

    //méthode de vérificaition basique d'une adresse email
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }


    //est appelée si le profil existe sur la base de données
    private void onProfilNonExistant(String mail, String mdp) {
        //Affichage du processus d'attente
        final ProgressDialog dia = ProgressDialog.show(this, null,
                getString(R.string.alert_wait));
        //Si l'adresse mail n'est pas utilisé, on va créer le compte
        //Pour cela on transmet les informations à l'activité suivante
        // InscriptionInformation qui va s'occuper d'enregistrer le compte
        Intent intent = new Intent(Inscription.this, InscriptionInformation.class);
        intent.putExtra("mail", mail);
        intent.putExtra("mdp", mdp);
        startActivity(intent);
        setResult(RESULT_OK);
        //On tue l'activité
        finish();
    }

}
