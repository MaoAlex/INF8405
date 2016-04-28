package com.example.alexmao.projetfinal.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexmao.projetfinal.Adapter.AdapterUtilisateur;
import com.example.alexmao.projetfinal.BDDExterne.FetchFullDataFromEBDD;
import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.FromEBDDToLocalClassTranslator;
import com.example.alexmao.projetfinal.BDDExterne.LocalUserProfilEBDD;
import com.example.alexmao.projetfinal.BDDExterne.NotificationBDD;
import com.example.alexmao.projetfinal.BDDExterne.NotificationTypes;
import com.example.alexmao.projetfinal.BDDExterne.OnFullUserData;
import com.example.alexmao.projetfinal.BDDExterne.OnStringReceived;
import com.example.alexmao.projetfinal.BDDExterne.Picture;
import com.example.alexmao.projetfinal.BDDExterne.Position;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDExterne.UserParamsEBDD;
import com.example.alexmao.projetfinal.BDDInterne.UtilisateurBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.InvitationConnexion;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InviterConnexion extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RemoteBD remoteBD;
    private UtilisateurBDD utilisateurBDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inviter_connexion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_inviter_connexion);
        //Mise en place de la flèche pour le retour en arrière
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Recupération du recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        mRecyclerView.setHasFixedSize(true);

        // Utilisation d'un linear LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        utilisateurBDD = new UtilisateurBDD(this);
        utilisateurBDD.open();
        Utilisateur utilisateur = utilisateurBDD.obtenirProfil();
        utilisateurBDD.close();

        EditText editText = (EditText) findViewById(R.id.connexion_email);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.d("Inviter Connexion","recherche du mail :"+v.getText().toString());
                    rechercherUtilisateur(v.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });
        remoteBD = new FireBaseBD(this);
    }

    private void setupRecyclerView() {

    }

    private void rechercherUtilisateur(final String mail) {
        remoteBD.getIDFromMail(mail, new OnStringReceived() {
            @Override
            public void onStringReceived(final String s) {
                if(s != null){
                FetchFullDataFromEBDD.fetchUser(s, remoteBD, new OnFullUserData() {
                    @Override
                    public void onFullUserData(LocalUserProfilEBDD localUserProfilEBDD,
                                               Position position,
                                               Picture picture,
                                               UserParamsEBDD params) {
                        if(!s.equals(null)) {
                            Utilisateur utilisateur =
                                    FromEBDDToLocalClassTranslator.utilisateurFromEBDD(localUserProfilEBDD,
                                            position, params, picture);
                            Log.d("Inviter Connexion", "recherche du mail :" + mail);
                            onUserReceived(utilisateur);
                        }else{
                            Toast.makeText(InviterConnexion.this, "L'utilisateur n'hésite pas !", Toast.LENGTH_SHORT).show();

                        }



                    }
                });}else{
                    Toast.makeText(InviterConnexion.this, "L'utilisateur n'hésite pas !", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void onUserReceived(Utilisateur utilisateur) {
        //TODO : Faire quelque chose
        //ex afficher le s données de l'utilisateur issu de la recherche
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        //Permet l'optimisation des performances
        // Dans le cas où l'on sait que la taille du layout RecyclerView ne va pas changer
        // Spécification de l'adapter
        ArrayList<Utilisateur> myDataset = new ArrayList<>();
        utilisateurBDD.open();
        long id = utilisateurBDD.insererUtilisateur(utilisateur);
        Log.d("Inviter Connexion", "l'id de l'utilisateur est : " + id);
        utilisateur.setIdBDD(id);
        myDataset.add(utilisateur);
        mAdapter = new AdapterUtilisateur(myDataset);
        Log.d("Inviter Connexion", "On va afficher l'utilisateur");
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Gestion du clique sur le retour en arrière
        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    //Envoie une invitation connexion
    private void sendConnexionInvitation(InvitationConnexion invitationConnexion, String userID) {
        Map<String,String> params = new HashMap<>();
        params.put("something", "exemple");
        NotificationBDD notificationBDD = new NotificationBDD(NotificationTypes.conctactInvitation,
               params, invitationConnexion.getExpediteur().getIdFirebase(),
                invitationConnexion.getInvite().getIdFirebase());
        notificationBDD.setDate(invitationConnexion.getDate());
        remoteBD.addNotificationToUser(userID, notificationBDD);
    }
}
