package com.example.alexmao.chat.BDDExterne;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by filou on 06/03/16.
 */
public class MyGroupEBDD {
    private String groupName;
    private List<String> membersID;
    private String conversationID;
    private String eventID;

    public MyGroupEBDD() {
        membersID = new LinkedList<>();
    }

    public MyGroupEBDD(String groupName, String organiser) {
        this.groupName = groupName;
        membersID = new LinkedList<>();
        membersID.add(organiser);
    }

    public void update(MyGroupEBDD myGroupEBDD) {
        groupName = myGroupEBDD.getGroupName();
        membersID = myGroupEBDD.getMembersID();
        conversationID = myGroupEBDD.getConversationID();
        eventID = myGroupEBDD.getEventID();
    }

    public void addUser(String id) {
        membersID.add(id);
    }

    public String getGroupName() {
        return groupName;
    }

    public List<String> getMembersID() {
        return membersID;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setMembersID(List<String> membersID) {
        this.membersID = membersID;
    }

    public void addMember(String id) {
        membersID.add(id);
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
}
