package worktest.filou.activitymaptest;

/**
 * Created by filou on 23/03/16.
 */
public class LocalTimeSlot extends TimeSlot {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalTimeSlot(int beginHour, int endHour, String date) {
        super(beginHour, endHour, date);
    }

    public LocalTimeSlot(TimeSlot timeSlot) {
        setBeginHour(timeSlot.getBeginHour());
        setEndHour(timeSlot.getEndHour());
        setDate(timeSlot.getDate());
    }
}
