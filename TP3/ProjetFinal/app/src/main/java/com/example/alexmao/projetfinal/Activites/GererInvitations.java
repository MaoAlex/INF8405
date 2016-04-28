package com.example.alexmao.projetfinal.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.alexmao.projetfinal.Activites.interfaces.BtnConnexionAccepterClickListener;
import com.example.alexmao.projetfinal.Activites.interfaces.BtnConnexionRefuserClickListener;
import com.example.alexmao.projetfinal.Activites.interfaces.BtnEvenementAccepterClickListener;
import com.example.alexmao.projetfinal.Activites.interfaces.BtnEvenementRefuserClickListener;
import com.example.alexmao.projetfinal.Adapter.AdapterInvitationConnexion;
import com.example.alexmao.projetfinal.Adapter.AdapterInvitationEvenement;
import com.example.alexmao.projetfinal.BDDInterne.InvitationConnexionBDD;
import com.example.alexmao.projetfinal.BDDInterne.InvitationEvenementBDD;
import com.example.alexmao.projetfinal.BDDInterne.UtilisateurBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.InvitationConnexion;
import com.example.alexmao.projetfinal.classeApp.InvitationEvenement;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;

public class GererInvitations extends AppCompatActivity implements BtnConnexionAccepterClickListener, BtnConnexionRefuserClickListener, BtnEvenementAccepterClickListener, BtnEvenementRefuserClickListener {
    private RecyclerView mRecyclerViewC;
    private AdapterInvitationConnexion mAdapterC;
    private RecyclerView.LayoutManager mLayoutManagerC;
    private RecyclerView mRecyclerViewE;
    private AdapterInvitationEvenement mAdapterE;
    private RecyclerView.LayoutManager mLayoutManagerE;


    private InvitationConnexionBDD invitationConnexionBDD;
    private InvitationEvenementBDD invitationEvenementBDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_invitations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mRecyclerViewC = (RecyclerView) findViewById(R.id.rv_invc);

        //Permet l'optimisation des performances
        // Dans le cas où l'on sait que la taille du layout RecyclerView ne va pas changer

        mRecyclerViewC.setHasFixedSize(true);

        // Utilisation d'un linear LayoutManager
        mLayoutManagerC = new LinearLayoutManager(this);
        mRecyclerViewC.setLayoutManager(mLayoutManagerC);

        UtilisateurBDD utilisateurBDD = new UtilisateurBDD(this);
        utilisateurBDD.open();
        Utilisateur utilisateur = utilisateurBDD.obtenirProfil();
        utilisateurBDD.close();

        invitationConnexionBDD = new InvitationConnexionBDD(this);
        invitationConnexionBDD.open();
        ArrayList<InvitationConnexion> listeInvitationConnexion = invitationConnexionBDD.obtenirInvitationUtilisateur(utilisateur);
        // Spécification de l'adapter
        ArrayList<InvitationConnexion> myDataset = new ArrayList<>();
        mAdapterC = new AdapterInvitationConnexion(listeInvitationConnexion, this, this);
        mRecyclerViewC.setAdapter(mAdapterC);
        invitationConnexionBDD.close();
        //------------------------------
        // Evenements
        //------------------------------

        mRecyclerViewE = (RecyclerView) findViewById(R.id.rv_inve);

        //Permet l'optimisation des performances
        // Dans le cas où l'on sait que la taille du layout RecyclerView ne va pas changer

        mRecyclerViewE.setHasFixedSize(true);

        // Utilisation d'un linear LayoutManager
        mLayoutManagerE = new LinearLayoutManager(this);
        mRecyclerViewE.setLayoutManager(mLayoutManagerE);

        invitationEvenementBDD = new InvitationEvenementBDD(this);
        invitationEvenementBDD.open();
        ArrayList<InvitationEvenement> listeInvitationEvenement = invitationEvenementBDD.obtenirInvitationUtilisateur(utilisateur);

        // Spécification de l'adapter
        ArrayList<InvitationEvenement> myDataset2 = new ArrayList<>();
        mAdapterE = new AdapterInvitationEvenement(listeInvitationEvenement, this, this);
        mRecyclerViewE.setAdapter(mAdapterE);
        invitationEvenementBDD.close();
    }

    @Override
    public void onBtnConnexionAccepterClick(InvitationConnexion invConnexion) {
        Log.d("Connexion acceptée", invConnexion.getExpediteur().getNom());
        //TODO : Traitement de l'acceptation
        mAdapterC.removeItem(invConnexion);
    }

    @Override
    public void onBtnConnexionRefuserClick(InvitationConnexion invConnexion) {
        Log.d("Connexion refusée", invConnexion.getExpediteur().getNom());
        //TODO : Traitement du refus
        mAdapterC.removeItem(invConnexion);
    }

    @Override
    public void onBtnEvenementAccepterClick(InvitationEvenement invEvenement) {
        Log.d("Evenement accepté", invEvenement.getEvenement().getNomEvenement());
        //TODO : Traitement de l'acceptation
        mAdapterE.removeItem(invEvenement);
    }

    @Override
    public void onBtnEvenementRefuserClick(InvitationEvenement invEvenement) {
        Log.d("Evenement refusé", invEvenement.getEvenement().getNomEvenement());
        //TODO : Traitement du refus
        mAdapterE.removeItem(invEvenement);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        //Gestion du clique sur le retour en arrière
        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
