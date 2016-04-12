package com.example.alexmao.projetfinal.BDDExterne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by filou on 12/04/16.
 */
@JsonIgnoreProperties({"idFirebase"})
public class InvitationConnexionEBDD extends NotificationDataEBDD {
    public InvitationConnexionEBDD() {
    }

    public InvitationConnexionEBDD(String expediteur, String invite, long date) {
        super(expediteur, invite, date);
    }
}
