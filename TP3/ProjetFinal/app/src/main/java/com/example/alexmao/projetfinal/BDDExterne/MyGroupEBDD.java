package com.example.alexmao.projetfinal.BDDExterne;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by filou on 06/03/16.
 */
public class MyGroupEBDD implements Parcelable {
    private String groupName;
    private List<String> membersID;
    private String conversationID;
    private String eventID;

    public static final Creator<MyGroupEBDD> CREATOR = new Creator<MyGroupEBDD>() {
        @Override
        public MyGroupEBDD createFromParcel(Parcel source)
        {
            return new MyGroupEBDD(source);
        }

        @Override
        public MyGroupEBDD[] newArray(int size)
        {
            return new MyGroupEBDD[size];
        }
    };
    public MyGroupEBDD(Parcel in) {
        groupName = in.readString();
        conversationID = in.readString();
        eventID = in.readString();
        in.readStringList(membersID);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupName);
        dest.writeString(conversationID);
        dest.writeString(eventID);
        dest.writeList(membersID);
    }

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
