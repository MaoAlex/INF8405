package com.example.alexmao.projetfinal.ActivitiesForTests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.LocalUserProfilEBDD;
import com.example.alexmao.projetfinal.BDDExterne.NotificationBDD;
import com.example.alexmao.projetfinal.BDDExterne.NotificationTypes;
import com.example.alexmao.projetfinal.BDDExterne.OnNotificationReceived;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.R;

import java.util.HashMap;
import java.util.Map;

public class TestNotificationActivity extends AppCompatActivity {
    private Button sendInvitationButton;
    private TextView textView;
    private LocalUserProfilEBDD currentUser;
    private RemoteBD remoteBD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_notification);
        sendInvitationButton = (Button) findViewById(R.id.activity_test_notification_send_button);
        sendInvitationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInvitation();
            }
        });
        textView = (TextView) findViewById(R.id.activity_test_notification_view);

        remoteBD = new FireBaseBD(this);

        init();
    }

    void init() {
        currentUser = new LocalUserProfilEBDD("test", "notification", "test@notification.ca");
        String currentID = remoteBD.addUserProfil(currentUser);
        currentUser.setDataBaseId(currentID);

        Map<String, OnNotificationReceived> receivedMap = new HashMap<>();
        receivedMap.put(NotificationTypes.conctactInvitation, new OnNotificationReceived() {
            @Override
            public void onNotificationReceived(NotificationBDD notificationBDD) {
                onContactInvitation(notificationBDD);
            }
        });
        receivedMap.put(NotificationTypes.conctactInvitationAnswer, new OnNotificationReceived() {
            @Override
            public void onNotificationReceived(NotificationBDD notificationBDD) {
                onContactInvitationAnswer(notificationBDD);
            }
        });

        remoteBD.listenToNotification(currentID, receivedMap);
    }

    void sendInvitation() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("textExtra", "this is a contact invitation");
        NotificationBDD notificationBDD = new NotificationBDD(NotificationTypes.conctactInvitation,
                paramMap, currentUser.getDataBaseId(), currentUser.getDataBaseId());

        remoteBD.addNotificationToUser(currentUser.getDataBaseId(), notificationBDD);
    }

    void onContactInvitation(NotificationBDD notificationBDD) {
        String buffer = "received :";
        buffer += notificationBDD.getType() + "\n";
        buffer += "with extra: " + notificationBDD.getParams().get("textExtra") + "\n";

        textView.setText(buffer);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("textExtra", "this is a contact invitation answer");
        NotificationBDD answer = new NotificationBDD(NotificationTypes.conctactInvitationAnswer,
                paramMap, currentUser.getDataBaseId(), currentUser.getDataBaseId());

        remoteBD.addNotificationToUser(currentUser.getDataBaseId(), answer);
    }

    void onContactInvitationAnswer(NotificationBDD notificationBDD) {
        String buffer = "received :";
        buffer += notificationBDD.getType() + "\n";
        buffer += "with extra: " + notificationBDD.getParams().get("textExtra") + "\n";

        textView.setText(buffer);
    }
}
