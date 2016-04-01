package com.example.alexmao.tp2final;

/**
 * Created by alexMAO on 22/03/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class AffichageGroupe extends Activity {

    ListView liste = null;
    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_groupes);

        liste = (ListView) findViewById(R.id.listView);

        List<String> exemple = new ArrayList<String>();

        exemple.add("Item 1");

        exemple.add("Item 2");

        exemple.add("Item 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exemple);

        liste.setAdapter(adapter);

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
}
