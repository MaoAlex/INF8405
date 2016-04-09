package com.example.alexmao.chat.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.alexmao.chat.BDDExterne.FireBaseBD;
import com.example.alexmao.chat.BDDExterne.OnStringReceived;
import com.example.alexmao.chat.BDDExterne.RemoteBD;
import com.example.alexmao.chat.R;
import com.example.alexmao.chat.UserList;
import com.example.alexmao.chat.custom.CustomActivity;
import com.example.alexmao.chat.utils.Utils;

/**
 * The Class Login is an Activity class that shows the login screen to users.
 * The current implementation simply includes the options for Login and button
 * for Register. On login button click, it sends the Login details to Parse
 * server to verify user.
 */
public class Login extends CustomActivity
{
    private RemoteBD remoteBD;

    private static final String TAG = "Login";
    /** The username edittext. */
	private EditText user;

	/** The password edittext. */
	private EditText pwd;
	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
        remoteBD = new FireBaseBD(this);

        setTouchNClick(R.id.btnLogin);
		setTouchNClick(R.id.btnReg);

		user = (EditText) findViewById(R.id.user);
		pwd = (EditText) findViewById(R.id.pwd);

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
			startActivityForResult(new Intent(this, Register.class), 10);
		}
		else
		{

			String u = user.getText().toString();
			final String p = pwd.getText().toString();


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
						startActivity(new Intent(Login.this, UserList.class));
						finish();
					}
					else
					{
						Utils.showDialog(
								Login.this,
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

                    if (s != null && s.equals(p)){

                        startActivity(new Intent(Login.this, UserList.class));
                        finish();
                    }else
                    {
                        Log.d("Login", "erreur de mdp");
                        Utils.showDialog(Login.this, getString(R.string.err_login) + " le mdp n'est pas bon" );
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
