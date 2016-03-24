package worktest.filou.activitymaptest;

/**
 * Created by filou on 23/03/16.
 */
public class LocalTimeSlots extends TimeSlots {
    public interface OnRetrieve {
        void onRetrieve(TimeSlots timeSlots);
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private OnRetrieve onRetrieve;

    public OnRetrieve getOnRetrieve() {
        return onRetrieve;
    }

    public void setOnRetrieve(OnRetrieve onRetrieve) {
        this.onRetrieve = onRetrieve;
    }

    public LocalTimeSlots() {
        super();
    }

    public void update(TimeSlots timeSlots) {
        setTimeSlots(timeSlots.getTimeSlots());
        if (onRetrieve != null)
            onRetrieve.onRetrieve(this);
    }
}
