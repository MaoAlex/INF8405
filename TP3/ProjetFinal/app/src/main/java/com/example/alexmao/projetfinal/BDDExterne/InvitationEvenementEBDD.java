package com.example.alexmao.projetfinal.BDDExterne;

/**
 * Created by filou on 12/04/16.
 */
public class InvitationEvenementEBDD {
    private String evenementIdFirebase;
    private String expediteurIdFirebase;
    private String inviteIdFirebase;
    private long date;
    private String idFirebase;

    public InvitationEvenementEBDD() {
    }

    public InvitationEvenementEBDD(String evenementIdFirebase, String expediteurIdFirebase,
                                   String inviteIdFirebase, long date) {
        this.evenementIdFirebase = evenementIdFirebase;
        this.expediteurIdFirebase = expediteurIdFirebase;
        this.inviteIdFirebase = inviteIdFirebase;
        this.date = date;
    }

    public String getEvenementIdFirebase() {
        return evenementIdFirebase;
    }

    public void setEvenementIdFirebase(String evenementIdFirebase) {
        this.evenementIdFirebase = evenementIdFirebase;
    }

    public String getExpediteurIdFirebase() {
        return expediteurIdFirebase;
    }

    public void setExpediteurIdFirebase(String expediteurIdFirebase) {
        this.expediteurIdFirebase = expediteurIdFirebase;
    }

    public String getInviteIdFirebase() {
        return inviteIdFirebase;
    }

    public void setInviteIdFirebase(String inviteIdFirebase) {
        this.inviteIdFirebase = inviteIdFirebase;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }
}
