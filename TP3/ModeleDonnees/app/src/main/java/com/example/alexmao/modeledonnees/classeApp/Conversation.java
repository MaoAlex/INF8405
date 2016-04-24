package com.example.alexmao.modeledonnees.classeApp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Fabien on 02/04/2016.
 */
public class Conversation implements Parcelable {
    private String nomConversation;
    private List<Message> listeMessage;
    private int idBDD;

    public Conversation(Parcel in) {
                nomConversation = in.readString();
                listeMessage = in.readArrayList(Message.class.getClassLoader());
                idBDD = in.readInt();
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
public int getIdBDD() {
        return idBDD;
    }

    public void setIdBDD(int idBDD) {
        this.idBDD = idBDD;
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
        dest.writeInt(idBDD);
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
