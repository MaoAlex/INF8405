package com.example.alexmao.projetfinal.Activites;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alexmao.projetfinal.Adapter.AdapterUtilisateur;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;

/**
 * Classe permettant l'affichage des participants d'un événement, d'un groupe
 */
public class AffichageParticipant extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_participant);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);

        //Permet l'optimisation des performances
        // Dans le cas où l'on sait que la taille du layout RecyclerView ne va pas changer

        mRecyclerView.setHasFixedSize(true);

        // Utilisation d'un linear LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Spécification de l'adapter
        ArrayList<Utilisateur> myDataset = new ArrayList<>();
        mAdapter = new AdapterUtilisateur(myDataset);
        mRecyclerView.setAdapter(mAdapter);

    }

}
