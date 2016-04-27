package com.example.alexmao.projetfinal.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alexmao.projetfinal.Adapter.AdapterInvitationConnexion;
import com.example.alexmao.projetfinal.Adapter.AdapterUtilisateurNom;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.InvitationConnexion;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class InviterEvenement extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private AdapterUtilisateurNom mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Utilisateur> listeConnectes;
    private List<Utilisateur> listeSelectionnes;
    private List<Utilisateur> currentDataset;
    private long evenementID;
    private RemoteBD remoteBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inviter_evenement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_inviter_evenement);
        //Mise en place de la flèche pour le retour en arrière
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if(intent != null) {
            evenementID = intent.getLongExtra("evenementID", 0);
        }

        // préchargement
        listeConnectes = chargerUtilisateursConnectes();
        currentDataset = listeConnectes;
        listeSelectionnes = new ArrayList<Utilisateur>();

        final EditText editText = (EditText) findViewById(R.id.invitation_evt_nom);

            mRecyclerView=(RecyclerView)

            findViewById(R.id.rv_inv_evt);

            //Permet l'optimisation des performances
            // Dans le cas où l'on sait que la taille du layout RecyclerView ne va pas changer

            mRecyclerView.setHasFixedSize(true);

            // Utilisation d'un linear LayoutManager
            mLayoutManager=new

            LinearLayoutManager(this);

            mRecyclerView.setLayoutManager(mLayoutManager);

            // Spécification de l'adapter
            mAdapter=new

            AdapterUtilisateurNom(currentDataset, listeSelectionnes);

            mRecyclerView.setAdapter(mAdapter);
        }

        private List<Utilisateur> chargerUtilisateursConnectes() {
        // TODO : Charger les connectés depuis la BDD
        return new ArrayList<Utilisateur>();
    }



    private void rechercherUtilisateur(String query) {
        currentDataset = new ArrayList<>();
        for(int i=0; i<listeConnectes.size(); i++) {
            if(listeConnectes.get(i).getNom().contains(query) || listeConnectes.get(i).getPrenom().contains(query)) {
                currentDataset.add(listeConnectes.get(i));
                Log.d("Test",listeConnectes.get(i).getNom()+" contient "+query);
            }
        }

        mAdapter.updateDataset(currentDataset);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_invitation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_send) {
            envoyerInvitations();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void envoyerInvitations() {
        // TODO : Envoyer les invitations
        // Exemple de récupération des utilisateurs
        List<Utilisateur> utilisateursSel = mAdapter.getUtilisateursSelectionnes();
        for(int i=0; i<utilisateursSel.size();i++) {
            Log.d("Invitation", utilisateursSel.get(i).getNom());
        }
    }

}