package com.example.alexmao.tp2final.firebase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alexmao.tp2final.R;

public class MDPTestActivity extends ConnectedMapActivity {
    private static String TAG = "MDPTestActivity";
    private EditText mailEdit;
    private EditText mdpEdit;
    private Button launchTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdptest);
        mailEdit = (EditText) findViewById(R.id.usr_mail);
        mdpEdit = (EditText) findViewById(R.id.usr_mdp);
        launchTestButton = (Button) findViewById(R.id.queryBD);
        launchTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTest();
            }
        });
    }

    void launchTest() {
        String mail = mailEdit.getText().toString().trim();
        Log.d(TAG, "launchTest: mail " + mail);
        String mdp = mdpEdit.getText().toString().trim();
        Log.d(TAG, "launchTest: mdp " + mdp);
        final MdpWrapper mdpWrapper = new MdpWrapper(mdp);
        mdpWrapper.setOnRetrieveListener(new MdpWrapper.onRetrieveListener() {
            @Override
            public void onRetrieve(String mdp) {
                if (mdp != null && mdp.equals(mdpWrapper.getMdp())) {
                    succes();
                } else {
                    failure();
                }
            }
        });
        getMyRemoteBD().getMdp(mail, mdpWrapper);
    }

    void succes() {
        Log.d(TAG, "succes: ");
    }

    void failure() {
        Log.d(TAG, "failure: ");
    }
}
