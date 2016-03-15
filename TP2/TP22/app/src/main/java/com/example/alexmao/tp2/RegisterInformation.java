package com.example.alexmao.tp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    private Spinner liste1 = null;
    private Spinner liste2 = null;
    private Spinner liste3 = null;

    final String EXTRA_PREFERENCE1 = "preference_1";
    final String EXTRA_PREFERENCE2 = "preference_2";
    final String EXTRA_PREFERENCE3 = "preference_3";
    final String EXTRA_EST_ORGANISATEUR = "est_organisateur";
    private String[] tableauChoix = {"", "restaurant", "bar", "pizzeria", "pub", "cafe" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_information);

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

        final int checkedRadioButton = radioGroup.getCheckedRadioButtonId();
        final String radioButtonSelected;
        switch (checkedRadioButton) {
            case R.id.radioNo : radioButtonSelected = "No";
                break;
            case R.id.radioYes : radioButtonSelected = "Yes";
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
                intent.putExtra(EXTRA_EST_ORGANISATEUR, checkedRadioButton);
                intent.putExtra(EXTRA_PREFERENCE1, liste1.getSelectedItem().toString());
                intent.putExtra(EXTRA_PREFERENCE2, liste2.getSelectedItem().toString());
                intent.putExtra(EXTRA_PREFERENCE3, liste3.getSelectedItem().toString());

                startActivity(intent);
            }
        });
        }




}
