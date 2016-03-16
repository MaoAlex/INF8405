package worktest.filou.activitymaptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
    private final String TAG = "GroupTestActivity";
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
                Log.d(TAG, "onChange: " + "group change");
            }
        });

        idToMarkers = new HashMap<>();
        localUsers = new LinkedList<>();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        RemoteBD remoteBD = getMyRemoteBD();
        remoteBD.getGroup(myLocalGroup.getDatabaseID(), myLocalGroup);
    }

    private void setupGroup(MyLocalGroup myLocalGroup) {
        List<String> ids = myLocalGroup.getMembersID();
        RemoteBD remoteBD = getMyRemoteBD();
        for (String id : ids) {
            if (id.equals(getLocalUser().getDataBaseId())) {
                localUser.setChangeListener(new LocalUser.ChangeListener() {
                    @Override
                    public void onPositionChanged(LocalUser localUser) {
                        addMarker(localUser);
                    }
                });
                localUsers.add(localUser);
                remoteBD.listenToChangeOnUser(localUser, localUser.getDataBaseId());
            } else {
                LocalUser localUserFromRemote = new LocalUser();
                localUserFromRemote.setDataBaseId(id);

                localUserFromRemote.setChangeListener(new LocalUser.ChangeListener() {
                    @Override
                    public void onPositionChanged(LocalUser localUser) {
                        onUserCreated(localUser);
                    }
                });

                remoteBD.listenToChangeOnUser(localUserFromRemote, localUserFromRemote.getDataBaseId());
                remoteBD.getUser(id, localUserFromRemote);
                localUsers.add(localUserFromRemote);
            }
        }
    }

    //called when an user is ready
    private void onUserCreated(LocalUser localUser) {
        RemoteBD remoteBD = getMyRemoteBD();
        localUser.setChangeListener(new LocalUser.ChangeListener() {
            @Override
            public void onPositionChanged(LocalUser localUser) {
                addMarker(localUser);
            }
        });

        remoteBD.listenToChangeOnUser(localUser, localUser.getDataBaseId());

        addMarker(localUser);
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
