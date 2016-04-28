package com.example.alexmao.projetfinal.classeApp;

import android.content.Context;

import com.example.alexmao.projetfinal.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Fabien on 27/04/2016.
 */
public class Sport {
    private String nom;
    private int sportId;

    public static String[] listeSports;
    public static int[] listeIds;
    public static int[] listeDrawingsIds;
    public static HashMap<String, Integer> stringToPosition = new HashMap<>();
    public static HashMap<String, Integer> stringToDrawable = new HashMap<>();
    public static boolean initialized = false;

    public static void initialize() {
        if(!initialized) {
            Sport.listeSports();
            for (int i = 0; i < listeSports.length; i++) {
                Sport.stringToPosition.put(listeSports[i],i);
                Sport.stringToDrawable.put(listeSports[i],listeDrawingsIds[i]);
            }

            initialized = true;
        }
    }
    public static void listeSports() {
        listeSports = new String[16];
        listeIds = new int[16];
        listeDrawingsIds = new int[16];

        listeSports[0] = "art_martial";
        listeIds[0] = R.id.art_martial;
        listeDrawingsIds[0] = R.drawable.art_martial;
        listeSports[1] = "athletisme";
        listeIds[1] = R.id.athletisme;
        listeDrawingsIds[1] = R.drawable.athletisme;
        listeSports[2] = "badminton";
        listeIds[2] = R.id.badminton;
        listeDrawingsIds[2] = R.drawable.badminton;
        listeSports[3] = "baseball";
        listeIds[3] = R.id.baseball;
        listeDrawingsIds[3] = R.drawable.baseball;
        listeSports[4] = "basketball";
        listeIds[4] = R.id.basketball;
        listeDrawingsIds[4] = R.drawable.basketball;
        listeSports[5] = "boxe";
        listeIds[5] = R.id.boxe;
        listeDrawingsIds[5] = R.drawable.boxe;
        listeSports[6] = "fitness";
        listeIds[6] = R.id.fitness;
        listeDrawingsIds[6] = R.drawable.fitness;
        listeSports[7] = "football";
        listeIds[7] = R.id.football;
        listeDrawingsIds[7] = R.drawable.football;
        listeSports[8] = "golf";
        listeIds[8] = R.id.golf;
        listeDrawingsIds[8] = R.drawable.golf;
        listeSports[9] = "hockey";
        listeIds[9] = R.id.hockey;
        listeDrawingsIds[9] = R.drawable.hockey;
        listeSports[10] = "natation";
        listeIds[10] = R.id.natation;
        listeDrawingsIds[10] = R.drawable.natation;
        listeSports[11] = "pingpong";
        listeIds[11] = R.id.pingpong;
        listeDrawingsIds[11] = R.drawable.pingpong;
        listeSports[12] = "rugby";
        listeIds[12] = R.id.rugby;
        listeDrawingsIds[12] = R.drawable.rugby;
        listeSports[13] = "tennis";
        listeIds[13] = R.id.tennis;
        listeDrawingsIds[13] = R.drawable.tennis;
        listeSports[14] = "voleyball";
        listeIds[14] = R.id.voleyball;
        listeDrawingsIds[14] = R.drawable.voleyball;
        listeSports[15] = "waterpolo";
        listeIds[15] = R.id.waterpolo;
        listeDrawingsIds[15] = R.drawable.waterpolo;
    }

    public Sport(String nom, int sportId) {
        this.nom = nom;
        this.sportId = sportId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sport sport = (Sport) o;

        return !(nom != null ? !nom.equals(sport.nom) : sport.nom != null);

    }

    @Override
    public int hashCode() {
        return nom != null ? nom.hashCode() : 0;
    }
}
