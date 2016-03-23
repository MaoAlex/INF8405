package com.example.alexmao.tp2final;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class VoteEvenement extends Activity {
    private ListView mListLieu = null;
    /** Affichage de la liste des lieux pour le vote **/
    private ListView mListDisponibilite = null;
    /** Bouton pour envoyer le vote **/
    private Button mSend = null;
    /** Contient les lieux **/
    private String[] mLieu = {"ici", "la bas"};

    /** Contient différents langages de programmation **/

    private String[] mLangages = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_evenement);
        //On récupère les trois vues définies dans notre layout

        mListLieu = (ListView) findViewById(R.id.listLieu);

        mListDisponibilite = (ListView) findViewById(R.id.listHoraire);

        mSend = (Button) findViewById(R.id.envoyer);

        mLangages = new String[]{"Restaurant", "Bar", "Cafe", "Cinema"};


        //On ajoute un adaptateur qui affiche des boutons radio (c'est l'affichage à considérer quand on ne peut

        //sélectionner qu'un élément d'une liste)

        mListLieu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, mLieu));

        //On déclare qu'on sélectionne de base le premier élément ()

        mListLieu.setItemChecked(0, true);

        //On ajoute un adaptateur qui affiche des cases à cocher (c'est l'affichage à considérer quand on peut sélectionner

        //autant d'éléments qu'on veut dans une liste)

        mListDisponibilite.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, mLangages));

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
                mListDisponibilite.setAdapter(new ArrayAdapter<String>(VoteEvenement.this, android.R.layout.simple_list_item_1, mLangages));
                //On désactive le bouton
                mSend.setEnabled(false);
            }

        });

    }
}

