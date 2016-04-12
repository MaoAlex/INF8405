package com.example.alexmao.projetfinal.BDDExterne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by filou on 07/04/16.
 */
@JsonIgnoreProperties({"dataBaseId"})
public class ConversationEBDD {
    private String nomConversation;
    private List<MessageBDD> listeMessage;
    private String groupID;
    private String dataBaseId;

    public ConversationEBDD() {
        listeMessage = new LinkedList<>();
    }

    public ConversationEBDD(String groupID, List<MessageBDD> listeMessage, String nomConversation) {
        this.groupID = groupID;
        this.listeMessage = listeMessage;
        this.nomConversation = nomConversation;
        if (listeMessage == null)
            this.listeMessage = new LinkedList<>();
    }

    public ConversationEBDD(ConversationEBDD conversationEBDD) {
        groupID = conversationEBDD.getGroupID();
        listeMessage = conversationEBDD.getListeMessage();
        nomConversation = conversationEBDD.getNomConversation();
        dataBaseId = conversationEBDD.getDataBaseId();

        if (listeMessage == null)
            listeMessage = new LinkedList<>();
    }

    public String getDataBaseId() {
        return dataBaseId;
    }

    public void setDataBaseId(String dataBaseId) {
        this.dataBaseId = dataBaseId;
    }

    public String getNomConversation() {
        return nomConversation;
    }

    public void setNomConversation(String nomConversation) {
        this.nomConversation = nomConversation;
    }

    public List<MessageBDD> getListeMessage() {
        return listeMessage;
    }

    public void setListeMessage(List<MessageBDD> listeMessage) {
        this.listeMessage = listeMessage;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public void addMsg(MessageBDD messageBDD) {
        listeMessage.add(messageBDD);
    }
}
