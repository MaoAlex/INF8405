package com.example.alexmao.projetfinal.model;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alexmao.projetfinal.BDDExterne.ConversationEBDD;
import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.LocalUserProfilEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MessageEBDD;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.R;

import java.util.Date;

public class ChatTestFBActivity extends Activity {
    private LinearLayout linearLayout;
    //widget where the user write its message
    private EditText writeMsgWidget;
    private Button launchMsgBut;
    private RemoteBD remoteBD;
    private LocalUserProfilEBDD currentUserFirebase;
    private LocalUserProfilEBDD testLocalUserProfil;
    private ConversationEBDD discussion;
    private String discussionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_test_fb);

        linearLayout = (LinearLayout) findViewById(R.id.all_msg_layout);
        writeMsgWidget = (EditText) findViewById(R.id.test_chat_fb);

        remoteBD = new FireBaseBD(this);
        currentUserFirebase = new LocalUserProfilEBDD("fifi", "filou", "fifi@filou.com");
        String id = remoteBD.addUserProfil(currentUserFirebase);
        currentUserFirebase.setDataBaseId(id);

        testLocalUserProfil = new LocalUserProfilEBDD("test", "test", "test@test.com");
        String idTest = remoteBD.addUserProfil(testLocalUserProfil);
        testLocalUserProfil.setDataBaseId(idTest);

        discussion = new ConversationEBDD();
        discussionID = remoteBD.addDiscussion(discussion);

        launchMsgBut = (Button) findViewById(R.id.send_message_fb);
        launchMsgBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (writeMsgWidget.getText().toString() != null)
                    onSendMsg(writeMsgWidget.getText().toString());
            }
        });

        //when a new message arrived, we call onNewMsg
//        remoteBD.listenToConversation(discussionID, currentUserFirebase.getDataBaseId(), new OnMessageReceiveCallback() {
//            @Override
//            public void onNewMessage(MessageEBDD message) {
//                onNewMsg(message);
//            }
//        });
    }

    //called whe nthe user when to send a message
    void onSendMsg(String content) {
        if (content == null)
            return;
        //Create a class
        MessageEBDD conversation   = new MessageEBDD();
        conversation.setDate(new Date().getTime());
        //content is what the user has written on the screen (in short the message body)
        conversation.setMessage(content);
        //id firebase
        String msgID = remoteBD.addMsgToDiscussion(discussionID, conversation);
//        remoteBD.notifyUserForMsg(testLocalUserProfil.getDataBaseId(), conversation, discussionID);
    }

    //Called when the current user recieved a new message
    void onNewMsg(MessageEBDD conversation) {
        if (conversation == null)
            return;
        TextView textView = new TextView(this);
        //set the content
        textView.setText(conversation.getMessage());
        linearLayout.addView(textView);
    }
}
