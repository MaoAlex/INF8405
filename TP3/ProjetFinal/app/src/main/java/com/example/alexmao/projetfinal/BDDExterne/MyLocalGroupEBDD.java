package com.example.alexmao.projetfinal.BDDExterne;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by filou on 10/03/16.
 */
@JsonIgnoreProperties({"databaseId"})

public class MyLocalGroupEBDD extends MyGroupEBDD {
    private String databaseID;

    public static final Creator<MyLocalGroupEBDD> CREATOR = new Creator<MyLocalGroupEBDD>()
    {
        @Override
        public MyLocalGroupEBDD createFromParcel(Parcel source)
        {
            return new MyLocalGroupEBDD(source);
        }

        @Override
        public MyLocalGroupEBDD[] newArray(int size)
        {
            return new MyLocalGroupEBDD[size];
        }
    };
    public MyLocalGroupEBDD(Parcel in) {
        super(in);
        databaseID = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(databaseID);
    }

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
