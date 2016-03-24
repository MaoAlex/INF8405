package com.example.alexmao.tp2final;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alexmao.tp2final.fragment.SearchFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VoteEvenement extends Activity implements IObserver {
    private ListView mListLieu = null;
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
        SearchFragment sF = new SearchFragment();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_evenement);
        //On récupère les trois vues définies dans notre layout
        PreferenceBDD preferenceBDD = new PreferenceBDD(this);
        preferenceBDD.open();
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
        sF.attach(this);
            LatLng centre = new LatLng(latitude, longitude);

        sF.doResearchByPreferences(centre,rayon,prefTab);//Mettre les bons arguments
        mListLieu = (ListView) findViewById(R.id.listLieu);

        mListDisponibilite = (ListView) findViewById(R.id.listHoraire);

        mSend = (Button) findViewById(R.id.envoyer);
        String choix1;
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
        Place choixPotentiel1 = listPlaces.get(choix1);
        Place choixPotentiel2 = listPlaces.get(choix2);
        Place choixPotentiel3 = listPlaces.get(choix3);
        while(choixPotentiel1!= null && prefTab[0]!= null && choixPotentiel1.getType()!=prefTab[0]) {
            choix2 = (new Random()).nextInt();
            choixPotentiel2 = listPlaces.get(choix2);

        }
        if(placeCalcule[1]!=null)
          placeCalcule[1] = choixPotentiel1;
        while(choixPotentiel2 != null && choixPotentiel2.getType()==choixPotentiel3.getType()){
            choix2 = (new Random()).nextInt();
            choixPotentiel2 = listPlaces.get(choix2);
        }
        if (placeCalcule[2]!=null)
            placeCalcule[2] = choixPotentiel2;
        while(choixPotentiel3 != null && choixPotentiel1.getType()==choixPotentiel3.getType()
                && choixPotentiel1.getType()==choixPotentiel3.getType()){
            choix2 = (new Random()).nextInt();
            choixPotentiel3 = listPlaces.get(choix2);
        }
        if (placeCalcule[3]!=null)
            placeCalcule[3] = choixPotentiel3;


    }
}

