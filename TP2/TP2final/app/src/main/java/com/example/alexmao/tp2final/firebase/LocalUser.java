package com.example.alexmao.tp2final.firebase;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by filou on 09/03/16.
 */

@JsonIgnoreProperties({"dataBaseId", "changeListener"})
public class LocalUser extends UserFirebase {
    private String dataBaseId;
    public interface ChangeListener{
        void onPositionChanged(LocalUser localUser);
    }

    private ChangeListener changeListener;

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public LocalUser() {
    }

    public ChangeListener getChangeListener() {
        return changeListener;
    }

    public LocalUser(String dataBaseId) {

        this.dataBaseId = dataBaseId;
    }

    public static final Parcelable.Creator<LocalUser> CREATOR = new Parcelable.Creator<LocalUser>()
    {
        @Override
        public LocalUser createFromParcel(Parcel source)
        {
            return new LocalUser(source);
        }

        @Override
        public LocalUser[] newArray(int size)
        {
            return new LocalUser[size];
        }
    };

    public LocalUser(Parcel in) {
        super(in);
        dataBaseId = in.readString();
    }

    public LocalUser(String firstName, String lastName, String mailAdr, String dataBaseId) {
        super(firstName, lastName, mailAdr);
        this.dataBaseId = dataBaseId;
    }

    public LocalUser(String firstName, String lastName, String mailAdr) {
        super(firstName, lastName, mailAdr);
    }

    public LocalUser(String firstName, String lastName, String mailAdr, ArrayList<String> pref) {
        super(firstName, lastName, mailAdr, pref);
    }
    public LocalUser(UserFirebase user) {
        super(user.getFirstName(), user.getLastName(), user.getMailAdr());
    }

    public String getDataBaseId() {
        return dataBaseId;
    }

    public void setDataBaseId(String dataBaseId) {
        this.dataBaseId = dataBaseId;
    }

    public void update() {
        if (changeListener != null)
            changeListener.onPositionChanged(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(dataBaseId);
    }
}
