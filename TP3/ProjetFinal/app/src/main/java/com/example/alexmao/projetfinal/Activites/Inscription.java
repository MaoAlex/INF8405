package com.example.alexmao.projetfinal.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.LocalUserProfilEBDD;
import com.example.alexmao.projetfinal.BDDExterne.OnUserProfilReceived;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDExterne.UtilisateurProfilEBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.custom.CustomActivity;
import com.example.alexmao.projetfinal.utils.Utils;


/**
 * The Class Inscription is the Activity class that shows user registration screen
 * that allows user to activity_inscription itself on Parse server for this Chat app.
 */
public class Inscription extends CustomActivity
{
	private RemoteBD remoteBD;

	/** The username EditText. */
	private EditText confirmation;

	/** The password EditText. */
	private EditText motDePasse ;

	/** The email EditText. */
	private EditText email;
    private boolean profilExistant = false;
	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);
		remoteBD = new FireBaseBD(this);
		setTouchNClick(R.id.btnReg);

		email = (EditText) findViewById(R.id.email);
		motDePasse = (EditText) findViewById(R.id.mdp);
		confirmation = (EditText) findViewById(R.id.mdpConfirmation);
	}

	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);

		String mdpConfirmation = confirmation.getText().toString();
		String mdp = motDePasse .getText().toString();
		String mail = email.getText().toString();


		if (mdpConfirmation.length() == 0 || mdp.length() == 0 || mail.length() == 0)
		{
            // Check for a valid email address.
            if (!isEmailValid(mail))
                email.setError(getString(R.string.error_invalid_email));
			else
                Utils.showDialog(this, R.string.err_fields_empty);
			return;
		}else if (mdpConfirmation.length() != mdp.length())
        {
            Utils.showDialog(this, R.string.err_mdp);
            return;
        }else {
            remoteBD.getUserProfilFromMail(mail, new LocalUserProfilEBDD(), new OnUserProfilReceived() {
                @Override
                public void onUserProfilReceived(UtilisateurProfilEBDD userProfilEBDD) {
                    if(userProfilEBDD!=null)
                        profilExistant = true;
                }
            });
            if(!profilExistant) {
                final ProgressDialog dia = ProgressDialog.show(this, null,
                        getString(R.string.alert_wait));

            /*final UtilisateurProfilEBDD user = new UtilisateurProfilEBDD();
            user.setLastName(mdpConfirmation);
            user.setMailAdr(mdp);*/
                //Ajout de l'utilisateur dans la BDD externe
                //final String idFirebase = remoteBD.addUserProfil(user);
                //remoteBD.addMdpToUser(mail, mdp);
                Intent intent = new Intent(Inscription.this, InscriptionInformation.class);
                //intent.putExtra("idFirebase", idFirebase);
                intent.putExtra("mail", mail);
                intent.putExtra("mdp", mdp);
                remoteBD.addMdpToUser(mail, mdp);
                startActivity(intent);
                setResult(RESULT_OK);
                finish();
            }
        }
		//Completer les différents elements de l'utilisateur
		/*pu.signUpInBackground(new SignUpCallback() {

			@Override
			public void done(ParseException mail)
			{
				dia.dismiss();
				if (mail == null)
				{
					UserList.user = pu;
					startActivity(new Intent(Inscription.this, UserList.class));
					setResult(RESULT_OK);
					finish();
				}
				else
				{
					Utils.showDialog(
							Inscription.this,
							getString(R.string.err_singup) + " "
									+ mail.getMessage());
					mail.printStackTrace();
				}
			}
		});*/

	}

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

}
