package com.example.alexmao.chat.classeApp;

import java.util.List;

/**
 * Created by Fabien on 02/04/2016.
 */
public class Conversation {
    private String nomConversation;
    private List<Message> listeMessage;
    private Groupe groupe;
    private int idBDD;

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

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public int getIdBDD() {
        return idBDD;
    }

    public void setIdBDD(int idBDD) {
        this.idBDD = idBDD;
    }
}
