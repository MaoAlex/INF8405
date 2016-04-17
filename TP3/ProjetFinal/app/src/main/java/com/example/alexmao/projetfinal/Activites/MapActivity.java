package com.example.alexmao.projetfinal.Activites;

import android.os.Bundle;
import com.example.alexmao.projetfinal.BDDExterne.OnPositionReceived;
import com.example.alexmao.projetfinal.BDDExterne.OnPositionReceivedForUser;
import com.example.alexmao.projetfinal.BDDExterne.Position;
import com.example.alexmao.projetfinal.MapResources.ConnectedMapActivity;
import com.example.alexmao.projetfinal.R;
import com.example.alexmao.projetfinal.classeApp.Groupe;
import com.example.alexmao.projetfinal.classeApp.Utilisateur;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapActivity extends ConnectedMapActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Map<String, Marker> idToMarkers;
    private Groupe currentGroup;
    private Utilisateur currentUser;
    private List<String> remoteUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        idToMarkers = new HashMap<>();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        currentUser = getIntent().getParcelableExtra("currentUser");
        setCurrentUserID(currentUser.getIdFirebase());
        currentGroup = getIntent().getParcelableExtra("currentGroup");
        remoteUserID = new ArrayList<>();
        //begin test
        currentUser = new Utilisateur();

        //end test
        for (Utilisateur utilisateur: currentGroup.getListeMembre()) {
            remoteUserID.add(utilisateur.getIdFirebase());
        }

        startPositionUpdateProcess(remoteUserID, new OnPositionReceivedForUser() {
            @Override
            public void onPositionReceivedForUser(Position position, String userID) {
                //change  user position (get user from id)
                int indexOf = remoteUserID.indexOf(userID);
                Utilisateur utilisateur = currentGroup.getListeMembre().get(indexOf);
                utilisateur.setLongitude(position.getLongitude());
                utilisateur.setLatitude(position.getLatitude());
                onPositionChanged(utilisateur);
            }
        }, new OnPositionReceived() {
            @Override
            public void onPostionReceived(Position position) {
                //change current user position
                currentUser.setLatitude(position.getLatitude());
                currentUser.setLongitude(position.getLongitude());
                onPositionChanged(currentUser);
            }
        });
    }

    //When the fragment is ready this method is called
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void onPositionChanged(Utilisateur utilisateur) {
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
