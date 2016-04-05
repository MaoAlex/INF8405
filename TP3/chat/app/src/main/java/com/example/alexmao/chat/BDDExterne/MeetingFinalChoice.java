package com.example.alexmao.chat.BDDExterne;

/**
 * Created by filou on 19/03/16.
 */
public class MeetingFinalChoice {
    private String description;
    private Picture picture;
    private MeetingPlace meetingPlace;
    private long date;

    public MeetingFinalChoice(String description, Picture picture, MeetingPlace meetingPlace, long date) {
        this.description = description;
        this.picture = picture;
        this.meetingPlace = meetingPlace;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public Picture getPicture() {
        return picture;
    }

    public MeetingPlace getMeetingPlace() {
        return meetingPlace;
    }

    public long getDate() {
        return date;
    }
}
