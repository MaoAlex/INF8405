package com.example.alexmao.projetfinal.BDDExterne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by filou on 12/04/16.
 */
@JsonIgnoreProperties({"idFirebase"})
public class InvitationEvenementEBDD extends NotificationDataEBDD {
    private String evenementIdFirebase;

    public InvitationEvenementEBDD() {
        super();
    }

    public InvitationEvenementEBDD(String evenementIdFirebase, String expediteurIdFirebase,
                                   String inviteIdFirebase, long date) {
        super(expediteurIdFirebase, inviteIdFirebase, date);
    }

    public String getEvenementIdFirebase() {
        return evenementIdFirebase;
    }

    public void setEvenementIdFirebase(String evenementIdFirebase) {
        this.evenementIdFirebase = evenementIdFirebase;
    }
}
