package com.example.alexmao.tp2final.firebase;

import java.util.Date;

/**
 * Created by filou on 19/03/16.
 */
public class MeetingFinalChoice {
    private String description;
    private String picture;
    private MeetingPlace meetingPlace;
    private Date date;

    public MeetingFinalChoice(String description, String picture, MeetingPlace meetingPlace, Date date) {
        this.description = description;
        this.picture = picture;
        this.meetingPlace = meetingPlace;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public MeetingPlace getMeetingPlace() {
        return meetingPlace;
    }

    public Date getDate() {
        return date;
    }
}
