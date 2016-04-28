package com.example.alexmao.projetfinal.Activites;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexmao.projetfinal.BDDExterne.ConversationEBDD;
import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.LocalUserProfilEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MessageEBDD;
import com.example.alexmao.projetfinal.BDDExterne.OnMessageReceiveCallback;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDInterne.ConversationBDD;
import com.example.alexmao.projetfinal.BDDInterne.GroupeBDD;
import com.example.alexmao.projetfinal.BDDInterne.UtilisateurBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Conversation;
import com.example.alexmao.projetfinal.classeApp.Groupe;
import com.example.alexmao.projetfinal.classeApp.Message;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;
import com.example.alexmao.projetfinal.custom.CustomActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * The Class Chat is the Activity class that holds main chat screen. It shows
 * all the conversation messages between two users and also allows the user to
 * send and receive messages.
 * Classe chat qui permet de dialoguer entre les utilisateurs d'un groupe
 */
public class Chat extends CustomActivity
{
    private RemoteBD remoteBD;
    private Conversation conversation;
	/** The Convers list. */
//	private Conversation convList;
	private ArrayList<Message> listeMessage;
	/** The chat adapter. */
	private ChatAdapter adp;
	/** The Editext to compose the message. */
	private EditText txt;
	/** The user name of buddy. */
	private String buddy;
	/** The date of last message in conversation. */
	private long lastMsgDate;
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
    private String conversationID;
    private ArrayList<Utilisateur> listeUtilisateur;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
        UtilisateurBDD utilisateurBDD = new UtilisateurBDD(this);
        utilisateurBDD.open();
        utilisateurConnecte = utilisateurBDD.obtenirProfil();
        long id = getIntent().getLongExtra("id", -1);

        ConversationBDD conversationBDD = new ConversationBDD(this);
        conversationBDD.open();
        conversation = conversationBDD.obtenirConversationParId(id);

		ListView list = (ListView) findViewById(R.id.list);
		adp = new ChatAdapter();
		list.setAdapter(adp);
		list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        //mise en place de la file de messages
		list.setStackFromBottom(true);
        //Mise en place de la zone de saisie
		txt = (EditText) findViewById(R.id.txt);
		txt.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //Mise en place du bouton d'envoie
		setTouchNClick(R.id.btnSend);
        //Recuperation du nom du groupe ou du destinataire unique
		buddy = conversation.getNomConversation();
        Log.d("chat", "Recuperation de la nouvelle conversatio");

        remoteBD = new FireBaseBD(this);
        /*currentUserFirebase = new LocalUserProfilEBDD("premier", "compte", "fifi@filou.com");
        Log.d("chat", "ici");
        String id = remoteBD.addUserProfil(currentUserFirebase);
        currentUserFirebase.setDataBaseId(id);
        testLocalUserProfil = new LocalUserProfilEBDD("deuxieme", "compte", "test@test.com");
        String idTest = remoteBD.addUserProfil(testLocalUserProfil);
        testLocalUserProfil.setDataBaseId(idTest);*/
//        currentUserFirebase = new LocalUserProfilEBDD("premier", "compte", "fifi@filou.com");
//        String id = remoteBD.addUserProfil(currentUserFirebase);
//        currentUserFirebase.setDataBaseId(id);
//        testLocalUserProfil = new LocalUserProfilEBDD("deuxieme", "compte", "test@test.com");
//        String idTest = remoteBD.addUserProfil(testLocalUserProfil);
//        testLocalUserProfil.setDataBaseId(idTest);
		if (conversationID == null) {
            discussion = new ConversationEBDD();
            conversationID = remoteBD.addDiscussion(discussion);
        }
        GroupeBDD groupeBDD = new GroupeBDD(this);
        groupeBDD.open();
		groupe = groupeBDD.obtenirGroupe(conversation.getIdFirebase());

