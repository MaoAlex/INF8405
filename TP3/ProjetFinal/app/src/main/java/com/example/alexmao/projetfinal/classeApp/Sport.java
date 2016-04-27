package com.example.alexmao.projetfinal.classeApp;

import android.content.Context;

import com.example.alexmao.projetfinal.R;

import java.util.ArrayList;

/**
 * Created by Fabien on 27/04/2016.
 */
public class Sport {
    private String nom;
    private int drawableId;

    public static String[] listeSports;
    public static int[] listeDrawablesId;

    public static void listeSports() {
        listeSports = new String[16];
        listeDrawablesId = new int[16];

        listeSports[0] = "art_martial";
        listeDrawablesId[0] = R.drawable.art_martial;
        listeSports[1] = "athletisme";
        listeDrawablesId[1] = R.drawable.athletisme;
        listeSports[2] = "badminton";
        listeDrawablesId[2] = R.drawable.badminton;
        listeSports[3] = "baseball";
        listeDrawablesId[3] = R.drawable.baseball;
        listeSports[4] = "basketball";
        listeDrawablesId[4] = R.drawable.basketball;
        listeSports[5] = "boxe";
        listeDrawablesId[5] = R.drawable.boxe;
        listeSports[6] = "fitness";
        listeDrawablesId[6] = R.drawable.fitness;
        listeSports[7] = "football";
        listeDrawablesId[7] = R.drawable.football;
        listeSports[8] = "golf";
        listeDrawablesId[8] = R.drawable.golf;
        listeSports[9] = "hockey";
        listeDrawablesId[9] = R.drawable.hockey;
        listeSports[10] = "natation";
        listeDrawablesId[10] = R.drawable.natation;
        listeSports[11] = "pingpong";
        listeDrawablesId[11] = R.drawable.pingpong;
        listeSports[12] = "rugby";
        listeDrawablesId[12] = R.drawable.rugby;
        listeSports[13] = "tennis";
        listeDrawablesId[13] = R.drawable.tennis;
        listeSports[14] = "voleyball";
        listeDrawablesId[14] = R.drawable.voleyball;
        listeSports[15] = "waterpolo";
        listeDrawablesId[15] = R.drawable.waterpolo;
    }

    public Sport(String nom, int drawableId) {
        this.nom = nom;
        this.drawableId = drawableId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
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
