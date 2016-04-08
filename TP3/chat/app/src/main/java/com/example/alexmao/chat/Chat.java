package com.example.alexmao.chat;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexmao.chat.BDDExterne.ConversationEBDD;
import com.example.alexmao.chat.BDDExterne.FireBaseBD;
import com.example.alexmao.chat.BDDExterne.LocalUserProfilEBDD;
import com.example.alexmao.chat.BDDExterne.MessageBDD;
import com.example.alexmao.chat.BDDExterne.OnMessageReceiveCallback;
import com.example.alexmao.chat.BDDExterne.RemoteBD;
import com.example.alexmao.chat.classeApp.Conversation;
import com.example.alexmao.chat.classeApp.Groupe;
import com.example.alexmao.chat.classeApp.Message;
import com.example.alexmao.chat.classeApp.Utilisateur;
import com.example.alexmao.chat.custom.CustomActivity;
import com.example.alexmao.chat.model.Convers;
import com.example.alexmao.chat.utils.Const;

import java.util.ArrayList;
import java.util.Date;

/**
 * The Class Chat is the Activity class that holds main chat screen. It shows
 * all the conversation messages between two users and also allows the user to
 * send and receive messages.
 */
public class Chat extends CustomActivity
{
    private LinearLayout linearLayout;

    private RemoteBD remoteBD;
    private Conversation conversation;
	/** The Convers list. */
	private ArrayList<Convers> convList;
	/** The chat adapter. */
	private ChatAdapter adp;
	/** The Editext to compose the message. */
	private EditText txt;
	/** The user name of buddy. */
	private String buddy;
	/** The date of last message in conversation. */
	private Date lastMsgDate;
	/** Flag to hold if the activity is running or not. */
	private boolean isRunning;
	/** The handler. */
	private static Handler handler;
    //Ajoute
	private Groupe groupe;
	private Utilisateur utilisateurConnecte;
    private LocalUserProfilEBDD currentUserFirebase;
    private LocalUserProfilEBDD testLocalUserProfil;
    private ConversationEBDD discussion;
    private String discussionID;
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
        utilisateurConnecte = new Utilisateur();
        utilisateurConnecte.setNom("TEST");
        conversation = new Conversation();
        conversation.setNomConversation("text");
        conversation.setListeMessage(new ArrayList<Message>());
        convList = new ArrayList<Convers>();
		ListView list = (ListView) findViewById(R.id.list);
		adp = new ChatAdapter();
		list.setAdapter(adp);
		list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        linearLayout = (LinearLayout) findViewById(R.id.all_msg_layout);

        //mise en place du file
		list.setStackFromBottom(true);
        //Mise en place de la zone de saisie
		txt = (EditText) findViewById(R.id.txt);
		txt.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //Mise en place du bouton d'envoie
		setTouchNClick(R.id.btnSend);

        //Recuperation du nom du groupe ou plutot du destinataire
		buddy = getIntent().getStringExtra(Const.EXTRA_DATA);
        buddy = "E T";
        //On met comme titre le nom du destinataire
		getActionBar().setTitle(buddy);

        remoteBD = new FireBaseBD(this);
        currentUserFirebase = new LocalUserProfilEBDD("fifi", "filou", "fifi@filou.com");
        String id = remoteBD.addUserProfil(currentUserFirebase);
        currentUserFirebase.setDataBaseId(id);
        testLocalUserProfil = new LocalUserProfilEBDD("test", "test", "test@test.com");
        String idTest = remoteBD.addUserProfil(testLocalUserProfil);
        testLocalUserProfil.setDataBaseId(idTest);

        discussion = new ConversationEBDD();
        discussionID = remoteBD.addDiscussion(discussion);

