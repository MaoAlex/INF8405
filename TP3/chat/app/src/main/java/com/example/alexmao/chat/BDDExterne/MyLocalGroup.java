package com.example.alexmao.chat.BDDExterne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by filou on 10/03/16.
 */
@JsonIgnoreProperties({"databaseId", "changeListener"})

public class MyLocalGroup extends MyGroup {
    private String databaseID;

    public MyLocalGroup() {
        super();
    }

    public MyLocalGroup(String databaseID) {
        this.databaseID = databaseID;
    }

    public String getDatabaseID() {
        return databaseID;
    }

    public void setDatabaseID(String databaseID) {
        this.databaseID = databaseID;
    }

    public MyLocalGroup(String groupName, String organiser, String databaseID) {
        super(groupName, organiser);
        this.databaseID = databaseID;
    }

    public MyLocalGroup(String groupName, String organiser) {
        super(groupName, organiser);
    }

}
