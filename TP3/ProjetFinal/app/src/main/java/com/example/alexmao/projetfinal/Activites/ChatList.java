package com.example.alexmao.projetfinal.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;
import com.example.alexmao.projetfinal.custom.CustomActivity;
import com.example.alexmao.projetfinal.utils.Const;
import com.example.alexmao.projetfinal.utils.Utils;

import java.util.ArrayList;

/**
 * The Class ChatList is the Activity class. It shows a list of all users of
 * this app. It also shows the Offline/Online status of users.
 */
public class ChatList extends CustomActivity
{

	/** The Chat list. */
	private ArrayList<Utilisateur> uList;

	/** The user. */
	public static Utilisateur user;

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_list);

		//Récupération de la toolbar et mise en place
		Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		//Mise en place de la flèche pour le retour en arrière
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
        uList = new ArrayList<>();
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("salut");

        Utilisateur utilisateur1 = new Utilisateur();
        utilisateur1.setNom("Yeah");
        uList.add(utilisateur);
        uList.add(utilisateur1);
		updateUserStatus(true);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		updateUserStatus(false);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume()
	{
		super.onResume();
		loadUserList();
	}

	/**
	 * Update user status.
	 * 
	 * @param online
	 *            true if user is online
	 */
	private void updateUserStatus(boolean online)
	{
		//user.put("online", online);
		//user.saveEventually();
	}

	/**
	 * Load list of users.
	 */
	private void loadUserList()
	{
		final ProgressDialog dia = ProgressDialog.show(this, null,
				getString(R.string.alert_loading));
		/*ParseUser.getQuery().whereNotEqualTo("username", user.getNom())
				.findInBackground(new FindCallback<ParseUser>() {

					@Override
					public void done(List<ParseUser> li, ParseException e)
					{
						dia.dismiss();
						if (li != null)
						{
							if (li.size() == 0)
								Toast.makeText(ChatList.this,
										R.string.msg_no_user_found,
										Toast.LENGTH_SHORT).show();

							uList = new ArrayList<Utilisateur>();
							uList = (ArrayList)li;
							ListView list = (ListView) findViewById(R.id.list);
							list.setAdapter(new UserAdapter());
							list.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int pos, long arg3)
								{
									startActivity(new Intent(ChatList.this,
											Chat.class).putExtra(
											Const.EXTRA_DATA, uList.get(pos)
													.getNom()));
								}
							});
						}
						else
						{
							Utils.showDialog(
									ChatList.this,
									getString(R.string.err_users) + " "
											+ e.getMessage());
							e.printStackTrace();
						}
					}
				});*/

        dia.dismiss();
        if (uList != null)
        {
            if (uList.size() == 0)
                Toast.makeText(ChatList.this,
                        R.string.msg_no_user_found,
                        Toast.LENGTH_SHORT).show();

            ListView list = (ListView) findViewById(R.id.list);
            list.setAdapter(new UserAdapter());
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0,
                                        View arg1, int pos, long arg3)
                {
                    startActivity(new Intent(ChatList.this,
                            Chat.class).putExtra(
                            Const.EXTRA_DATA, uList.get(pos)
                                    .getNom()));
                }
            });
        }
        else
        {
            Utils.showDialog(
                    ChatList.this,
                    getString(R.string.err_users) + " "
            );
        }

	}

	/**
	 * The Class UserAdapter is the adapter class for Utilisateur ListView. This
	 * adapter shows the user name and it's only online status for each item.
	 */
	private class UserAdapter extends BaseAdapter
	{

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount()
		{
			return uList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Utilisateur getItem(int arg0)
		{
			return uList.get(arg0);
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int arg0)
		{
			return arg0;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int pos, View v, ViewGroup arg2)
		{
			if (v == null)
				v = getLayoutInflater().inflate(R.layout.chat_item, null);

			Utilisateur c = getItem(pos);
			TextView lbl = (TextView) v;
			lbl.setText(c.getNom());
			/*lbl.setCompoundDrawablesWithIntrinsicBounds(
					c.isOnline() ? R.drawable.ic_online
							: R.drawable.ic_offline, 0, R.drawable.arrow, 0);
*/
			return v;
		}

	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Gestion du clique sur le retour en arrière
        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
