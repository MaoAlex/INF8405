package com.example.alexmao.projetfinal.MapResources;

/**
 * Created by filou on 05/03/16.
 */

/*
 * gère l'envoie de données sur le serveur pour les modification de position
 */

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.LocalUserProfilEBDD;
import com.example.alexmao.projetfinal.BDDExterne.OnPositionReceived;
import com.example.alexmao.projetfinal.BDDExterne.OnPositionReceivedForUser;
import com.example.alexmao.projetfinal.BDDExterne.Position;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Map;

public class ConnectedMapActivity extends AppCompatActivity implements
        ConnectionCallbacks, LocationListener {

    private static final String TAG = "ConnectedMapActivity";

    //le serveur à joindre
    protected RemoteBD myRemoteBD;

    // Google API - Locations
    private GoogleApiClient mGoogleApiClient;

    // lattitude and longitude
    private LatLng mLatLng;

    //id de l'utilisateur courant
    protected String currentUserID;

    //callback appelée lorsque la position de l'utilisateur courant est modifiée
    private OnPositionReceived onPositionReceived;

    // =============================================================================================
    // Activity Life Cycle
    // =============================================================================================
    private synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this).addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Start Google Client
        this.buildGoogleApiClient();
        Log.d(TAG, "onCreate: google API started");
        myRemoteBD = new FireBaseBD(this);
        //start database
        Log.d(TAG, "onCreate: BD started");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    protected void startLocationUpdates() {
        Log.d(TAG, "startLocationUpdates: ");
        LocationRequest mLocationRequest = createLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }
    // =============================================================================================
    // Google Location API CallBacks
    // =============================================================================================

    //appelée lorsque la position a changé
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location Detected");
        mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (currentUserID != null) {
            Position position = new Position(location.getLatitude(), location.getLongitude());
            myRemoteBD.addPositionToUser(currentUserID, position);

            if (onPositionReceived != null)
                onPositionReceived.onPostionReceived(position);
        }
    }

    //forge une requête pour récupérer la position courante
    private LocationRequest createLocationRequest() {
        Log.d(TAG, "Building request");
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return mLocationRequest;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "Connected to Google API for Location Management");
        LocationRequest mLocationRequest = createLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection to Google API suspended");
    }

    public void setOnPositionReceived(OnPositionReceived onPositionReceived) {
        this.onPositionReceived = onPositionReceived;
    }

    /**
     * configure les callback pour qu'elles soient appelées lorsque la position est modifiée
     * @param membersID ids firebase des membres du groupe
     * @param remoteUserCallback la callback appelée lorsque la position d'un utilisateur distant a changé
     * @param curentUserCallback la callback appelée lorsque l'utilisateur courant change de position
     */
    public void startPositionUpdateProcess(List<String> membersID,
                                           OnPositionReceivedForUser remoteUserCallback,
                                           OnPositionReceived curentUserCallback) {
        this.onPositionReceived = curentUserCallback;
        for (String id : membersID) {
            if (!id.equals(currentUserID)) {
                myRemoteBD.listenToPositionChanges(id, remoteUserCallback);
            }
        }
    }

    public void startPositionUpdateProcess(String userID, OnPositionReceived curentUserCallback) {
        this.onPositionReceived = curentUserCallback;
        this.currentUserID = userID;
    }

    public void setCurrentUserID(String currentUserID) {
        this.currentUserID = currentUserID;
    }
}
