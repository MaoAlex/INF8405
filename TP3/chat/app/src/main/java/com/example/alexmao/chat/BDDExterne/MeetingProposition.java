package com.example.alexmao.chat.BDDExterne;

import java.util.Date;

/**
 * Created by filou on 19/03/16.
 */
public class MeetingProposition {
    private MeetingPlace meetingPlace;
    private Date date;

    public MeetingProposition(Date date, MeetingPlace meetingPlace) {
        this.date = date;
        this.meetingPlace = meetingPlace;
    }

    public MeetingPlace getMeetingPlace() {
        return meetingPlace;
    }

    public Date getDate() {
        return date;
    }
}
