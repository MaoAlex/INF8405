package com.example.alexmao.tp2final;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.alexmao.tp2final.firebase.ConnectedMapActivity;
import com.example.alexmao.tp2final.firebase.GroupTestActivity;
import com.example.alexmao.tp2final.firebase.LocalUser;
import com.example.alexmao.tp2final.firebase.MDPTestActivity;
import com.example.alexmao.tp2final.firebase.MapActivity;
import com.example.alexmao.tp2final.firebase.MyGroup;
import com.example.alexmao.tp2final.firebase.MyLocalGroup;
import com.example.alexmao.tp2final.firebase.RemoteBD;
import com.example.alexmao.tp2final.firebase.UserFirebase;

public class MainActivity extends ConnectedMapActivity {
    MaBaseSQLite db;
    final public String DEBUG_TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Action sur le clic du bouton login

        final Button loginButton = (Button) findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Action sur le clic du bouton register
        final Button registerButton = (Button) findViewById(R.id.button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        //Creation d'une instance de ma classe usersBDD
        UsersBDD userBDD = new UsersBDD(this);
        GroupeBDD groupeBDD = new GroupeBDD(this);
        DisponibiliteBDD disponibiliteBDD = new DisponibiliteBDD(this);
        EvenementBDD evenementBDD = new EvenementBDD(this);
        LieuBDD lieuBDD = new LieuBDD(this);
        LieuChoisiBDD lieuChoisiBDD = new LieuChoisiBDD(this);
        LocalisationBDD localisationBDD = new LocalisationBDD(this);
        PreferenceBDD preferenceBDD = new PreferenceBDD(this);
        VoteBDD voteBDD = new VoteBDD(this);


        //On ouvre la base de données pour écrire dedans
        userBDD.open();
        groupeBDD.open();
        disponibiliteBDD.open();
        evenementBDD.open();
        lieuBDD.open();
        lieuChoisiBDD.open();
        localisationBDD.open();
        preferenceBDD.open();
        voteBDD.open();
        //on ferme la base de données
        userBDD.close();
        groupeBDD.close();
        disponibiliteBDD.close();
        evenementBDD.close();
        lieuBDD.close();

        lieuChoisiBDD.close();
        localisationBDD.close();
        preferenceBDD.close();
        voteBDD.close();
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
        Log.d(DEBUG_TAG, "prepareUser: " + "creation of id");
        testCreateUser();
    }

}
