package worktest.filou.activitymaptest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends ConnectedMapActivity {
    private static final String TAG = "MainActivity";
    private Button gotoMapButton;
    private Button gotoGroupTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gotoMapButton = (Button) findViewById(R.id.goto_map_button);
        gotoMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMap();
            }
        });
        gotoGroupTest = (Button) findViewById(R.id.goto_group_test);
        gotoGroupTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoGroupTest(prepareGroupTest());
            }
        });
        prepareUser();
    }

    private void testCreateUser() {
        localUser = new LocalUser("fifi", "test", "exemple@polymtl.ca");
        RemoteBD remoteBD = getMyRemoteBD();
        saveID(localUser.getDataBaseId());
        String userBDID = remoteBD.addUser((User) localUser);
        localUser.setDataBaseId(userBDID);
        localUser.setChangeListener(new LocalUser.ChangeListener() {
            @Override
            public void onPositionChanged(LocalUser localUser) {
                getMyRemoteBD().updateLocationOnServer((User) localUser, localUser.getDataBaseId());
            }
        });
    }

    private String prepareGroupTest() {
        RemoteBD remoteBD = getMyRemoteBD();
        localUser = new LocalUser("fifi", "test1", "exemple1@polymtl.ca");
        String userBDID = remoteBD.addUser((User) localUser);
        localUser.setDataBaseId(userBDID);
        MyLocalGroup myLocalGroup = new MyLocalGroup("test group", localUser.getDataBaseId());


        localUser = new LocalUser("fifi", "test2", "exemple2@polymtl.ca");
        userBDID = remoteBD.addUser((User) localUser);
        localUser.setDataBaseId(userBDID);
        myLocalGroup.addMember(userBDID);


        localUser = new LocalUser("fifi", "test3", "exemple3@polymtl.ca");
        userBDID = remoteBD.addUser((User) localUser);
        localUser.setDataBaseId(userBDID);
        myLocalGroup.addMember(userBDID);

        return remoteBD.addGroup((MyGroup) myLocalGroup);
    }

    private void gotoMap() {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra("localUser", localUser);
        startActivity(intent);
    }

    private void gotoGroupTest(String myLocalGroupID) {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra("localUser", localUser);
        intent.putExtra("groupID", myLocalGroupID);
        startActivity(intent);
    }

    private void prepareUser() {
        String potentialID = getIDIfSaved();
        if (potentialID != null) {
            Log.d(TAG, "prepareUser: " + "id detected");
            setUserFromBD(potentialID);
            localUser.setChangeListener(new LocalUser.ChangeListener() {
                @Override
                public void onPositionChanged(LocalUser localUser) {
                    getMyRemoteBD().updateLocationOnServer( (User) localUser, localUser.getDataBaseId());
                }
            });
        } else {
            Log.d(TAG, "prepareUser: " + "creation of id");
            testCreateUser();
        }
    }

    private void saveID (String id) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("remoteID", id);
        editor.commit();
    }

    private String getIDIfSaved() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("remoteID", null);
    }

    private void setUserFromBD(String id) {
        LocalUser userFromBD = new LocalUser(id);
        getMyRemoteBD().getUser(id, userFromBD);
        setLocalUser(userFromBD);
    }
}
