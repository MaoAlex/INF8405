package worktest.filou.activitymaptest;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class BenchMarkActivity extends ConnectedMapActivity {
    private static String TAG = "BenchMarkActivity";
    private MyLocalGroup myLocalGroup;
    private int batteryLevel;
    private float batterypercentbef;
    private float batterypercentaft;
    private int actualGroupSize = 0;
    private int groupSize = -1;
    private TextView tvBatteryBefore;
    private TextView tvBatteryAfter;
    private Button blaunchTest;
    private List<LocalUser> localUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bench_mark);
        setLocalUser((LocalUser) getIntent().getParcelableExtra("localUser"));

        localUsers = new LinkedList<>();

        tvBatteryBefore = (TextView) findViewById(R.id.batt_level_bef);
        tvBatteryAfter = (TextView) findViewById(R.id.batt_level_af);

        blaunchTest = (Button) findViewById(R.id.launch_batt_test);
        blaunchTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupBatteryTest();
            }
        });
    }

    void getBatteLevelBF() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        batteryLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        batterypercentbef = batteryLevel / (float)scale;
        batterypercentbef *= 100;
        tvBatteryBefore.setText("" + batterypercentbef);
    }

    void getBatteLevelAF() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        batteryLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        batterypercentaft = batteryLevel / (float)scale;
        batterypercentaft *= 100;
        tvBatteryAfter.setText("" + batterypercentaft);
    }

    void groupBatteryTest() {
        getBatteLevelBF();

        myLocalGroup = new MyLocalGroup();
        myLocalGroup.setDatabaseID(getIntent().getStringExtra("groupID"));
        myLocalGroup.setChangeListener(new MyLocalGroup.ChangeListener() {
            @Override
            public void onChange(MyLocalGroup myLocalGroup) {
                setupGroup(myLocalGroup);
                Log.d(TAG, "onChange: " + "group change");
            }
        });

        RemoteBD remoteBD = getMyRemoteBD();
        remoteBD.getGroup(myLocalGroup.getDatabaseID(), myLocalGroup);
    }

    private void setupGroup(MyLocalGroup myLocalGroup) {
        List<String> ids = myLocalGroup.getMembersID();
        RemoteBD remoteBD = getMyRemoteBD();
        groupSize = 0;
        actualGroupSize = 0;
        for (String id : ids) {
            if (id.equals(getLocalUser().getDataBaseId())) {
                localUser.setChangeListener(new LocalUser.ChangeListener() {
                    @Override
                    public void onPositionChanged(LocalUser localUser) {
                        updateBattLevel();
                    }
                });
                actualGroupSize++;
                localUsers.add(localUser);
            } else {
                LocalUser localUserFromRemote = new LocalUser();
                localUserFromRemote.setDataBaseId(id);

                localUserFromRemote.setChangeListener(new LocalUser.ChangeListener() {
                    @Override
                    public void onPositionChanged(LocalUser localUser) {
                        onUserCreated(localUser);
                    }
                });

                remoteBD.getUser(id, localUserFromRemote);
                remoteBD.listenToChangeOnUser(localUser, localUser.getDataBaseId());
                localUsers.add(localUserFromRemote);
            }
            groupSize++;
        }
    }

    //called when an user is ready
    private void onUserCreated(LocalUser localUser) {
        actualGroupSize++;
        localUser.setChangeListener(new LocalUser.ChangeListener() {
            @Override
            public void onPositionChanged(LocalUser localUser) {
                updateBattLevel();
            }
        });
        updateBattLevel();
    }

    void updateBattLevel() {
        if (actualGroupSize == groupSize) {
            getBatteLevelAF();
        }
    }
}
