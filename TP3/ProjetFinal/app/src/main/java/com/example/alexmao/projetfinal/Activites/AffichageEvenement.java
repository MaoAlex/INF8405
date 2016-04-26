package com.example.alexmao.projetfinal.Activites;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.Groupe;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by alexMAO on 24/04/2016.
 */
public class AffichageEvenement extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] myDataset = {"Elem1"};//}, "Elem2", "Elem3", "Elem4", "Elem5", "Elem6", "Elem7", "Elem8", "Elem9", "Elem10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_evenement);

        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Evenement");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

//        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//
//        mRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        mAdapter = new MyAdapter(myDataset);
//        mRecyclerView.setAdapter(mAdapter);
        String idFirebase = getIntent().getStringExtra("evenement");
        Evenement evenement = new Evenement();
        evenement.setNbreMaxParticipants(10);
        Utilisateur u1 = new Utilisateur();
        u1.setNom("Jean Paul");

        GregorianCalendar test = new GregorianCalendar(2016, 03, 27);
        evenement.setDate(test.getTimeInMillis());
        evenement.setLieu("parc kent");
        evenement.setNomEvenement("Fin de session");
        evenement.setOrganisateur(u1);
        evenement.setSport("foot");
        Groupe g = new Groupe();

        g.setListeMembre(new ArrayList<Utilisateur>());
        g.getListeMembre().add(u1);

        Utilisateur u2 = new Utilisateur();
        u2.setNom("Poly Technique");
        u2.setDateNaissance(test.getTimeInMillis());
        Utilisateur u3 = new Utilisateur();
        u3.setNom("Mont Real");
        u3.setDateNaissance(test.getTimeInMillis());
        g.getListeMembre().add(u2);
        g.getListeMembre().add(u3);
        evenement.setGroupeAssocie(g);

        //getIntent().getParcelableExtra("evenement");
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
