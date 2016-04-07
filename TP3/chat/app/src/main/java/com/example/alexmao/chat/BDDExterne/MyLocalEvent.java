package com.example.alexmao.chat.BDDExterne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by filou on 07/04/16.
 */
@JsonIgnoreProperties({"dataBaseId"})
public class MyLocalEvent extends MyEvent {
    private String id;

    public MyLocalEvent(int nbreMaxParticipants, String sport, String nomEvenement,
                        String organisateurID, String visibilite) {
        super(nbreMaxParticipants, sport, nomEvenement, organisateurID, visibilite);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
