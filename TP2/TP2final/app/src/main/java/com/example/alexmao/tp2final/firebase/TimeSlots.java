package com.example.alexmao.tp2final.firebase;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by filou on 23/03/16.
 */
public class TimeSlots {
    private List<TimeSlot> timeSlots;

    public TimeSlots() {
        timeSlots = new LinkedList<>();
    }

    public void addTimeSlot(TimeSlot timeSlot) {
        timeSlots.add(timeSlot);
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
