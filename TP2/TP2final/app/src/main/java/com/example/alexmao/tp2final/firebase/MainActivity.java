package com.example.alexmao.tp2final.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.alexmao.tp2final.R;

public class MainActivity extends ConnectedMapActivity {
    private static final String TAG = "MainActivity";
    private Button gotoMapButton;
    private Button gotoGroupTest;
    private Button gotoMDPTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_firebase);
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
        gotoMDPTest = (Button) findViewById(R.id.goto_mdp_test);
        gotoMDPTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMDPTest();
            }
        });

        prepareUser();
    }

    private void testCreateUser() {
        localUser = new LocalUser("fifi", "test", "exemple@polymtl.ca");
        RemoteBD remoteBD = getMyRemoteBD();
        String userBDID = remoteBD.addUser((UserFirebase) localUser);
        remoteBD.addMdpToUser(localUser.getMailAdr().trim(), "fifi");
        localUser.setDataBaseId(userBDID);
        localUser.setChangeListener(new LocalUser.ChangeListener() {
            @Override
            public void onPositionChanged(LocalUser localUser) {
                getMyRemoteBD().updateLocationOnServer((UserFirebase) localUser, localUser.getDataBaseId());
            }
        });
        setLocalUser(localUser);
    }

    private String prepareGroupTest() {
        RemoteBD remoteBD = getMyRemoteBD();
        localUser = new LocalUser("fifi", "test1", "exemple1@polymtl.ca");
        String userBDID = remoteBD.addUser((UserFirebase) localUser);
        localUser.setDataBaseId(userBDID);
        MyLocalGroup myLocalGroup = new MyLocalGroup("test group", localUser.getDataBaseId());


        localUser = new LocalUser("fifi", "test2", "exemple2@polymtl.ca");
        userBDID = remoteBD.addUser((UserFirebase) localUser);
        localUser.setDataBaseId(userBDID);
        myLocalGroup.addMember(userBDID);


        localUser = new LocalUser("fifi", "test3", "exemple3@polymtl.ca");
        userBDID = remoteBD.addUser((UserFirebase) localUser);
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
        Intent intent = new Intent(MainActivity.this, GroupTestActivity.class);
        intent.putExtra("localUser", localUser);
        intent.putExtra("groupID", myLocalGroupID);
        startActivity(intent);
    }

    private void gotoMDPTest() {
        Intent intent = new Intent(MainActivity.this, MDPTestActivity.class);
        startActivity(intent);
    }

    private void prepareUser() {
            Log.d(TAG, "prepareUser: " + "creation of id");
            testCreateUser();
    }
}
