package com.example.alexmao.projetfinal.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.FromClassAppToEBDD;
import com.example.alexmao.projetfinal.BDDExterne.LocalUserProfilEBDD;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Sport;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexMAO on 24/04/2016.
 */
public class ChoixSport extends AppCompatActivity {
    private List<Sport> listeSports;
    private List<Sport> selectedSports;
    private boolean modeUnique;
    private RemoteBD remoteBD;
    public static final int ASK_SPORT = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_sports);
        //Récupération de la toolbar et mise en place
        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Mise en place de la flèche pour le retour en arrière
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadSports();
        selectedSports = new ArrayList<>();
        definirParametres(getIntent());
        adapterTransparence();
        remoteBD = new FireBaseBD(this);
    }

    private void definirParametres(Intent intent) {
        if(intent == null) {
            return;
        } else {
            if(intent.getBooleanExtra("estEvenement", false)) {
                modeUnique = true;
                // TODO : Pour trouver la position correspondant à la String du sport,
                // TODO : Sport.stringToPosition (key : String, element : Integer)
                // TODO : Ensuite l'objet Sport (listeSport(position) peut être ajouté à selectedSports())
            }
            else {
                modeUnique = false;
                // TODO : C'est un utilisateur : penser à récupérer les événements
            }
        }
    }

    private void loadSports() {
        listeSports = new ArrayList<>();
        Sport.initialize();
        for(int i=0; i<Sport.listeSports.length; i++) {
            Sport nouveauSport = new Sport(Sport.listeSports[i], Sport.listeIds[i]);
            listeSports.add(nouveauSport);
        }
    }

    private void adapterTransparence() {
        for(int i=0; i<listeSports.size(); i++) {
            Sport sport = listeSports.get(i);
            ImageView view = (ImageView) findViewById(listeSports.get(i).getSportId());
            view.setTag(sport);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectionner(v);
                }
            });
            if(!selectedSports.contains(sport)) {
                view.setAlpha((float)0.5);
            }
            else {
                view.setAlpha((float)1.0);
            }
        }
    }

    private void selectionner(View v) {
        Sport sport = (Sport) v.getTag();
        if(selectedSports.contains(sport)) {
            selectedSports.remove(sport);
            v.setAlpha((float)0.5);
        } else {
            if(modeUnique == true) {
                selectedSports = new ArrayList<>();
                decocherTout();
            }
            selectedSports.add(sport);
            v.setAlpha((float) 1.0);
        }
    }

    private void decocherTout() {
        for(int i=0; i<listeSports.size(); i++) {
            Sport sport = listeSports.get(i);
            ImageView view = (ImageView) findViewById(listeSports.get(i).getSportId());
            view.setAlpha((float)0.5);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_invitation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Gestion du clique sur le retour en arrière
        if(id == android.R.id.home)
            finish();
        if(id == R.id.menu_send)
            envoyerSports();

        return super.onOptionsItemSelected(item);
    }

    private void envoyerSports() {
        ArrayList<String> sports = new ArrayList<>();
        for(int i=0; i<selectedSports.size(); i++) {
           sports.add(selectedSports.get(i).getNom());
        }
        Intent intent = new Intent();
        intent.putStringArrayListExtra("sports", sports);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Rempli la liste des sports selectionnés à partir d'une liste de strings représentant les sports
     * @param listeStrings : la liste de strings de sports de l'utilisateur/événement (si événement 1 seul sport)
     */
    private void fromStringsToSelected(List<String> listeStrings) {
        for(int i=0; i<listeStrings.size(); i++) {
            Integer pos = Sport.stringToPosition.get(listeStrings.get(i));
            if(pos != null) {
                if(modeUnique) {
                    selectedSports = new ArrayList<>();
                }
                selectedSports.add(listeSports.get(pos));
            }
        }
        adapterTransparence();
    }

    //appeler pour mettre le profil utilisateur à jour
    private void pushModifications(Utilisateur utilisateur) {
        LocalUserProfilEBDD profilEBDD = new LocalUserProfilEBDD();
        FromClassAppToEBDD.translateUtilisateur(utilisateur, profilEBDD, null, null);
        remoteBD.updateUserProfil(utilisateur.getIdFirebase(), profilEBDD);
    }
}
