package com.example.alexmao.tp2final.firebase;

/**
 * Created by filou on 23/03/16.
 */
public class LocalPlaceProposals extends PlaceProposals {
    public interface OnRetrieve {
        void onRetrieve(PlaceProposals placeProposals);
    }

    private OnRetrieve onRetrieve;

    public void setOnRetrieve(OnRetrieve onRetrieve) {
        this.onRetrieve = onRetrieve;
    }

    public void update() {
        if (onRetrieve != null)
            onRetrieve.onRetrieve(this);
    }
}
