package com.example.alexmao.projetfinal.ActivitiesForTests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alexmao.projetfinal.BDDExterne.ConversationEBDD;
import com.example.alexmao.projetfinal.BDDExterne.FetchFullDataFromEBDD;
import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.FullUserWrapper;
import com.example.alexmao.projetfinal.BDDExterne.MessageEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MyEventEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MyGroupEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MyLocalEventEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MyLocalGroupEBDD;
import com.example.alexmao.projetfinal.BDDExterne.OnFullGroup;
import com.example.alexmao.projetfinal.BDDExterne.Picture;
import com.example.alexmao.projetfinal.BDDExterne.Position;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDExterne.UserParamsEBDD;
import com.example.alexmao.projetfinal.BDDExterne.UtilisateurProfilEBDD;
import com.example.alexmao.projetfinal.R;
import com.google.android.gms.internal.ei;

import java.util.LinkedList;
import java.util.List;

public class TestFetchGroupActivity extends AppCompatActivity {
    private String groupID;
    private Button launchTestButton;
    private TextView testResultsView;
    private RemoteBD remoteBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fetch_group);

        remoteBD = new FireBaseBD(this);

        String user1ID = initUser1(remoteBD);
        String user2ID = initUser2(remoteBD);
        String eventID = initEvent(remoteBD);
        String conversationID = initConversation(remoteBD);

        List<String> usersIDS = new LinkedList<>();
        usersIDS.add(user1ID);
        usersIDS.add(user2ID);
        groupID = initGroup(remoteBD, usersIDS, conversationID, eventID);

        testResultsView = (TextView) findViewById(R.id.test_group_fetch_view);

        launchTestButton = (Button) findViewById(R.id.launch_test_group_fetch);
        launchTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTest();
            }

        });
    }

    private String initUser1(RemoteBD remoteBD) {
        String id;
        UtilisateurProfilEBDD currentUser = new UtilisateurProfilEBDD("test1", "fulluser","test.fulluser@polytestgroup.ca");
        List<String> sports = new LinkedList<>();
        sports.add("curling");
        sports.add("boxe en tutu");
        currentUser.setSports(sports);
        List<String> interets = new LinkedList<>();
        interets.add("id1");
        interets.add("id2");
        currentUser.setListeInteretsID(interets);
        List<String> participations = new LinkedList<>();
        participations.add("participationID");
        currentUser.setListeParticipationsID(participations);
        Position mPosition  = new Position(0xDEAD,0xBEAF);
        UserParamsEBDD mParams = new UserParamsEBDD(10, true, true);
        Picture mPicture = new Picture();
        List<String> strings = new LinkedList<>();
        strings.add("success");
        mPicture.setStringChunks(strings);

        id = remoteBD.addUserProfil(currentUser);
        remoteBD.addPositionToUser(id, mPosition);
        remoteBD.addUserParam(mParams, id);
        remoteBD.addPicToUser(id, mPicture);

        return id;
    }

    private String initUser2(RemoteBD remoteBD) {
        String id;
        UtilisateurProfilEBDD currentUser = new UtilisateurProfilEBDD("test2", "fullother","test2.fullother@polytestgroup.ca");
        List<String> sports = new LinkedList<>();
        sports.add("lap dance");
        sports.add("barathon");
        currentUser.setSports(sports);
        List<String> interets = new LinkedList<>();
        interets.add("id1");
        interets.add("id2");
        currentUser.setListeInteretsID(interets);
        List<String> participations = new LinkedList<>();
        participations.add("participationID");
        currentUser.setListeParticipationsID(participations);
        Position mPosition  = new Position(0xDEAF,0xFEFA);
        UserParamsEBDD mParams = new UserParamsEBDD(10, true, false);
        Picture mPicture = new Picture();
        List<String> strings = new LinkedList<>();
        strings.add("success other");
        mPicture.setStringChunks(strings);

        id = remoteBD.addUserProfil(currentUser);
        remoteBD.addPositionToUser(id, mPosition);
        remoteBD.addUserParam(mParams, id);
        remoteBD.addPicToUser(id, mPicture);

        return id;
    }

    private String  initGroup(RemoteBD remoteBD, List<String> users, String conversationID, String eventID) {
        MyGroupEBDD groupEBDD = new MyGroupEBDD();
        for (String userID: users) {
            groupEBDD.addMember(userID);
        }
        groupEBDD.setConversationID(conversationID);
        groupEBDD.setEventID(eventID);

       String groupID = remoteBD.addGroup(groupEBDD);
        return groupID;
    }

    private String initConversation(RemoteBD remoteBD) {
        ConversationEBDD conversation = new ConversationEBDD();
        conversation.setNomConversation("conversation Test group fetch");
        List<MessageEBDD> messages = new LinkedList<>();
        MessageEBDD messageEBDD = new MessageEBDD("test recup message", 0, "invalid ID");
        messages.add(messageEBDD);
        String conversatuionID = remoteBD.addDiscussion(conversation);

        return conversatuionID;
    }

    private String initEvent(RemoteBD remoteBD) {
        MyEventEBDD eventEBDD = new MyEventEBDD(1,
                "corrida",
                "test fetch group",
                "personne", "visible");

        String eventID = remoteBD.addEvent(eventEBDD);
        return eventID;
    }

    private void launchTest() {
        FetchFullDataFromEBDD.fetchGroup(groupID, remoteBD, new OnFullGroup() {
            @Override
            public void onFullGroup(MyLocalGroupEBDD myLocalGroupEBDD,
                                    List<FullUserWrapper> wrappers,
                                    ConversationEBDD conversationEBDD,
                                    MyLocalEventEBDD myLocalEventEBDD) {
                onReception(myLocalGroupEBDD, wrappers, conversationEBDD, myLocalEventEBDD);
            }
        });
    }

    private void onReception(MyLocalGroupEBDD myLocalGroupEBDD,
                             List<FullUserWrapper> wrappers,
                             ConversationEBDD conversationEBDD,
                             MyLocalEventEBDD myLocalEventEBDD) {
        String mToString = myLocalGroupEBDD.toString() + "\n";
        for (FullUserWrapper fullUserWrapper: wrappers) {
            mToString += fullUserWrapper.toString();
        }
        mToString += "\n";
        mToString += conversationEBDD.toString() + "\n";
        mToString += myLocalEventEBDD.toString() + "\n";

        testResultsView.setText(mToString);
    }

}
