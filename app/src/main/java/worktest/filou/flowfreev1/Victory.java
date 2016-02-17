package worktest.filou.flowfreev1;

/**
 * Created by alexMAO on 16/02/2016.
 */
public class Victory {

    // Step 1 - This interface defines the type of messages I want to communicate to my owner
    public interface VictoryListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        public void showVictory();// or when data has been loaded

    }

    // Constructor where listener events are ignored
   /* public Victory() {
        // set null or default listener or accept as argument to constructor
        this.listener = null;
    }*/

    // Assign the listener implementing events interface that will receive the events
    public void setVictoryListener(VictoryListener listener) {
        this.listener = listener;
    }

    // Constructor where listener events are ignored
    public void VictoryListener() {
        // set null or default listener or accept as argument to constructor
        this.listener = null;
    }

    private VictoryListener listener;
}
