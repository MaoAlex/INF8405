package com.example.alexmao.projetfinal.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.alexmao.projetfinal.Adapter.AdapterUtilisateur;
import com.example.alexmao.projetfinal.BDDInterne.EvenementBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant l'affichage des participants d'un événement, d'un groupe
 */
public class AffichageParticipant extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Evenement evenement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_participant);

        //Récupération de la toolbar et mise en place
        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Mise en place de la flèche pour le retour en arrière
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String idFirebase = getIntent().getStringExtra("evenement");
        evenement = new Evenement();
        EvenementBDD evenementBDD = new EvenementBDD(this);
        evenementBDD.open();

        evenement = evenementBDD.obtenirEvenement(idFirebase);

        List<Utilisateur> myDataset = evenement.getGroupeAssocie().getListeMembre();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        //Permet l'optimisation des performances
        // Dans le cas où l'on sait que la taille du layout RecyclerView ne va pas changer

        mRecyclerView.setHasFixedSize(true);

        // Utilisation d'un linear LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Spécification de l'adapter
        mAdapter = new AdapterUtilisateur(myDataset);
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

}
