package com.example.alexmao.projetfinal.BDDExterne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by filou on 10/03/16.
 */
@JsonIgnoreProperties({"databaseId"})

public class MyLocalGroupEBDD extends MyGroupEBDD {
    private String databaseID;

    public MyLocalGroupEBDD() {
        super();
    }

    public MyLocalGroupEBDD(String databaseID) {
        this.databaseID = databaseID;
    }

    public String getDatabaseID() {
        return databaseID;
    }

    public void setDatabaseID(String databaseID) {
        this.databaseID = databaseID;
    }

    public MyLocalGroupEBDD(String groupName, String organiser, String databaseID) {
        super(groupName, organiser);
        this.databaseID = databaseID;
    }

    public MyLocalGroupEBDD(String groupName, String organiser) {
        super(groupName, organiser);
    }

}
