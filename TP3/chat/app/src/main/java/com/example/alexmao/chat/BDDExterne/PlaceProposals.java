package com.example.alexmao.chat.BDDExterne;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by filou on 23/03/16.
 */
public class PlaceProposals {
    private List<MeetingPlace> places;

    public PlaceProposals() {
        places = new LinkedList<>();
    }

    public void addPlace(MeetingPlace place) {
        places.add(place);
    }

    public List<MeetingPlace> getPlaces() {
        return places;
    }
}
