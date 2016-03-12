package worktest.filou.activitymaptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GroupTestActivity extends ConnectedMapActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Map<String, Marker> idToMarkers;
    private MyLocalGroup myLocalGroup;
    private List<LocalUser> localUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocalUser((LocalUser) getIntent().getParcelableExtra("localUser"));
        setContentView(R.layout.activity_map);

        myLocalGroup = new MyLocalGroup();
        myLocalGroup.setDatabaseID(getIntent().getStringExtra("groupID"));
        myLocalGroup.setChangeListener(new MyLocalGroup.ChangeListener() {
            @Override
            public void onChange(MyLocalGroup myLocalGroup) {
                setupGroup(myLocalGroup);
            }
        });

        idToMarkers = new HashMap<>();
        localUsers = new LinkedList<>();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocalUser().setChangeListener(new LocalUser.ChangeListener() {
            @Override
            public void onPositionChanged(LocalUser localUser) {
                addMarker(localUser);
            }
        });
    }

    private void setupGroup(MyLocalGroup myLocalGroup) {
        List<String> ids = myLocalGroup.getMembersID();
        RemoteBD remoteBD = getMyRemoteBD();
        for (String id : ids) {
            LocalUser localUser = new LocalUser();
            localUser.setDataBaseId(id);

            localUser.setChangeListener(new LocalUser.ChangeListener() {
                @Override
                public void onPositionChanged(LocalUser localUser) {
                    addMarker(localUser);
                }
            });

            remoteBD.getUser(id, localUser);
            remoteBD.listenToChangeOnUser(localUser, id);
            localUsers.add(localUser);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LocalUser localUser = getLocalUser();
        if (localUser != null) {
            Marker marker = createMarker(localUser);
            idToMarkers.put(localUser.getMailAdr(), marker);
        }
    }


    private void addMarker(LocalUser user) {
        if (user != null) {
            Marker marker = idToMarkers.get(user.getMailAdr());
            if (marker != null)
                marker.remove();

            idToMarkers.put(user.getMailAdr(), createMarker(user));
        }
    }

    private Marker createMarker(LocalUser user) {

        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(user.getLat(), user.getLongi()))
                .title(user.getFirstName() + " " + user.getLastName()));

        return marker;
    }
}
