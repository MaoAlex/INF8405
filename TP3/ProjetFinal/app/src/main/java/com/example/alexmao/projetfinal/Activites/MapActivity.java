package com.example.alexmao.projetfinal.Activites;

import android.os.Bundle;

import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.MyLocalGroupEBDD;
import com.example.alexmao.projetfinal.BDDExterne.OnPositionReceived;
import com.example.alexmao.projetfinal.BDDExterne.Position;
import com.example.alexmao.projetfinal.MapResources.ConnectedMapActivity;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class MapActivity extends ConnectedMapActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Map<String, Marker> idToMarkers;
    private MyLocalGroupEBDD currentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        idToMarkers = new HashMap<>();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        currentUser = getIntent().getParcelableExtra("currentUser");
        currentGroup = getIntent().getParcelableExtra("currentGroup");

        myRemoteBD = new FireBaseBD(this);
        startPositionUpdateProcess(currentGroup.getMembersID(),
                new OnPositionReceived() {
            @Override
            public void onPostionReceived(Position position) {
                //must be call on a Utilisateur from classapp
                //onPositionChanged(Utilisateur utilisateur);
            }
        }, new OnPositionReceived() {
            @Override
            public void onPostionReceived(Position position) {
                //change current user position
                //onPositionChanged(Utilisateur currentUtilisateur);
            }
        });
    }

    //When the fragment is ready this method is called
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void onPositionChanged(Utilisateur utilisateur) {
        //TODO update position in class

        //add a marker on the map
        addMarker(utilisateur);
    }

    //add a marker on the map
    private void addMarker(Utilisateur user) {
        if (user != null) {
            Marker marker = idToMarkers.get(user.getMail());
            if (marker != null)
                marker.remove();

            idToMarkers.put(user.getMail(), createMarker(user));
        }
    }

    //create a marker, it must be add to the map
    private Marker createMarker(Utilisateur user) {
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(user.getLatitude(), user.getLongitude()))
                .title(user.getPrenom() + " " + user.getNom()));

        return marker;
    }
}
