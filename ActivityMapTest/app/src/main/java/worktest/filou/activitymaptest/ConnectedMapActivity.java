package worktest.filou.activitymaptest;

/**
 * Created by filou on 05/03/16.
 */

/*
 * gère l'envoie de données sur le serveur
 */
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;

public class ConnectedMapActivity extends AppCompatActivity implements
        ConnectionCallbacks, LocationListener {

    private static final String TAG = "ConnectedMapActivity";

    boolean mustUpdateFromServer = false;

    //Needed to access the server
    private RemoteBD myRemoteBD;

    // Google API - Locations
    private GoogleApiClient mGoogleApiClient;

    // lattitude and longitude
    private LatLng mLatLng;

    //user Data
    protected LocalUser localUser;

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

        //start database
        myRemoteBD = new FireBaseBD(this);
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
        LocationRequest mLocationRequest = createLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }
    // =============================================================================================
    // Google Location API CallBacks
    // =============================================================================================

    //called when local location is changed
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location Detected");
        mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (localUser != null) {
            localUser.setLat(mLatLng.latitude);
            Log.d(TAG, "new latitude " + localUser.getLat());
            localUser.setLongi(mLatLng.longitude);
            Log.d(TAG, "new longitude " + localUser.getLongi());
            localUser.update();
            //send new location to server
            myRemoteBD.updateLocationOnServer(localUser, localUser.getDataBaseId());
        }
    }

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

    public boolean isMustUpdateFromServer() {
        return mustUpdateFromServer;
    }

    public RemoteBD getMyRemoteBD() {
        return myRemoteBD;
    }

    public LatLng getmLatLng() {
        return mLatLng;
    }

    public void setMustUpdateFromServer(boolean mustUpdateFromServer) {
        this.mustUpdateFromServer = mustUpdateFromServer;
    }

    public LocalUser getLocalUser() {
        return localUser;
    }

    public void setLocalUser(LocalUser localUser) {
        this.localUser = localUser;
    }
}
