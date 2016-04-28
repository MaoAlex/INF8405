package com.example.alexmao.projetfinal.Activites;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexmao.projetfinal.BDDExterne.FetchFullDataFromEBDD;
import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.FullUserWrapper;
import com.example.alexmao.projetfinal.BDDExterne.MyEventEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MyLocalGroupEBDD;
import com.example.alexmao.projetfinal.BDDExterne.OnEventReceived;
import com.example.alexmao.projetfinal.BDDExterne.OnGroupOnly;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDInterne.EvenementBDD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Evenement;
import com.example.alexmao.projetfinal.classeApp.Sport;

import java.util.List;

/**
 * Created by alexMAO on 24/04/2016.
 */
public class AffichageEvenement extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] myDataset = {"Elem1"};//}, "Elem2", "Elem3", "Elem4", "Elem5", "Elem6", "Elem7", "Elem8", "Elem9", "Elem10"};
    private RemoteBD remoteBD;
    private Evenement evenement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_evenement);
        //Récupération de la toolbar et mise en place
        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Mise en place de la flèche pour le retour en arrière
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Mise en place de l'image qui va se réduire
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Evenement");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        //Mise en place des éléments de test
        String idFirebase = getIntent().getStringExtra("evenement");
        Log.d("Affichage information", "if Firebase :" + idFirebase);
        evenement = new Evenement();
        EvenementBDD evenementBDD = new EvenementBDD(this);
        evenementBDD.open();

        evenement = evenementBDD.obtenirEvenement(idFirebase);

        Sport.initialize();

        TextView nomEvenement = (TextView) findViewById(R.id.nom_evenement);
        nomEvenement.setText(evenement.getNomEvenement());
        TextView nomSport = (TextView) findViewById(R.id.nom_sport);
        nomSport.setText(evenement.getSport());
        TextView nombreParticipants = (TextView) findViewById(R.id.nombre_participant);
        nombreParticipants.setText(String.valueOf(evenement.getGroupeAssocie().getListeMembre().size())+"/"+String.valueOf(evenement.getNbreMaxParticipants()));
        TextView nomLieu = (TextView) findViewById(R.id.nomLieu);
        nomLieu.setText(evenement.getLieu());
        ImageView image = (ImageView) findViewById(R.id.person_photo);
        image.setImageResource(Sport.stringToDrawable.get(evenement.getSport()));
        TextView organisateur = (TextView) findViewById(R.id.nom_organisateur);
        organisateur.setText(evenement.getOrganisateur().construireNom());
        TextView dateEvenement = (TextView) findViewById(R.id.date_evenement);
        dateEvenement.setText(evenement.construireDateEvt());
//        evenement.setNbreMaxParticipants(10);
//        Utilisateur u1 = new Utilisateur();
//        u1.setNom("Jean Paul");
//
//        GregorianCalendar test = new GregorianCalendar(2016, 03, 27);
//        evenement.setDate(test.getTimeInMillis());
//        evenement.setLieu("parc kent");
//        evenement.setNomEvenement("Fin de session");
//        evenement.setOrganisateur(u1);
//        evenement.setSport("foot");
//        Groupe g = new Groupe();
//
//        g.setListeMembre(new ArrayList<Utilisateur>());
//        g.getListeMembre().add(u1);
//
//        Utilisateur u2 = new Utilisateur();
//        u2.setNom("Poly Technique");
//        u2.setDateNaissance(test.getTimeInMillis());
//        Utilisateur u3 = new Utilisateur();
//        u3.setNom("Mont Real");
//        u3.setDateNaissance(test.getTimeInMillis());
//        g.getListeMembre().add(u2);
//        g.getListeMembre().add(u3);
//        evenement.setGroupeAssocie(g);

        //getIntent().getParcelableExtra("evenement");

        String eventID = evenement.getIdFirebase();
        remoteBD = new FireBaseBD(this);
        remoteBD.getEvent(eventID, new OnEventReceived() {
            @Override
            public void onEventReceived(MyEventEBDD myEventEBDD) {
                onEventReceive(myEventEBDD);
            }
        });
    }

    private void onEventReceive(final MyEventEBDD myEventEBDD) {
        FetchFullDataFromEBDD.fetchGroupOnly(myEventEBDD.getGroupID(), remoteBD, new OnGroupOnly() {
            @Override
            public void onGroupOnly(MyLocalGroupEBDD myLocalGroupEBDD, List<FullUserWrapper> wrappers) {
                onInformationUpToDate(myEventEBDD, myLocalGroupEBDD, wrappers);
            }
        });
    }

    private void onInformationUpToDate(MyEventEBDD myEventEBDD,
                                       MyLocalGroupEBDD myLocalGroupEBDD,
                                       List<FullUserWrapper> wrappers ) {
        //TODO update la BD interne
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public void onBackPressed()
    {
        finish();
    }
}
