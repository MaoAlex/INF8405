package com.example.alexmao.chat.BDDExterne;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by filou on 23/03/16.
 */
public class UserPreferences {
    private List<String> preferences;

    public UserPreferences() {
        preferences = new LinkedList<>();
    }

    public void addPreference(String pref) {
        preferences.add(pref);
    }

    public List<String> getPreferences() {
        return preferences;
    }
}