        //Partie non encore utilisée
        //partie BDD interne
        listeUtilisateur = (ArrayList) groupe.getListeMembre();
        //Creation du Handler
		handler = new Handler();
        //On met en place le listerner qui nous permet d'etre notifie d'un nouveau message
        remoteBD.listenToConversations(utilisateurConnecte.getIdFirebase(), new OnMessageReceiveCallback() {
			@Override
			public void onNewMessage(MessageEBDD messageEBDD) {
				Log.d("chat", "notification de nouveau message");
				onNewMsg(messageEBDD);
			}
		});
	}

	@Override
	protected void onResume()
	{
        //Lorsque l'on utilise la rotation d'ecran
		super.onResume();
		isRunning = true;
		loadConversationList();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		isRunning = false;
	}

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
     * Fonction permettant l'envoi du message au destinataire.
     * Si le texte est vide l'envoi n'est pas fait, sinon on l'envoie
	 */

	private void sendMessage()
	{
        //cas où l'utilisateur n'a rien rentré
		if (txt.length() == 0)
			return;
        //Recuperation du message
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(txt.getWindowToken(), 0);

		String s = txt.getText().toString();
        /*
		final Convers c = new Convers(s, new Date(),
				groupe.getListeMembre().get(0).getNom());
		c.setStatus(Convers.STATUS_SENDING);*/
		GregorianCalendar calendar = new GregorianCalendar();
        final Message m = new Message(s, calendar.getTimeInMillis(), utilisateurConnecte);
        conversation.getListeMessage().add(m);
		adp.notifyDataSetChanged();
        //On remet un champ vide
		txt.setText(null);
        if (s == null)
            return;
        //Create a class
        MessageEBDD conversation   = new MessageEBDD();
        conversation.setDate(new Date().getTime());
        //content is what the user has written on the screen (in short the message body)
        conversation.setMessage(s);
        //id firebase
        String msgID = remoteBD.addMsgToDiscussion(conversationID, conversation);
        //mise à jour de l'état d'envoi du message
//        if (msgID != null)
//            m.setStatus(Convers.STATUS_SENT);
//        else
//            m.setStatus(Convers.STATUS_FAILED);
        adp.notifyDataSetChanged();
//        remoteBD.notifyUserForMsg(testLocalUserProfil.getDataBaseId(), conversation, conversationID);
        //onSendMsg(s);
	}

	/**
	 * Load the conversation list from Parse server and save the date of last
	 *
	 */
	private void loadConversationList()
	{
//        Log.d("Chat", "on est loadConversationList");
//        //when a new message arrived, we call onNewMsg
//		remoteBD.getDiscussion(conversationID, new OnConversationReceived() {
//			@Override
//			public void onConversationRecieved(ConversationEBDD conversationEBDD) {
//				onDiscussionReceived(conversationEBDD);
//			}
//		});
//        remoteBD.listenToConversation(conversationID, currentUserFirebase.getDataBaseId(), new OnMessageReceiveCallback() {
//            @Override
//            public void onNewMessage(MessageEBDD message) {
//                onNewMsg(message);
//            }
//        });

	}

	/**
	 * The Class ChatAdapter is the adapter class for Chat ListView. This
	 * adapter shows the Sent or Receieved Chat message in each list item.
	 */
	private class ChatAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{

			return conversation.getListeMessage().size();
		}

		@Override
		public Message getItem(int arg0)
		{
			return conversation.getListeMessage().get(arg0);
		}

		@Override
		public long getItemId(int arg0)
		{
			return arg0;
		}

		@Override
		public View getView(int pos, View v, ViewGroup arg2)
		{
			Message message = getItem(pos);
			if (message.getExpediteur().getIdBDD() == utilisateurConnecte.getIdBDD())
				v = getLayoutInflater().inflate(R.layout.chat_item_sent, null);
			else
				v = getLayoutInflater().inflate(R.layout.chat_item_rcv, null);

			TextView lbl = (TextView) v.findViewById(R.id.lbl1);
			lbl.setText(DateUtils.getRelativeDateTimeString(Chat.this, message
					.getDate(), DateUtils.SECOND_IN_MILLIS,
					DateUtils.DAY_IN_MILLIS, 0));

			lbl = (TextView) v.findViewById(R.id.lbl2);
			lbl.setText(message.getMessage());

			lbl = (TextView) v.findViewById(R.id.lbl3);
//			if (message.getExpediteur().getIdBDD() == utilisateurConnecte.getIdBDD())
//			{
//				if (message.getStatus() == Convers.STATUS_SENT)
//					lbl.setText("Delivered");
//				else if (message.getStatus() == Convers.STATUS_SENDING)
//					lbl.setText("Sending...");
//				else
//					lbl.setText("Failed");
//			}
//			else
//				lbl.setText("");

			return v;
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == android.R.id.home)
		{
            finish();
		}
		return super.onOptionsItemSelected(item);
	}

    //fonction d'envoi d'un message
    void onSendMsg(String content) {
        if (content == null)
            return;
        //Create a class
        MessageEBDD conversation   = new MessageEBDD();
        conversation.setDate(new Date().getTime());
        //content is what the user has written on the screen (in short the message body)
        conversation.setMessage(content);
        //id firebase
        String msgID = remoteBD.addMsgToDiscussion(conversationID, conversation);

        adp.notifyDataSetChanged();
//        remoteBD.
//        remoteBD.notifyUserForMsg(testLocalUserProfil.getDataBaseId(), conversation, conversationID);
    }

    //Fonction permettant la mise à jour du nouveau message reçu
    void onNewMsg(MessageEBDD messageEBDD) {
        Log.d("Chat", "On est dans la r�cup�ration des nouveaux messages");
        if (messageEBDD == null)
            return;
		//TODO faire attention le message reçu n'est pas nécessairement de cette conversation
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(messageEBDD.getDate());
        UtilisateurBDD utilisateurBDD = new UtilisateurBDD(this);
        Utilisateur expediteur = utilisateurBDD.obtenirUtilisateurParIdFirebase(messageEBDD.getExpediteurID());
        //Utilisateur expediteur = new Utilisateur();
        //expediteur.setIdBDD(135);
        Message m= new Message(messageEBDD.getMessage(), messageEBDD.getDate(), expediteur);
        conversation.getListeMessage().add(m);
        if (lastMsgDate == 0)
                //|| lastMsgDate.before(m.getDate()))
            lastMsgDate = m.getDate();
        //on notifie l'adaptater des changements
        adp.notifyDataSetChanged();
        handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (isRunning)
					loadConversationList();
			}
		}, 1000);
    }

	void onDiscussionReceived(ConversationEBDD conversationEBDD) {
		String buffer = "";
		for (MessageEBDD messageBDD: conversationEBDD.getListeMessage()) {
			buffer += messageBDD.getMessage() + "\n";
		}
	}

}
