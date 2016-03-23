package worktest.filou.activitymaptest;

/**
 * Created by filou on 19/03/16.
 */
public class MdpWrapper {
    private String mdp;
    public interface onRetrieveListener {
        public void onRetrieve(String mdp);
    }

    private onRetrieveListener onRetrieveListener;

    public MdpWrapper(String mdp) {
        this.mdp = mdp;
    }

    public void setOnRetrieveListener(MdpWrapper.onRetrieveListener onRetrieveListener) {
        this.onRetrieveListener = onRetrieveListener;
    }

    public void update(String mdp) {
//        this.mdp = mdp;
        if (onRetrieveListener != null) {
            onRetrieveListener.onRetrieve(mdp);
        }
    }

    public boolean exist() {
        return mdp != null;
    }

    public String getMdp() {
        return mdp;
    }
}
