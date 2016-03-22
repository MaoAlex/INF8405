package com.example.alexmao.tp2final;

/**
 * Created by alexMAO on 13/03/2016.
 */
public class Localisation {
    private float positionX_;
    private float positionY_;

    public Localisation(float x, float y){
        positionX_=x;
        positionY_=y;
    }
    public float getPositionX_() {
        return positionX_;
    }

    public void setPositionX_(float positionX_) {
        this.positionX_ = positionX_;
    }

    public float getPositionY_() {
        return positionY_;
    }

    public void setPositionY_(float positionY_) {
        this.positionY_ = positionY_;
    }

    public String toString(){
        return positionX_ + ", " + positionY_;
    }
}
