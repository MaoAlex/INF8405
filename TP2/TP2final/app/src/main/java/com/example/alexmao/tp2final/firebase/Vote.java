package com.example.alexmao.tp2final.firebase;

/**
 * Created by filou on 19/03/16.
 */
public class Vote {
    private String userID;
    private int userChoice;

    public Vote(String userID, int userChoice) {
        this.userID = userID;
        this.userChoice = userChoice;
    }

    public String getUserID() {
        return userID;
    }

    public int getUserChoice() {
        return userChoice;
    }
}
