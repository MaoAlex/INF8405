package com.example.alexmao.projetfinal.ActivitiesForTests;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alexmao.projetfinal.BDDExterne.ConversationEBDD;
import com.example.alexmao.projetfinal.BDDExterne.FetchFullDataFromEBDD;
import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.FullGroupWrapper;
import com.example.alexmao.projetfinal.BDDExterne.FullUserWrapper;
import com.example.alexmao.projetfinal.BDDExterne.MessageEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MyEventEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MyGroupEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MyLocalEventEBDD;
import com.example.alexmao.projetfinal.BDDExterne.MyLocalGroupEBDD;
import com.example.alexmao.projetfinal.BDDExterne.OnFullGroup;
import com.example.alexmao.projetfinal.BDDExterne.OnGroupsReady;
import com.example.alexmao.projetfinal.BDDExterne.Picture;
import com.example.alexmao.projetfinal.BDDExterne.Position;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDExterne.UserParamsEBDD;
import com.example.alexmao.projetfinal.BDDExterne.UtilisateurProfilEBDD;
import com.example.alexmao.projetfinal.R;

import java.util.LinkedList;
import java.util.List;

public class TestFetchAllGroupsActivity extends Activity {
    private String userID;
    private Button launchTestButton;
    private TextView testResultsView;
    private RemoteBD remoteBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fetch_all_groups);

        remoteBD = new FireBaseBD(this);

        String user1ID = initUser1(remoteBD);
        String user2ID = initUser2(remoteBD);
        String user3ID = initUser3(remoteBD);
        String event1ID = initEvent1(remoteBD);
        String event2ID = initEvent2(remoteBD);
        String conversation1ID = initConversation1(remoteBD);
        String conversation2ID = initConversation2(remoteBD);

        List<String> usersIDS = new LinkedList<>();
        usersIDS.add(user1ID);
        usersIDS.add(user2ID);
        String group1ID = initGroup(remoteBD, usersIDS, conversation1ID, event1ID);

        List<String> usersIDS2 = new LinkedList<>();
        usersIDS2.add(user1ID);
        usersIDS2.add(user3ID);
        String group2ID = initGroup(remoteBD, usersIDS2, conversation2ID, event2ID);

        testResultsView = (TextView) findViewById(R.id.test_group_fetch_view);

        launchTestButton = (Button) findViewById(R.id.launch_test_group_fetch);
        launchTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTest();
            }

        });

        userID = user1ID;
        remoteBD.addUserToGroup(group1ID, userID);
        remoteBD.addUserToGroup(group2ID, userID);
    }

    private String initUser1(RemoteBD remoteBD) {
        String id;
        UtilisateurProfilEBDD currentUser = new UtilisateurProfilEBDD("test1", "fulluser", "test.fulluser@polytestgroup.ca");
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
        Position mPosition = new Position(0xDEAD, 0xBEAF);
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
        UtilisateurProfilEBDD currentUser = new UtilisateurProfilEBDD("test2", "fullother", "test2.fullother@polytestgroup.ca");
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
        Position mPosition = new Position(0xDEAF, 0xFEFA);
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

    private String initUser3(RemoteBD remoteBD) {
        String id;
        UtilisateurProfilEBDD currentUser = new UtilisateurProfilEBDD("test3", "fulllast", "test2.fulllast@polytestgroup.ca");
        List<String> sports = new LinkedList<>();
        sports.add("natation synchronisée");
        sports.add("bowling");
        currentUser.setSports(sports);
        List<String> interets = new LinkedList<>();
        interets.add("id1Swag");
        interets.add("id2Swag");
        currentUser.setListeInteretsID(interets);
        List<String> participations = new LinkedList<>();
        participations.add("participationID");
        currentUser.setListeParticipationsID(participations);
        Position mPosition = new Position(0xDEAF, 0xBEAF);
        UserParamsEBDD mParams = new UserParamsEBDD(100, true, false);
        Picture mPicture = new Picture();
        List<String> strings = new LinkedList<>();
        strings.add("success last");
        mPicture.setStringChunks(strings);

        id = remoteBD.addUserProfil(currentUser);
        remoteBD.addPositionToUser(id, mPosition);
        remoteBD.addUserParam(mParams, id);
        remoteBD.addPicToUser(id, mPicture);

        return id;
    }

    private String initGroup(RemoteBD remoteBD, List<String> users, String conversationID, String eventID) {
        MyGroupEBDD groupEBDD = new MyGroupEBDD();
        for (String userID : users) {
            groupEBDD.addMember(userID);
        }
        groupEBDD.setConversationID(conversationID);
        groupEBDD.setEventID(eventID);

        String groupID = remoteBD.addGroup(groupEBDD);
        return groupID;
    }

    private String initConversation1(RemoteBD remoteBD) {
        ConversationEBDD conversation = new ConversationEBDD();
        conversation.setNomConversation("conversation Test all groups fetch 1");
        List<MessageEBDD> messages = new LinkedList<>();
        MessageEBDD messageEBDD = new MessageEBDD("test recup message 1", 0, "invalid ID 1");
        messages.add(messageEBDD);
        String conversatuionID = remoteBD.addDiscussion(conversation);

        return conversatuionID;
    }

    private String initConversation2(RemoteBD remoteBD) {
        ConversationEBDD conversation = new ConversationEBDD();
        conversation.setNomConversation("conversation Test all groups fetch 2");
        List<MessageEBDD> messages = new LinkedList<>();
        MessageEBDD messageEBDD = new MessageEBDD("test recup message 2", 0, "invalid ID 2");
        messages.add(messageEBDD);
        String conversatuionID = remoteBD.addDiscussion(conversation);

        return conversatuionID;
    }

    private String initEvent1(RemoteBD remoteBD) {
        MyEventEBDD eventEBDD = new MyEventEBDD(1,
                "corrida",
                "test fetch all groups 1",
                "personne", "visible");

        String eventID = remoteBD.addEvent(eventEBDD);
        return eventID;
    }

    private String initEvent2(RemoteBD remoteBD) {
        MyEventEBDD eventEBDD = new MyEventEBDD(1,
                "ski sur gazon",
                "test fetch all groups 2",
                "personne bis", "invisible");

        String eventID = remoteBD.addEvent(eventEBDD);
        return eventID;
    }

    private void launchTest() {
        FetchFullDataFromEBDD.fetchallGroups(userID, remoteBD, new OnGroupsReady() {
            @Override
            public void onGroupsReady(List<FullGroupWrapper> groupWrappers) {
                onReception(groupWrappers);
            }
        });
    }

    private void onReception(List<FullGroupWrapper> groupWrappers) {
        String mToString = "";
        for (FullGroupWrapper groupWrapper: groupWrappers) {
            mToString += groupWrapper + "\n";
        }
        testResultsView.setText(mToString);
    }

}
