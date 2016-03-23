package com.example.alexmao.tp2final;

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

import com.example.alexmao.tp2final.firebase.ConnectedMapActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexMAO on 22/03/2016.
 */
public class UserProfileActivityModify extends ConnectedMapActivity{
    private static final String DEBUG_TAG = "UserProfileModify";
    private String[] tableauChoix = {"", "restaurant", "bar", "pizzeria", "pub", "cafe" };
    private Spinner liste1 = null;
    private Spinner liste2 = null;
    private Spinner liste3 = null;

    final String EXTRA_PREFERENCE1 = "preference_1";
    final String EXTRA_PREFERENCE2 = "preference_2";
    final String EXTRA_PREFERENCE3 = "preference_3";


    boolean estOrganisateur = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_edit_mode);

        final Button loginButton = (Button) findViewById(R.id.enregistrer);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivityModify.this, UserProfileActivity.class);
                startActivity(intent);

            }
        });

        final UsersBDD userBDD = new UsersBDD(this);
        GroupeBDD groupeBDD = new GroupeBDD(this);

        userBDD.open();
        groupeBDD.open();
        User user = new User("Jean", "Paul");

        userBDD.insertUser(user);
        Log.d(DEBUG_TAG, "Insertion test reussi ");

        liste1 = (Spinner) findViewById(R.id.edit_spinner_preference1);
        liste2 = (Spinner) findViewById(R.id.edit_spinner_preference2);
        liste3 = (Spinner) findViewById(R.id.edit_spinner_preference3);
        List<String> exemple = new ArrayList<String>();
        for(String s : tableauChoix){
            exemple.add(s);
        }
        List<String> exemple1 = exemple;
        List<String> exemple2 = exemple;

        //TODO Verifier l'utilisation des adaptateurs
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple);
        //Le layout par dÃ©faut est android.R.layout.simple_spinner_dropdown_item
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste1.setAdapter(adapter1);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple1);
        //Le layout par dÃ©faut est android.R.layout.simple_spinner_dropdown_item
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste2.setAdapter(adapter2);

        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple2);
        //Le layout par dÃ©faut est android.R.layout.simple_spinner_dropdown_item
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste3.setAdapter(adapter3);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        //On met par dÃ©faut non
        //radioGroup.check(R.id.radioNo);
        //Recuperation de l'information sur l'organisateur du groupe
        /*
        final int checkedRadioButton = radioGroup.getCheckedRadioButtonId();// Verifier la valeur
        final String radioButtonSelected;
        switch (checkedRadioButton) {
            case R.id.radioNo : radioButtonSelected = "No";
                break;
            case R.id.radioYes : radioButtonSelected = "Yes";
                estOrganisateur = true;
                break;
        }
*/
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

    }
}
