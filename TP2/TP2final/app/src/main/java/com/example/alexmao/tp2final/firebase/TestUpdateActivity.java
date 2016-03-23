package com.example.alexmao.tp2final.firebase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.alexmao.tp2final.R;

public class TestUpdateActivity extends ConnectedMapActivity {
    private static String TAG = "TestUpdateActivity";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_update);
        setLocalUser((LocalUser) getIntent().getParcelableExtra("localUser"));

        button = (Button) findViewById(R.id.launch_test_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testUpdate(getLocalUser().getDataBaseId(), getIntent().getStringExtra("groupID"));
            }
        });
    }

    private void testUpdate(String userID, String groupID) {
        LocalUser localUser = new LocalUser(userID);
        localUser.setChangeListener(new LocalUser.ChangeListener() {
            @Override
            public void onPositionChanged(LocalUser localUser) {
                Log.d(TAG, "onPositionChanged: " + localUser.getLastName());
            }
        });

        MyLocalGroup myLocalGroup = new MyLocalGroup(groupID);
        myLocalGroup.setChangeListener(new MyLocalGroup.ChangeListener() {
            @Override
            public void onChange(MyLocalGroup myLocalGroup) {
                Log.d(TAG, "onChange: " + myLocalGroup.getGroupName());
            }
        });

        LocalUserPreferences localUserPreferences = new LocalUserPreferences();
        localUserPreferences.setOnRetrieve(new LocalUserPreferences.OnRetrieve() {
            @Override
            public void onRetrieve(LocalUserPreferences localUserPreferences) {
                String buf = new String();
                for (String pref : localUserPreferences.getPreferences()) {
                    buf = buf + " " + pref;
                }
                Log.d(TAG, "onRetrieve: " + buf);
            }
        });

        getMyRemoteBD().update(localUser, myLocalGroup, localUserPreferences, new OnUpdateComplete() {
            @Override
            public void onUpdateComplete(LocalUser localUser, MyLocalGroup myLocalGroup, LocalUserPreferences localUserPreferences) {
                Log.d(TAG, "onUpdateComplete: " + localUser.getLastName() + " " +myLocalGroup.getGroupName() + " ");
            }
        });
    }
}
