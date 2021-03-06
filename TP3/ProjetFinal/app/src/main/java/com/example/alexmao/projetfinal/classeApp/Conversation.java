package com.example.alexmao.projetfinal.classeApp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Fabien on 02/04/2016.
 */
public class Conversation implements Parcelable {
    private String nomConversation;
    private List<Message> listeMessage;
    private long idBDD;
    private String idFirebase;
    private String groupID;

    public Conversation() {

    }

    public Conversation(Parcel in) {
        nomConversation = in.readString();
        listeMessage = in.readArrayList(Message.class.getClassLoader());
        idBDD = in.readLong();
        idFirebase = in.readString();
    }

    public String getNomConversation() {
        return nomConversation;
    }

    public void setNomConversation(String nomConversation) {
        this.nomConversation = nomConversation;
    }

    public List<Message> getListeMessage() {
        return listeMessage;
    }

    public void setListeMessage(List<Message> listeMessage) {
        this.listeMessage = listeMessage;
    }

    public long getIdBDD() {
        return idBDD;
    }

    public void setIdBDD(long idBDD) {
        this.idBDD = idBDD;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    /*
         * Parcel methods
         */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nomConversation);
        dest.writeList(listeMessage);
        dest.writeLong(idBDD);
        dest.writeString(idFirebase);
    }

    // Creator
    public static final Parcelable.Creator<Conversation> CREATOR = new Parcelable.Creator<Conversation>() {
        public Conversation createFromParcel(Parcel in) {
            return new Conversation(in);
        }

        public Conversation[] newArray(int size) {
            return new Conversation[size];
        }
    };
}
