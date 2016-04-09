package com.example.alexmao.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.alexmao.chat.BDDExterne.FireBaseBD;
import com.example.alexmao.chat.BDDExterne.RemoteBD;
import com.example.alexmao.chat.BDDExterne.UserProfilEBDD;
import com.example.alexmao.chat.custom.CustomActivity;
import com.example.alexmao.chat.utils.Utils;


/**
 * The Class Register is the Activity class that shows user registration screen
 * that allows user to register itself on Parse server for this Chat app.
 */
public class Register extends CustomActivity
{
	private RemoteBD remoteBD;

	/** The username EditText. */
	private EditText user;

	/** The password EditText. */
	private EditText pwd;

	/** The email EditText. */
	private EditText email;

	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		remoteBD = new FireBaseBD(this);
		setTouchNClick(R.id.btnReg);

		user = (EditText) findViewById(R.id.user);
		pwd = (EditText) findViewById(R.id.pwd);
		email = (EditText) findViewById(R.id.email);
	}

	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);

		String u = user.getText().toString();
		String p = pwd.getText().toString();
		String e = email.getText().toString();
		if (u.length() == 0 || p.length() == 0 || e.length() == 0)
		{
			Utils.showDialog(this, R.string.err_fields_empty);
			return;
		}
		final ProgressDialog dia = ProgressDialog.show(this, null,
				getString(R.string.alert_wait));

		final UserProfilEBDD user = new UserProfilEBDD();
		user.setLastName(u);
		user.setMailAdr(p);
		//Ajout de l'utilisateur dans la BDD externe
		final String idFirebase = remoteBD.addUserProfil(user);
		remoteBD.addMdpToUser(e, p);

		startActivity(new Intent(Register.this, UserList.class));
		setResult(RESULT_OK);
		finish();
		//Completer les différents elements de l'utilisateur
		/*pu.signUpInBackground(new SignUpCallback() {

			@Override
			public void done(ParseException e)
			{
				dia.dismiss();
				if (e == null)
				{
					UserList.user = pu;
					startActivity(new Intent(Register.this, UserList.class));
					setResult(RESULT_OK);
					finish();
				}
				else
				{
					Utils.showDialog(
							Register.this,
							getString(R.string.err_singup) + " "
									+ e.getMessage());
					e.printStackTrace();
				}
			}
		});*/

	}
}
