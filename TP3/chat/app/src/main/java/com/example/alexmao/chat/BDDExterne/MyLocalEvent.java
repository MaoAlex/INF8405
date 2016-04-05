package com.example.alexmao.chat.BDDExterne;

/**
 * Created by filou on 01/04/16.
 */
public class MyLocalEvent extends Events {
    public interface ChangeListener {
        void onChange(MyLocalEvent myLocalEvent);
    }

    private ChangeListener changeListener;

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public void update(Events events) {
        setMeetings(events.getMeetings());
        if (changeListener != null)
            changeListener.onChange(this);
    }
}
