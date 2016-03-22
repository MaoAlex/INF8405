package com.example.alexmao.tp2final;

import java.util.ArrayList;

/**
 * Created by alexMAO on 13/03/2016.
 */
public class Groupe {
    private ArrayList<User> users_;
    private String nomGroupe_;
    private ArrayList<Boolean> organisateur_;

    public Groupe(){
        users_ = new ArrayList<User>();
        organisateur_ = new ArrayList<Boolean>();
        nomGroupe_="";
    };
    public Groupe(String nomGroupe_){
        users_ = new ArrayList<User>();
        this.nomGroupe_ = nomGroupe_;
        organisateur_ = new ArrayList<Boolean>();
    }

    public String getNomGroupe() {
        return nomGroupe_;
 }

    public void setNomGroupe(String nomGroupe_) {
        this.nomGroupe_ = nomGroupe_;
    }

    public ArrayList<Boolean> getOrganisateur() {
        return organisateur_;
    }

    public void setOrganisateur(ArrayList<Boolean> organisateur_) {
        this.organisateur_ = organisateur_;
    }

    public ArrayList<User> getUsers() {
        return users_;
    }

    public void setUsers(ArrayList<User> users_) {
        this.users_ = users_;
    }
}
