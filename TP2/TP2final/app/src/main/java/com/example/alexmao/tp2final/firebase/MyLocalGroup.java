package com.example.alexmao.tp2final.firebase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by filou on 10/03/16.
 */
@JsonIgnoreProperties({"dataBaseId", "changeListener"})
public class    MyLocalGroup extends MyGroup {
    public interface ChangeListener {
        void onChange(MyLocalGroup myLocalGroup);
    };

    private ChangeListener changeListener;
    private String databaseID;

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public MyLocalGroup() {
    }

    public MyLocalGroup(String databaseID) {
        this.databaseID = databaseID;
    }

    public ChangeListener getChangeListener() {
        return changeListener;
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

    @Override
    public void update(MyGroup myGroup) {
        super.update(myGroup);

        if (changeListener != null)
            changeListener.onChange(this);
    }
}
