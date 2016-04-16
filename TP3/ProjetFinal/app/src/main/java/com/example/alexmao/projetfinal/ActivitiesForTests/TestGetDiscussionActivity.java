package com.example.alexmao.projetfinal.ActivitiesForTests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alexmao.projetfinal.BDDExterne.ConversationEBDD;
import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.MessageEBDD;
import com.example.alexmao.projetfinal.BDDExterne.OnConversationReceived;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.R;

import java.util.Date;

public class TestGetDiscussionActivity extends AppCompatActivity {
    private Button get_button;
    private TextView conversationTextView;
    private ConversationEBDD conversationEBDD;
    private RemoteBD remoteBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_get_discussion);

        get_button  = (Button) findViewById(R.id.activity_test_get_discussion_get_button);
        get_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGetRequest();
            }
        });
        conversationTextView = (TextView)
                findViewById(R.id.activity_test_get_discussion_view_discussion);

        remoteBD = new FireBaseBD(this);

        init();
    }

    void init() {
        conversationEBDD = new ConversationEBDD("pas de group", null, "test conversation");
        MessageEBDD messageEBDD = new MessageEBDD("premier message",
                new Date().getTime(), "pas de destinataire");
        conversationEBDD.addMsg(messageEBDD);
        messageEBDD = new MessageEBDD("deuxieme message",
                new Date().getTime(), "pas de destinataire");
        conversationEBDD.addMsg(messageEBDD);
        messageEBDD = new MessageEBDD("troisieme message",
                new Date().getTime(), "pas de destinataire");
        conversationEBDD.addMsg(messageEBDD);

        String id  = remoteBD.addDiscussion(conversationEBDD);
        conversationEBDD.setDataBaseId(id);
    }

    void onGetRequest() {
        remoteBD.getDiscussion(conversationEBDD.getDataBaseId(), new OnConversationReceived() {
            @Override
            public void onConversationRecieved(ConversationEBDD conversationEBDD) {
                onDiscussionReceived(conversationEBDD);
            }
        });

    }

    void onDiscussionReceived(ConversationEBDD conversationEBDD) {
        String buffer = "";
        for (MessageEBDD messageEBDD : conversationEBDD.getListeMessage()) {
            buffer += messageEBDD.getMessage() + "\n";
        }

        conversationTextView.setText(buffer);
    }

}
