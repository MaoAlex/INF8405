package com.example.alexmao.projetfinal.ActivitiesForTests;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.alexmao.projetfinal.Activites.ChoisirLieu;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.utils.ShakeEventListener;
import com.google.android.gms.maps.model.LatLng;

//import com.example.alexmao.projetfinal.utils.ShakeDetector;

public class GyroscopeTest extends AppCompatActivity {
    private SensorManager mSensorManager;

    private ShakeEventListener mSensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope_test);

        Intent intent=new Intent(GyroscopeTest.this,ChoisirLieu.class);
        startActivityForResult(intent, ChoisirLieu.ASK_LIEU);

//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        mSensorListener = new ShakeEventListener();
//
//        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
//
//            public void onShake() {
//                Toast.makeText(GyroscopeTest.this, "Shake!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ChoisirLieu.ASK_LIEU)
        {
            if(resultCode == RESULT_OK) {
                LatLng lieuChoisi = (LatLng) data.getParcelableExtra("lieu");
                Log.d("Lieu choisi", "Lat : " + String.valueOf(lieuChoisi.latitude) + ", Lng : " + String.valueOf(lieuChoisi.longitude));
            } else {
                Toast.makeText(this, "Vous devez choisir un lieu", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(GyroscopeTest.this,ChoisirLieu.class);
                startActivityForResult(intent, ChoisirLieu.ASK_LIEU);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mSensorManager.registerListener(mSensorListener,
//                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
//        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    /*// The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private long lastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope_test);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
				*//*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 *//*
                handleShakeEvent(count);
            }
        });
    }

    private void handleShakeEvent(int count) {
        if(count>1) {
            Date dateNow = new Date();
            long date = dateNow.getTime();
            if (date - lastUpdate > 2000) {
                Log.d("Shake", "Shake effectué");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }*/
}
