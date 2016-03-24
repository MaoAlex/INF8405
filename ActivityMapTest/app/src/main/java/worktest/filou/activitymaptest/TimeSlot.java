package worktest.filou.activitymaptest;

/**
 * Created by filou on 19/03/16.
 */
public class TimeSlot {
    private int beginHour;
    private int endHour;
    private String date;

    public TimeSlot() {
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TimeSlot(int beginHour, int endHour, String date) {
        this.beginHour = beginHour;
        this.endHour = endHour;
        this.date = date;

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
