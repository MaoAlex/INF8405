package com.example.alexmao.projetfinal.Activites;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.alexmao.projetfinal.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class BatterieInformation extends AppCompatActivity {

    int mLevel;
    int mScale;
    double fileBatteryPct;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            mLevel = level;
            mScale = scale;
            updateBattery();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batterie_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Mise en place de la flèche pour le retour en arrière
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String filename = "batteryinfo";
        FileInputStream inputStream;
        StringBuffer data = new StringBuffer("");

        try {
            inputStream = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader ( inputStream ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                data.append(readString);
                readString = buffreader.readLine ( ) ;
            }
            isr.close();
            fileBatteryPct = Double.parseDouble(data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    }

    private void updateBattery() {
        TextView valeurAncienne = (TextView) findViewById(R.id.valeur_batterie_ancien);
        TextView valeurActuelle = (TextView) findViewById(R.id.valeur_batterie_actuelle);
        TextView valeurConsommation = (TextView) findViewById(R.id.valeur_batterie_consommation);

        double ancien = fileBatteryPct*100;
        double pctActuel = mLevel/(double)mScale*100;
        double consommation = ancien-pctActuel;
        valeurAncienne.setText(String.valueOf(ancien)+" %");
        valeurActuelle.setText(String.valueOf(pctActuel)+" %");
        valeurConsommation.setText(String.valueOf(consommation) + " %");
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mBatInfoReceiver);
    }

    protected void onResume() {
        super.onResume();
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Gestion du clique sur le retour en arrière
        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

}
