package com.example.alexmao.tp2final;

import java.util.ArrayList;

/**
 * Created by alexMAO on 17/03/2016.
 */
public class Vote {
    private ArrayList<String> listeNomLieu;
    private ArrayList<Integer> votesParLieu;
    private ArrayList<Disponibilite> listeDisponibilite;
    private ArrayList<Integer> votesParDisponibilite;
    private String groupName;

    public Vote(){
        listeNomLieu = new ArrayList<String>();
        votesParLieu = new ArrayList<Integer>();
    }

    public ArrayList<String> getPropositions(){
        return listeNomLieu;
    }

    public void setPropositions(ArrayList<String> nPropositions){
        listeNomLieu = nPropositions;
    }

    public ArrayList<Integer> getVotes(){
        return votesParLieu;
    }

    public void setVotes(ArrayList<Integer> nVotes){
        votesParLieu = nVotes;
    }

    public void votePourPropositions(String lieu){
        int index = listeNomLieu.indexOf(lieu);
        votesParLieu.set(index, votesParLieu.get(index) + 1);
    }

    public void annuleVote(Lieu lieu){
        int index = listeNomLieu.indexOf(lieu);
        votesParLieu.set(index, votesParLieu.get(index) - 1);
    }

    public ArrayList<Disponibilite> getListeDisponibilite() {
        return listeDisponibilite;
    }

    public void setListeDisponibilite(ArrayList<Disponibilite> listeDisponibilite) {
        this.listeDisponibilite = listeDisponibilite;
    }

    public ArrayList<Integer> getVotesParDisponibilite() {
        return votesParDisponibilite;
    }

    public void setVotesParDisponibilite(ArrayList<Integer> votesParDisponibilite) {
        this.votesParDisponibilite = votesParDisponibilite;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
