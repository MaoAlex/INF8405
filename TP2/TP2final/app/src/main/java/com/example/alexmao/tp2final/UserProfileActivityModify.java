package com.example.alexmao.tp2final;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.alexmao.tp2final.firebase.ConnectedMapActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexMAO on 22/03/2016.
 */
public class UserProfileActivityModify extends ConnectedMapActivity{
    private static final String DEBUG_TAG = "UserProfileModify";
    private String[] tableauChoix = {"", "Bar", "Cafe", "Restaurant", "Parc", "Cinema", "Musee" };
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
        liste1 = (Spinner) findViewById(R.id.spinner_preference1);
        liste2 = (Spinner) findViewById(R.id.spinner_preference2);
        liste3 = (Spinner) findViewById(R.id.spinner_preference3);
        final Button loginButton = (Button) findViewById(R.id.enregistrer);
        final UsersBDD userBDD = new UsersBDD(this);
        GroupeBDD groupeBDD = new GroupeBDD(this);
        userBDD.open();
        groupeBDD.open();
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
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                User u = userBDD.getProfil();
                /*On doit faire ici la mise à jour de l'utilisateur, mais du à un bug j'ai enleve le code*/
                finish();

            }
        });
        liste1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                /*if(position!=0){
                    adapter2.remove(adapter2.getItem(adapter2.getPosition(adapter1.getItem(position))));
                   //
                    adapter3.remove(adapter3.getItem(adapter3.getPosition(adapter1.getItem(position))));
                }*/
               /* Toast.makeText(parent.getContext(),
                        ">> : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();*/
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
               /* Toast.makeText(parent.getContext(),
                        ">> : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();*/
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
               /* Toast.makeText(parent.getContext(),
                        ">> : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();*/
            }

            public void onNothingSelected(AdapterView arg0) {
                // rien du tout
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();

        //R.menu.menu est l'id de notre menu
        inflater.inflate(R.menu.menu_connecte, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {

        switch(item.getItemId())
        {
            case R.id.action_user_profile:
                Intent intentUP = new Intent(getApplicationContext(), UserProfileActivity.class);
                intentUP.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentUP);
                return true;
            case R.id.action_logout:
                Intent intentLO = new Intent(getApplicationContext(), MainActivity.class);
                intentLO.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentLO);
                return true;
            case R.id.action_home_connecte:
                Intent intentHC = new Intent(getApplicationContext(), MainActivity_Home.class);
                intentHC.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentHC);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
