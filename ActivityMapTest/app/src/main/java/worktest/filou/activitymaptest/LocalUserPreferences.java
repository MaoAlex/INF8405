package worktest.filou.activitymaptest;

/**
 * Created by filou on 23/03/16.
 */
public class LocalUserPreferences extends UserPreferences {
    public interface OnRetrieve {
        void onRetrieve(LocalUserPreferences localUserPreferences);
    }

    private OnRetrieve onRetrieve;

    public void setOnRetrieve(OnRetrieve onRetrieve) {
        this.onRetrieve = onRetrieve;
    }

    public void update() {
        if (onRetrieve != null)
            onRetrieve.onRetrieve(this);
    }

    public OnRetrieve getOnRetrieve() {
        return onRetrieve;
    }
}
