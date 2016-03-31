package com.example.alexmao.tp2final;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alexmao.tp2final.firebase.ConnectedMapActivity;
import com.example.alexmao.tp2final.fragment.SearchFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VoteEvenement extends ConnectedMapActivity implements IObserver {
    private static final String DEBUG_TAG = "VoteEvenement" ;
    private ListView mListLieu = null;
    private static SearchFragment searchFragment;
    /** Affichage de la liste des lieux pour le vote **/
    private ListView mListDisponibilite = null;
    /** Bouton pour envoyer le vote **/
    private Button mSend = null;
    /** Contient les lieux **/
    private ArrayList<String> mLieu = null;
    private ArrayList<Lieu> lieuCalcule = null;
    private String[] prefTab;
    private Place[] placeCalcule = new Place[3];
    private double latitude;
    private double longitude;
    /** Contient différents langages de programmation **/
    private final int rayon = 10000;//10km de rayon de recherche
    private ArrayList<String> mDispo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        searchFragment = new SearchFragment();
        Log.d(DEBUG_TAG, "On serachFragment est lancé");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_evenement);
        //On récupère les trois vues définies dans notre layout
        PreferenceBDD preferenceBDD = new PreferenceBDD(this);
        preferenceBDD.open();
        preferenceBDD.affichagePreferences();
        UsersBDD usersBDD = new UsersBDD(this);
        usersBDD.open();
        usersBDD.affichageUtilisateurConnecte();
        usersBDD.affichageUtilisateurConnecte();
        Log.d(DEBUG_TAG, "Affichage des utilisateurs");

        usersBDD.affichageUsers();
        User userTest = usersBDD.getProfil();
        Log.d(DEBUG_TAG, "L'id du de l'utilisateur courant est : " + userTest.getId());

        User userCourant;
        if(userTest!=null)
            userCourant = new User(userTest);
        else {
            Log.d(DEBUG_TAG, "Il n'y a pas d'utilisateur connecte");
            userCourant = new User(userTest);

        }

        LocalisationBDD localisationBDD = new LocalisationBDD(this);
        localisationBDD.open();
        //userCourant.setLocalisation_(localisationBDD.getlocalisationUser(userCourant.getId()));
        Localisation loc = new Localisation(41.756, 12.456);

        //Localisation loc = new Localisation(getmLatLng().latitude, getmLatLng().longitude);
        userCourant.setLocalisation_(loc);
        preferenceBDD.affichagePreferences();
        HashMap<String, ArrayList<User>> pref = preferenceBDD.getPreferences();
        ArrayList<Integer> preferenceOrdonne = new ArrayList<>();
        HashMap<Integer, String> classementPref = new HashMap<>();
        ArrayList<Integer> classement = new ArrayList<>();
        for (Map.Entry<String, ArrayList<User>> p : pref.entrySet()){
                classementPref.put(p.getValue().size(), p.getKey());
                classement.add(p.getValue().size());
            //On ne fait rien sinon car on propose déjà l'activité
        }
        Collections.sort(classement);
        if(classement.size()>2) {
            prefTab = new String[3];
        }else {
            prefTab = new String[classement.size()];
        }
        if(classement.get(0)!=null) {
            prefTab[0] = classementPref.get(classement.get(0));
        }
        if (classement.get(1) != null) {
            prefTab[1] = classementPref.get(classement.get(1));
        }
        if (classement.get(2) != null) {
            prefTab[2] = classementPref.get(classement.get(2));
        }
        VoteEvenement.searchFragment.attach(VoteEvenement.this);
        LatLng centre = new LatLng(userCourant.getLocalisation_().getPositionX_(), userCourant.getLocalisation_().getPositionY_());

        if(prefTab.length <1)
            Log.d(DEBUG_TAG, "Le talbeau des preferences est vide");
        Log.d(DEBUG_TAG, "taille du tableau prefTab : " + prefTab.length);


        VoteEvenement.searchFragment.doResearchByPreferences(centre, rayon, prefTab);//Mettre les bons arguments
        update(VoteEvenement.searchFragment.getListPlaces());
        mListLieu = (ListView) findViewById(R.id.listLieu);

        mListDisponibilite = (ListView) findViewById(R.id.listHoraire);

        Log.d(DEBUG_TAG, "taille du tableau placeCalcule : " + placeCalcule.length);
        if(placeCalcule[0]==null){
            Log.d(DEBUG_TAG, "Le tableau placeCacule contient des eleemtns nuls : " + placeCalcule.length);
        }

        mSend = (Button) findViewById(R.id.envoyer);
        String choix;
        mLieu = new ArrayList<>();
        for(int i =0; i< placeCalcule.length; i++) {
            choix = placeCalcule[0].getNom() + " - " + placeCalcule[0].getType();
            mLieu.add(choix);
        }
        //mDispo = new String[]{"Restaurant", "Bar", "Cafe", "Cinema"};
        //On ajoute un adaptateur qui affiche des boutons radio (c'est l'affichage à considérer quand on ne peut
        //sélectionner qu'un élément d'une liste)
        mListLieu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, mLieu));
        //On déclare qu'on sélectionne de base le premier élément ()
        mListLieu.setItemChecked(0, true);
        //On ajoute un adaptateur qui affiche des cases à cocher (c'est l'affichage à considérer quand on peut sélectionner
        //autant d'éléments qu'on veut dans une liste)
        mListDisponibilite.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, mDispo));
        //On déclare qu'on sélectionne de base le second élément ()
        mListDisponibilite.setItemChecked(1, true);
        //Que se passe-t-il dès qu'on clique sur le bouton ?
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VoteEvenement.this, "Merci ! Votre vote a été pris en compte !", Toast.LENGTH_LONG).show();
                //On déclare qu'on ne peut plus sélectionner d'élément
                mListLieu.setChoiceMode(ListView.CHOICE_MODE_NONE);
                //On affiche un layout qui ne permet pas de sélection
                mListLieu.setAdapter(new ArrayAdapter<String>(VoteEvenement.this, android.R.layout.simple_list_item_1,
                        mLieu));
                //On déclare qu'on ne peut plus sélectionner d'élément
                mListDisponibilite.setChoiceMode(ListView.CHOICE_MODE_NONE);
                //On affiche un layout qui ne permet pas de sélection
                mListDisponibilite.setAdapter(new ArrayAdapter<String>(VoteEvenement.this, android.R.layout.simple_list_item_1, mDispo));
                //On désactive le bouton
                mSend.setEnabled(false);
            }

        });
    }

    @Override
    public void update(ArrayList<Place> listPlaces) {
        Random r1 = new Random();
        Random r2 = new Random();
        Random r3 = new Random();
        int choix1 = r1.nextInt(listPlaces.size());
        int choix2 = r2.nextInt(listPlaces.size());
        int choix3 = r3.nextInt(listPlaces.size());
        Place choixPotentiel1 = new Place(listPlaces.get(choix1));
        Place choixPotentiel2 = new Place(listPlaces.get(choix2));
        Place choixPotentiel3 = new Place(listPlaces.get(choix3));
        while(choixPotentiel1!= null && prefTab[0]!= null && choixPotentiel1.getType()!=prefTab[0]) {
            choix2 = (new Random()).nextInt();
            choixPotentiel2 = listPlaces.get(choix2);

        }
        if(placeCalcule[1]!=null)
          placeCalcule[1] = new Place(choixPotentiel1);
        while(choixPotentiel2 != null && choixPotentiel2.getType()==choixPotentiel3.getType()){
            choix2 = (new Random()).nextInt();
            choixPotentiel2 = listPlaces.get(choix2);
        }
        if (placeCalcule[2]!=null)
            placeCalcule[2] = new Place(choixPotentiel2);
        while(choixPotentiel3 != null && choixPotentiel1.getType()==choixPotentiel3.getType()
                && choixPotentiel1.getType()==choixPotentiel3.getType()){
            choix3 = (new Random()).nextInt();
            choixPotentiel3 = listPlaces.get(choix3);
        }
        if (placeCalcule[3]!=null)
            placeCalcule[3] = new Place(choixPotentiel3);
    }
}

