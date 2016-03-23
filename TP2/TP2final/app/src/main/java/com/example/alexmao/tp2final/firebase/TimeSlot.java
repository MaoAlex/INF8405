package com.example.alexmao.tp2final.firebase;

/**
 * Created by filou on 19/03/16.
 */
public class TimeSlot {
    private int beginHour;
    private int endHour;

    public TimeSlot(int beginHour, int endHour) {
        this.beginHour = beginHour;
        this.endHour = endHour;
    }

    public int getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(int beginHour) {
        this.beginHour = beginHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }
}
