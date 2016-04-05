package com.example.alexmao.chat.BDDExterne;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by filou on 06/03/16.
 */
public class MyGroup {
    private String groupName;
    private List<String> membersID;
    private List<Boolean> isOrganiser;

    public List<Boolean> getIsOrganiser() {
        return isOrganiser;
    }

    public void setIsOrganiser(List<Boolean> isOrganiser) {
        this.isOrganiser = isOrganiser;
    }

    public MyGroup() {
        membersID = new LinkedList<>();
        isOrganiser = new LinkedList<>();

    }

    public MyGroup(String groupName, String organiser) {
        this.groupName = groupName;
        membersID = new LinkedList<>();
        isOrganiser = new LinkedList<>();
        membersID.add(organiser);
    }

    public void update(MyGroup myGroup) {
        groupName = myGroup.getGroupName();
        membersID = myGroup.getMembersID();
    }

    public void addUser(String id) {
        membersID.add(id);
    }

    public void addIsOrganiser(boolean b) {
        isOrganiser.add(b);
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
}
