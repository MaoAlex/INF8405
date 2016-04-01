package com.example.alexmao.tp2final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alexMAO on 14/03/2016.
 */
public class UserProfileActivity extends Activity {
    private TextView mail;
    private TextView nom;
    private TextView groupName;
    private TextView pref1;
    private ImageView photoProfil;
    private TextView pref3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        UsersBDD usersBDD = new UsersBDD(this);
        usersBDD.open();
        User u = usersBDD.getProfil();
        GroupeBDD groupeBDD = new GroupeBDD(this);
        groupeBDD.open();
        PreferenceBDD preferenceBDD = new PreferenceBDD(this);
        preferenceBDD.open();
        ArrayList<String > listNomGroupe = groupeBDD.getGroupesByUserId(u.getId());
        User user = usersBDD.getProfil();
        ArrayList<String> p = preferenceBDD.getPreferencesForUser(user.getId());
        mail =  (TextView) findViewById(R.id.email_display);
        nom = (TextView)  findViewById(R.id.nom);
        groupName = (TextView)  findViewById(R.id.group_name);
        pref1 = (TextView) findViewById(R.id.preference);
        photoProfil = (ImageView) findViewById(R.id.photo_profil);

        photoProfil.setImageURI(user.getPhoto());
        mail.setHint(user.getMail_());
        nom.setHint(user.getNom());
        String pref = "";
        for(String s : p){
            pref.concat(" ");
            pref.concat(s);
            Log.d("User", pref);
        }

        String groupN = "";
        for(String s : listNomGroupe){
            groupN.concat(" ");
            groupN.concat(s);
            Log.d("groupe nom ",pref);
        }
        pref1.setHint(pref);
        groupName.setHint(groupN);
        final Button modifierButton = (Button) findViewById(R.id.modifier);
        modifierButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, UserProfileActivityModify.class);
                startActivity(intent);
            }

        });

        final Button deconnexionButton = (Button) findViewById(R.id.deconnexion);
        deconnexionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                //startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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


}