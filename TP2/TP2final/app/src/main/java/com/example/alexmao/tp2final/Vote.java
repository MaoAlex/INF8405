package com.example.alexmao.tp2final;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by alexMAO on 17/03/2016.
 */
public class Vote implements Parcelable {
    private ArrayList<String> listeNomLieu;
    private ArrayList<Integer> votesParLieu;
    private ArrayList<Disponibilite> listeDisponibilite;
    private ArrayList<Integer> votesParDisponibilite;
    private String groupName;

    public Vote(){
        listeNomLieu = new ArrayList<String>();
        votesParLieu = new ArrayList<Integer>();
    }

    public Vote(Parcel in) {
        listeNomLieu = in.readArrayList(String.class.getClassLoader());
        votesParLieu = in.readArrayList(Integer.class.getClassLoader());
        listeDisponibilite = in.readArrayList(Disponibilite.class.getClassLoader());
        votesParDisponibilite = in.readArrayList(Integer.class.getClassLoader());
        groupName = in.readString();
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(listeNomLieu);
        dest.writeList(votesParLieu);
        dest.writeList(listeDisponibilite);
        dest.writeList(votesParDisponibilite);
        dest.writeString(groupName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Vote> CREATOR = new Creator<Vote>() {
        @Override
        public Vote createFromParcel(Parcel in) {
            return new Vote(in);
        }

        @Override
        public Vote[] newArray(int size) {
            return new Vote[size];
        }
    };
}