		/*GroupeBDD groupeBDD = new GroupeBDD(this);
		groupeBDD.open();
		groupe = new Groupe();*/
        //Partie non encore utilisée
        groupe = new Groupe();
        ArrayList<Utilisateur> listeUtilisateur = new ArrayList<>();
        Utilisateur u1 = new Utilisateur();
        u1.setNom("nom");
        u1.setPrenom("prenom");
        u1.setDateNaissance(null);
        ArrayList<String> listeSport = new ArrayList<>();
        listeSport.add("Footba");
        u1.setSports(listeSport);
        u1.setPhoto(null);

/*        private double latitude;
        private double longitude;
        private List<Utilisateur> listeConnexion;
        private List<Integer> listeInterets;
        private List<Integer> listeParticipations;
        private String idFirebase;
        private int idBDD;
        private ParametresUtilisateur parametres;*/
        groupe.setListeMembre(new ArrayList<Utilisateur>());
        groupe.getListeMembre().add(u1);
//        groupe.getListeMembre();
        //Creation du Handler
		handler = new Handler();
        remoteBD.listenToConversation(discussionID, currentUserFirebase.getDataBaseId(), new OnMessageReceiveCallback() {
            @Override
            public void onNewMessage(MessageBDD message) {
                onNewMsg(message);
            }
        });
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume()
	{
        //Lorsque l'on utilise la rotation d'ecran
		super.onResume();
		isRunning = true;
		loadConversationList();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onPause()
	 */
	@Override
	protected void onPause()
	{
		super.onPause();
		isRunning = false;
	}

	/* (non-Javadoc)
	 * @see com.socialshare.custom.CustomFragment#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (v.getId() == R.id.btnSend)
		{
            //Au clic du bouton d'envoie on action l'envoi du message
			sendMessage();
		}

	}

	/**
	 * Call this method to Send message to opponent. It does nothing if the text
	 * is empty otherwise it creates a Parse object for Chat message and send it
	 * to Parse server.
     * Fonction permettant l'envoi du message au destinataire.
     * Si le texte est vide l'envoie n'est pas fait, sinon on l'envoie sur le message
	 */

	private void sendMessage()
	{
		if (txt.length() == 0)
			return;

        //Recuperation du message
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(txt.getWindowToken(), 0);

		String s = txt.getText().toString();
		final Convers c = new Convers(s, new Date(),
				groupe.getListeMembre().get(0).getNom());
		c.setStatus(Convers.STATUS_SENDING);
		convList.add(c);
        final Message m = new Message(s, new Date(), utilisateurConnecte);
        conversation.getListeMessage().add(m);
		adp.notifyDataSetChanged();
        //On remet un champ vide
		txt.setText(null);
        if (s == null)
            return;
        //Create a class
        MessageBDD conversation   = new MessageBDD();
        conversation.setDate(new Date().getTime());
        //content is what the user has written on the screen (in short the message body)
        conversation.setMessage(s);
        //id firebase
        String msgID = remoteBD.addMsgToDiscussion(discussionID, conversation);
        if (msgID != null)
            c.setStatus(Convers.STATUS_SENT);
        else
            c.setStatus(Convers.STATUS_FAILED);
        adp.notifyDataSetChanged();
        remoteBD.notifyUserForMsg(testLocalUserProfil.getDataBaseId(), conversation, discussionID);
        //onSendMsg(s);


	}

	/**
	 * Load the conversation list from Parse server and save the date of last
	 *
	 */
	private void loadConversationList()
	{

        //when a new message arrived, we call onNewMsg
        remoteBD.listenToConversation(discussionID, currentUserFirebase.getDataBaseId(), new OnMessageReceiveCallback() {
            @Override
            public void onNewMessage(MessageBDD message) {
                onNewMsg(message);
            }
        });
        /*
		ParseQuery<ParseObject> q = ParseQuery.getQuery("Chat");
		if (convList.size() == 0)
		{
			// load all messages...
			ArrayList<String> al = new ArrayList<String>();
			al.add(buddy);
			al.add(groupe.getListeMembre().get(0).getNom());
			q.whereContainedIn("sender", al);
			q.whereContainedIn("receiver", al);
		}
		else
		{
			// load only newly received message..
			if (lastMsgDate != null)
				q.whereGreaterThan("createdAt", lastMsgDate);
			q.whereEqualTo("sender", buddy);
			q.whereEqualTo("receiver", groupe.getListeMembre().get(0).getNom());
		}
		q.orderByDescending("createdAt");
		q.setLimit(30);
		q.findInBackground(
                new FindCallback<ParseObject>() {

                    @Override
                    public void done(List<ParseObject> li, ParseException e) {
                        if (li != null && li.size() > 0) {
                            for (int i = li.size() - 1; i >= 0; i--) {
                                ParseObject po = li.get(i);
                                Convers c = new Convers(po
                                        .getString("message"), po.getCreatedAt(), po
                                        .getString("sender"));
                                convList.add(c);
                                if (lastMsgDate == null
                                        || lastMsgDate.before(c.getDate()))
                                    lastMsgDate = c.getDate();
                                adp.notifyDataSetChanged();
                            }
                        }
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                if (isRunning)
                                    loadConversationList();
                            }
                        }, 1000);
                    }
                });*/

	}

	/**
	 * The Class ChatAdapter is the adapter class for Chat ListView. This
	 * adapter shows the Sent or Receieved Chat message in each list item.
	 */
	private class ChatAdapter extends BaseAdapter
	{

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount()
		{
			return convList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Convers getItem(int arg0)
		{
			return convList.get(arg0);
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
			Convers c = getItem(pos);
			if (c.isSent())
				v = getLayoutInflater().inflate(R.layout.chat_item_sent, null);
			else
				v = getLayoutInflater().inflate(R.layout.chat_item_rcv, null);

			TextView lbl = (TextView) v.findViewById(R.id.lbl1);
			lbl.setText(DateUtils.getRelativeDateTimeString(Chat.this, c
					.getDate().getTime(), DateUtils.SECOND_IN_MILLIS,
					DateUtils.DAY_IN_MILLIS, 0));

			lbl = (TextView) v.findViewById(R.id.lbl2);
			lbl.setText(c.getMsg());

			lbl = (TextView) v.findViewById(R.id.lbl3);
			if (c.isSent())
			{
				if (c.getStatus() == Convers.STATUS_SENT)
					lbl.setText("Delivered");
				else if (c.getStatus() == Convers.STATUS_SENDING)
					lbl.setText("Sending...");
				else
					lbl.setText("Failed");
			}
			else
				lbl.setText("");

			return v;
		}

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == android.R.id.home)
		{
            finish();
		}
		return super.onOptionsItemSelected(item);
	}

    void onSendMsg(String content) {
        if (content == null)
            return;
        //Create a class
        MessageBDD conversation   = new MessageBDD();
        conversation.setDate(new Date().getTime());
        //content is what the user has written on the screen (in short the message body)
        conversation.setMessage(content);
        //id firebase
        String msgID = remoteBD.addMsgToDiscussion(discussionID, conversation);

        adp.notifyDataSetChanged();
        remoteBD.notifyUserForMsg(testLocalUserProfil.getDataBaseId(), conversation, discussionID);
    }

    //Called when the current user recieved a new message
    void onNewMsg(MessageBDD conversation) {
        if (conversation == null)
            return;
        TextView textView = new TextView(this);
        //set the content
        textView.setText(conversation.getMessage());
        linearLayout.addView(textView);
        Date date = new Date(conversation.getDate());

        Convers c = new Convers(conversation.getMessage(),
                date, conversation.getExpediteurID().toString()
        );
        convList.add(c);
        if (lastMsgDate == null
                || lastMsgDate.before(c.getDate()))
            lastMsgDate = c.getDate();
        adp.notifyDataSetChanged();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (isRunning)
                    loadConversationList();
            }
        }, 1000);
    }

}
