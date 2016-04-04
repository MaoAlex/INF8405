package com.example.alexmao.modeledonnees.classeApp;

import java.util.List;

/**
 * Created by Fabien on 02/04/2016.
 */
public class Groupe {
    private List<Utilisateur> listeMembre;
    private String idFirebase;
    private int idBDD;
    private Conversation conversation;

    public List<Utilisateur> getListeMembre() {
        return listeMembre;
    }

    public void setListeMembre(List<Utilisateur> listeMembre) {
        this.listeMembre = listeMembre;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }

    public int getIdBDD() {
        return idBDD;
    }

    public void setIdBDD(int idBDD) {
        this.idBDD = idBDD;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
