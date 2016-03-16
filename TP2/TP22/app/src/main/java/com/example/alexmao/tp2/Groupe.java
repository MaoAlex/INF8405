package com.example.alexmao.tp2;

import java.util.ArrayList;

/**
 * Created by alexMAO on 13/03/2016.
 */
public class Groupe {
    private ArrayList<User> users_;
    private String nomGroupe_;
    private ArrayList<User> organisateur_;

    public Groupe(String nomGroupe_){
        this.nomGroupe_ = nomGroupe_;
    }

    public String getNomGroupe_() {
        return nomGroupe_;
    }

    public void setNomGroupe_(String nomGroupe_) {
        this.nomGroupe_ = nomGroupe_;
    }

    public ArrayList<User> getOrganisateur_() {
        return organisateur_;
    }

    public void setOrganisateur_(ArrayList<User> organisateur_) {
        this.organisateur_ = organisateur_;
    }

    public ArrayList<User> getUsers_() {
        return users_;
    }

    public void setUsers_(ArrayList<User> users_) {
        this.users_ = users_;
    }
}
