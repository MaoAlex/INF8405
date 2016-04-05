package com.example.alexmao.chat.BDDExterne;

/**
 * Created by filou on 01/04/16.
 */
public class LocalPicture extends Picture {
    public interface OnRetrieve {
        void onRetrive(Picture picture);
    }

    private OnRetrieve onRetrieve;

    public void setOnRetrieve(OnRetrieve onRetrieve) {
        this.onRetrieve = onRetrieve;
    }

    public void update(Picture picture) {
        setStringChunks(picture.getStringChunks());
        if (onRetrieve != null)
            onRetrieve.onRetrive(picture);
    }
}
