package com.example.alexmao.projetfinal.Activites;

import android.os.Bundle;

import com.example.alexmao.projetfinal.BDDExterne.FromClassAppToEBDD;
import com.example.alexmao.projetfinal.BDDExterne.LocalUserProfilEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MyLocalGroupEBDD;
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
        //TODO decommenter à la fin des tests
//        setCurrentUserID(currentUser.getIdFirebase());
        currentGroup = getIntent().getParcelableExtra("currentGroup");
        remoteUserID = new ArrayList<>();
        //TODO Supprimmer la partie test pour la version finale
        //begin test
        //current user
        currentUser = new Utilisateur();
        currentUser.setNom("user");
        currentUser.setPrenom("current");
        currentUser.setMail("user@current.fr");
        LocalUserProfilEBDD profilEBDD  = new LocalUserProfilEBDD();
        FromClassAppToEBDD.translateUtilisateur(currentUser, profilEBDD, null, null);
        String currentID = myRemoteBD.addUserProfil(profilEBDD);
        profilEBDD.setDataBaseId(currentID);
        currentUser.setIdFirebase(currentID);
        setCurrentUserID(currentUser.getIdFirebase());

        //remote user
        Utilisateur remoteUser = new Utilisateur();
        remoteUser.setNom("user");
        remoteUser.setPrenom("remote");
        remoteUser.setMail("user@remote.fr");
        LocalUserProfilEBDD remoteProfilEBDD  = new LocalUserProfilEBDD();
        FromClassAppToEBDD.translateUtilisateur(remoteUser, remoteProfilEBDD, null, null);
        String remoteID = myRemoteBD.addUserProfil(remoteProfilEBDD);
        profilEBDD.setDataBaseId(remoteID);
        remoteUser.setIdFirebase(remoteID);

        //group
        currentGroup = new Groupe();
        List<Utilisateur> utilisateurs = new ArrayList<>();
        utilisateurs.add(currentUser);
        utilisateurs.add(remoteUser);
        currentGroup.setListeMembre(utilisateurs);
        List<String> ids = new ArrayList<>();
        for (Utilisateur utilisateur: utilisateurs) {
            ids.add(utilisateur.getIdFirebase());
        }
        MyLocalGroupEBDD groupEBDD = new MyLocalGroupEBDD();
        groupEBDD.setMembersID(ids);
        String groupID = myRemoteBD.addGroup(groupEBDD);
        groupEBDD.setDatabaseID(groupID);
        currentGroup.setIdFirebase(groupID);
        //end test

        for (Utilisateur utilisateur: currentGroup.getListeMembre()) {
            remoteUserID.add(utilisateur.getIdFirebase());
        }

        startPositionUpdateProcess(remoteUserID, new OnPositionReceivedForUser() {
            @Override
            public void onPositionReceivedForUser(Position position, String userID) {
                //met à jour la position de l'utilisateur identifié par userID
                if (position == null) {
                    //par défaut la position est (0,0)
                    position = new Position();
                }
                //mise à jour de la position
                int indexOf = remoteUserID.indexOf(userID);
                Utilisateur utilisateur = currentGroup.getListeMembre().get(indexOf);
                utilisateur.setLongitude(position.getLongitude());
                utilisateur.setLatitude(position.getLatitude());

                //on informe l'activité du chagement de position
                onPositionChanged(utilisateur);
            }
        }, new OnPositionReceived() {
            @Override
            public void onPostionReceived(Position position) {
                if (position == null) {
                    //par défaut la position est (0,0)
                    position = new Position();
                }
                //mise à jour de la position
                currentUser.setLatitude(position.getLatitude());
                currentUser.setLongitude(position.getLongitude());

                //on informe l'activité du chagement de position
                onPositionChanged(currentUser);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //save the new position in the intern Database
    }

    //Est appelée quand le fragment est prêt
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    //appelée lorsqu'un utilisateur change de position
    private void onPositionChanged(Utilisateur utilisateur) {
        //on met à jour l'affichage
        addMarker(utilisateur);
    }

    //ajoute ou modifie la position du marker sur la map
    private void addMarker(Utilisateur user) {
        if (user != null) {
            Marker marker = idToMarkers.get(user.getMail());
            //si l'utilisateur a déjà un marker on l'enlève
            if (marker != null)
                marker.remove();

            //création d'un nouveau marker et ajout à la map
            idToMarkers.put(user.getMail(), createMarker(user));
        }
    }

    //crée et ajoute un marker sur la map
    private Marker createMarker(Utilisateur user) {
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(user.getLatitude(), user.getLongitude()))
                .title(user.getPrenom() + " " + user.getNom()));

        return marker;
    }
}
