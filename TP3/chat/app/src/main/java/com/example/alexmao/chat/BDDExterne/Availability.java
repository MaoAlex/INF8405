package com.example.alexmao.chat.BDDExterne;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by filou on 19/03/16.
 */
public class Availability {
    private Map<Date, List<TimeSlot>> dayToTimeSlot;

    public Availability() {
        dayToTimeSlot = new HashMap<>();
    }

    private void createList(Date date) {
        dayToTimeSlot.put(date, new LinkedList<TimeSlot>());
    }

    public void addTimeSlot(Date date, TimeSlot timeSlot) {
        List<TimeSlot> listTmp = dayToTimeSlot.get(date);
        if (listTmp == null) {
            createList(date);
            listTmp = dayToTimeSlot.get(date);
        }

        listTmp.add(timeSlot);
    }

    public List<TimeSlot> getTimeSlotsFromDate(Date date) {
        return dayToTimeSlot.get(date);
    }
}
