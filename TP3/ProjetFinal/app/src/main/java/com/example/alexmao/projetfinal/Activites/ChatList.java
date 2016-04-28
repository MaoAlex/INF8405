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

import com.example.alexmao.projetfinal.BDDInterne.ConversationBDD;
import com.example.alexmao.projetfinal.BDDInterne.UtilisateurBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Conversation;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;
import com.example.alexmao.projetfinal.custom.CustomActivity;
import com.example.alexmao.projetfinal.utils.Utils;

import java.util.ArrayList;

/**
 * The Class ChatList is the Activity class. It shows a list of all users of
 * this app. It also shows the Offline/Online status of users.
 */
public class ChatList extends CustomActivity
{

	/** The Chat list. */
	private ArrayList<Conversation> conversationListe;

	/** The utilisateur. */
	public static Utilisateur utilisateur;

    //BD interne
    private UtilisateurBDD utilisateurBDD;
    private ConversationBDD conversationBDD;
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_list);

		//Récupération de la toolbar et mise en place
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		//Mise en place de la flèche pour le retour en arrière
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

        conversationListe = new ArrayList<>();

        utilisateurBDD = new UtilisateurBDD(this);
        utilisateurBDD.open();

        utilisateur = utilisateurBDD.obtenirProfil();

        conversationBDD = new ConversationBDD(this);
		conversationBDD.open();
        conversationBDD.affichageConversation();

        conversationListe = conversationBDD.obtenirListeConversation();
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
		loadConversation();
	}

	/**
     * Permet de mettre à jour le statut de la conversation
	 */
	private void updateUserStatus(boolean online)
	{
		//utilisateur.put("online", online);
		//utilisateur.saveEventually();
	}

	/**
     * Récupération de la liste des conversation
	 */
	private void loadConversation()
	{
		final ProgressDialog dia = ProgressDialog.show(this, null,
				getString(R.string.alert_loading));

        dia.dismiss();
        if (conversationListe != null)
        {
            if (conversationListe.size() == 0)
                Toast.makeText(ChatList.this,
                        "Vous n'avez pas de conversation en cours",
                        Toast.LENGTH_SHORT).show();

            ListView list = (ListView) findViewById(R.id.list);
            list.setAdapter(new ConversationAdapter());
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0,
                                        View arg1, int pos, long arg3)
                {
                    startActivity(new Intent(ChatList.this,
                            Chat.class).putExtra(
                            "id", conversationListe.get(pos).getIdBDD()
                                    ));
                }
            });
        }
        else
        {
            Utils.showDialog(ChatList.this, getString(R.string.err_users) + " ");
        }

	}

	/**
     *
	 * ConversationAdapter est un adaptateur pour la liste des conversation
     * Cette adapteur montre la liste des conversations et le nom de chaque conversation
     * Adapter pour l'affichage de la liste des conversation
     */
	private class ConversationAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return conversationListe.size();
		}

		@Override
		public Conversation getItem(int arg0)
		{
			return conversationListe.get(arg0);
		}

		@Override
		public long getItemId(int arg0)
		{
			return arg0;
		}

		@Override
		public View getView(int pos, View v, ViewGroup arg2)
		{
			if (v == null)
				v = getLayoutInflater().inflate(R.layout.chat_item, null);

			Conversation c = getItem(pos);
			TextView lbl = (TextView) v;
			lbl.setText(c.getNomConversation());
//			lbl.setCompoundDrawablesWithIntrinsicBounds(
//					c.isOnline() ? R.drawable.ic_online
//							: R.drawable.ic_offline, 0, R.drawable.arrow, 0);

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
