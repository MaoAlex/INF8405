package com.example.alexmao.projetfinal.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.OnStringReceived;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.UserList;
import com.example.alexmao.projetfinal.custom.CustomActivity;
import com.example.alexmao.projetfinal.utils.Utils;

/**
 * The Class Authentification is an Activity class that shows the acitivity_authentification screen to users.
 * The current implementation simply includes the options for Authentification and button
 * for Inscription. On acitivity_authentification button click, it sends the Authentification details to Parse
 * server to verify user.
 */
public class Authentification extends CustomActivity
{
    private RemoteBD remoteBD;

    private static final String TAG = "Authentification";
    /** The username edittext. */
	private EditText user;

	/** The password edittext. */
	private EditText motDePasse;
	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivity_authentification);
        remoteBD = new FireBaseBD(this);

        setTouchNClick(R.id.btnLogin);
		setTouchNClick(R.id.btnReg);

		user = (EditText) findViewById(R.id.user);
		motDePasse = (EditText) findViewById(R.id.motDePasse);

	}

	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (v.getId() == R.id.btnReg)
		{
			startActivityForResult(new Intent(this, Inscription.class), 10);
		}
		else
		{

			String u = user.getText().toString();
			final String p = motDePasse.getText().toString();


            if (u.length() == 0 || p.length() == 0)
			{
				Utils.showDialog(this, R.string.err_fields_empty);
				return;
			}
			final ProgressDialog dia = ProgressDialog.show(this, null,
					getString(R.string.alert_wait));
			/*ParseUser.logInInBackground(u, p, new LogInCallback() {

				@Override
				public void done(ParseUser pu, ParseException e)
				{
					dia.dismiss();
					if (pu != null)
					{
						//UserList.user = pu;
						startActivity(new Intent(Authentification.this, UserList.class));
						finish();
					}
					else
					{
						Utils.showDialog(
								Authentification.this,
								getString(R.string.err_login) + " "
										+ e.getMessage());
						e.printStackTrace();
					}
				}
			});*/
            remoteBD.getMdp(u, new OnStringReceived() {
                @Override
                public void onStringReceived(String s) {
                    dia.dismiss();

                    if (s != null && s == p){

                        startActivity(new Intent(Authentification.this, UserList.class));
                        finish();
                    }else
                    {
                        Log.d("Authentification", "erreur de mdp");
                        //notification de l'echec de acitivity_authentification
						motDePasse.setError(getString(R.string.mdp_incorrect));
						motDePasse.requestFocus();
                    }
                }
            });
		}
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 10 && resultCode == RESULT_OK)
			finish();

	}



}
