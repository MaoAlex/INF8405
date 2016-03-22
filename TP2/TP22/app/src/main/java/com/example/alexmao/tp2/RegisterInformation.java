package com.example.alexmao.tp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegisterInformation extends Activity {
    private static final String DEBUG_TAG = "RegisterInformation";
    private Spinner liste1 = null;
    private Spinner liste2 = null;
    private Spinner liste3 = null;

    final String EXTRA_PREFERENCE1 = "preference_1";
    final String EXTRA_PREFERENCE2 = "preference_2";
    final String EXTRA_PREFERENCE3 = "preference_3";
    final String EXTRA_EST_ORGANISATEUR = "est_organisateur";
    final String EXTRA_LOGIN = "user_login";
    final String EXTRA_PASSWORD = "user_password";
    final String EXTRA_GROUP = "user_group";
    boolean estOrganisateur = false;

    private String[] tableauChoix = {"", "restaurant", "bar", "pizzeria", "pub", "cafe" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_information);
        final UsersBDD userBDD = new UsersBDD(this);
        GroupeBDD groupeBDD = new GroupeBDD(this);

        userBDD.open();
        groupeBDD.open();
        User user = new User("Jean", "Paul");

        userBDD.insertUser(user);
        Log.d(DEBUG_TAG, "Insertion test reussi ");

        liste1 = (Spinner) findViewById(R.id.spinner_preference1);
        liste2 = (Spinner) findViewById(R.id.spinner_preference2);
        liste3 = (Spinner) findViewById(R.id.spinner_preference3);
        List<String> exemple = new ArrayList<String>();
        for(String s : tableauChoix){
            exemple.add(s);
        }
        List<String> exemple1 = exemple;
        List<String> exemple2 = exemple;

        //TODO Verifier l'utilisation des adaptateurs
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple);
        //Le layout par défaut est android.R.layout.simple_spinner_dropdown_item
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste1.setAdapter(adapter1);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple1);
        //Le layout par défaut est android.R.layout.simple_spinner_dropdown_item
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste2.setAdapter(adapter2);

        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple2);
        //Le layout par défaut est android.R.layout.simple_spinner_dropdown_item
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste3.setAdapter(adapter3);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        //On met par défaut non
        radioGroup.check(R.id.radioNo);
        //Recuperation de l'information sur l'organisateur du groupe

        final int checkedRadioButton = radioGroup.getCheckedRadioButtonId();// Verifier la valeur
        final String radioButtonSelected;
        switch (checkedRadioButton) {
            case R.id.radioNo : radioButtonSelected = "No";
                break;
            case R.id.radioYes : radioButtonSelected = "Yes";
                estOrganisateur = true;
                break;
        }

        liste1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                /*if(position!=0){
                    adapter2.remove(adapter2.getItem(adapter2.getPosition(adapter1.getItem(position))));
                   //
                    adapter3.remove(adapter3.getItem(adapter3.getPosition(adapter1.getItem(position))));
                }*/
                Toast.makeText(parent.getContext(),
                        ">> : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView arg0) {
                // rien du tout
            }
        });

        liste2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                /*if(position!=0){
                    adapter1.remove(adapter1.getItem(adapter1.getPosition(adapter2.getItem(position-1))));
                    adapter3.remove(adapter3.getItem(adapter3.getPosition(adapter2.getItem(position-1))));
                }*/
                Toast.makeText(parent.getContext(),
                        ">> : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView arg0) {
                // rien du tout
            }
        });
        liste3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
               /* if(position!=0){
                    adapter2.remove(adapter2.getItem(adapter2.getPosition(adapter3.getItem(position))));
                    adapter1.remove(adapter1.getItem(adapter1.getPosition(adapter3.getItem(position))));
                }*/
                Toast.makeText(parent.getContext(),
                        ">> : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView arg0) {
                // rien du tout
            }
        });

        final Button nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterInformation.this, PhotoActivity.class);
                String login = getIntent().getStringExtra(EXTRA_LOGIN);
                String password = getIntent().getStringExtra(EXTRA_PASSWORD);
                String group = getIntent().getStringExtra(EXTRA_GROUP);

                intent.putExtra(EXTRA_LOGIN, login);
                intent.putExtra(EXTRA_PASSWORD, password);
                intent.putExtra(EXTRA_GROUP, group);
                intent.putExtra(EXTRA_EST_ORGANISATEUR, checkedRadioButton);
                intent.putExtra(EXTRA_PREFERENCE1, liste1.getSelectedItem().toString());
                intent.putExtra(EXTRA_PREFERENCE2, liste2.getSelectedItem().toString());
                intent.putExtra(EXTRA_PREFERENCE3, liste3.getSelectedItem().toString());
                //Création de l'utilisateur et stockage dans la bdd
                User user;
                //creation de la liste de preference
                ArrayList<String> preference = new ArrayList<String>();
                preference.add(liste1.getSelectedItem().toString());
                preference.add(liste2.getSelectedItem().toString());
                preference.add(liste3.getSelectedItem().toString());
                if (estOrganisateur)
                    user = new User("Test", "BOIs", login, null, true, preference, null);
                else
                    user = new User("Test", "BOIs", login, null, false, preference, null);
                userBDD.insertUser(user);

                Log.d(DEBUG_TAG, "Insertion reussi de " +user.getNom() + ""+ user.toString());

                startActivity(intent);
                userBDD.close();
            }
        });


        }




}
