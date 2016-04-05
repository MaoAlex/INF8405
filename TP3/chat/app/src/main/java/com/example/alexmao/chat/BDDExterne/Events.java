package com.example.alexmao.chat.BDDExterne;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by filou on 01/04/16.
 */
public class Events {
    private List<MeetingFinalChoice> meetings;

    public Events() {
        meetings = new ArrayList<>();
    }

    public List<MeetingFinalChoice> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<MeetingFinalChoice> meetings) {
        this.meetings = meetings;
    }

    public void addMeeting(MeetingFinalChoice meetingFinalChoice) {
        meetings.add(meetingFinalChoice);
    }
}
