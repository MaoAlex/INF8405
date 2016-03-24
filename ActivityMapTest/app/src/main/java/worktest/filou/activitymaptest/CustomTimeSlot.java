package worktest.filou.activitymaptest;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by filou on 23/03/16.
 */
public class CustomTimeSlot extends TimeSlot {
    private List<String> idsOK;
    private int groupSize;

    public CustomTimeSlot(int beginHour, int endHour, String date, int groupSize) {
        super(beginHour, endHour, date);
        this.idsOK = new LinkedList<>();
        this.groupSize = groupSize;
    }

    public boolean isIDin(String id) {
        return idsOK.contains(id);
    }

    public void addID(String id) {
        idsOK.add(id);
    }

    public boolean isOKforEvery() {
        return idsOK.size() == groupSize;
    }
}
