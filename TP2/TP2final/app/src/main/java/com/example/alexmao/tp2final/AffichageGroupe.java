package com.example.alexmao.tp2final;

/**
 * Created by alexMAO on 22/03/2016.
 */

import android.app.Activity;
import android.os.Bundle;
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

}
