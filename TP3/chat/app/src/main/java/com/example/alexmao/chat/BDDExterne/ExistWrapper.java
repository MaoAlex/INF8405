package com.example.alexmao.chat.BDDExterne;

/**
 * Created by filou on 19/03/16.
 */
public class ExistWrapper {
    private boolean exist = false;

    public interface OnConnectedListener {
        public void onConnected(boolean exist);
    }
    private OnConnectedListener onConnectedListener;

    public ExistWrapper() {
    }

    public void setOnConnectedListener(OnConnectedListener onConnectedListener) {
        this.onConnectedListener = onConnectedListener;
    }

    public void update(boolean b) {
        exist = b;
        if (onConnectedListener != null)
            onConnectedListener.onConnected(b);
    }
}
