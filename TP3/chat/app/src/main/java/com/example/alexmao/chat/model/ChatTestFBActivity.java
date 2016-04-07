package com.example.alexmao.chat.model;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alexmao.chat.R;
import com.example.alexmao.chat.classeApp.Conversation;
import com.example.alexmao.chat.classeApp.Message;
import com.example.alexmao.chat.BDDExterne.FireBaseBD;
import com.example.alexmao.chat.BDDExterne.LocalUserProfil;
import com.example.alexmao.chat.BDDExterne.OnMessageReceiveCallback;
import com.example.alexmao.chat.BDDExterne.RemoteBD;

import java.util.Date;

public class ChatTestFBActivity extends Activity {
    private LinearLayout linearLayout;
    //widget where the user write its message
    private EditText writeMsgWidget;
    private Button launchMsgBut;
    private RemoteBD remoteBD;
    private LocalUserProfil currentUserFirebase;
    private LocalUserProfil testLocalUserProfil;
    private Conversation discussion;
    private String discussionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_test_fb);

        linearLayout = (LinearLayout) findViewById(R.id.all_msg_layout);
        writeMsgWidget = (EditText) findViewById(R.id.test_chat_fb);

        remoteBD = new FireBaseBD(this);
        currentUserFirebase = new LocalUserProfil("fifi", "filou", "fifi@filou.com");
        String id = remoteBD.addUserProfil(currentUserFirebase);
        currentUserFirebase.setDataBaseId(id);

        testLocalUserProfil = new LocalUserProfil("test", "test", "test@test.com");
        String idTest = remoteBD.addUserProfil(testLocalUserProfil);
        testLocalUserProfil.setDataBaseId(idTest);

        discussion = new Conversation();
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
        remoteBD.listenToConversation(discussionID, currentUserFirebase.getDataBaseId(), new OnMessageReceiveCallback() {
            @Override
            public void onNewMessage(Message message) {
                onNewMsg(message);
            }
        });
    }

    //called whe nthe user when to send a message
    void onSendMsg(String content) {
        if (content == null)
            return;
        //Create a class
        Message conversation   = new Message();
        conversation.setDate(new Date());
        //content is what the user has written on the screen (in short the message body)
        conversation.setMessage(content);
        //id firebase
        String msgID = remoteBD.addMsgToDiscussion(discussionID, conversation);
        remoteBD.notifyUserForMsg(testLocalUserProfil.getDataBaseId(), conversation, discussionID);
    }

    //Called when the current user recieved a new message
    void onNewMsg(Message conversation) {
        if (conversation == null)
            return;
        TextView textView = new TextView(this);
        //set the content
        textView.setText(conversation.getMessage());
        linearLayout.addView(textView);
    }
}
