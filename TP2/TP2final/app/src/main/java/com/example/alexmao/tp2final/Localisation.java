package com.example.alexmao.tp2final;

/**
 * Created by alexMAO on 13/03/2016.
 */
public class Localisation {
    private double positionX_;
    private double positionY_;

    public Localisation(double x, double y){
        positionX_=x;
        positionY_=y;
    }
    public double getPositionX_() {
        return positionX_;
    }

    public void setPositionX_(double positionX_) {
        this.positionX_ = positionX_;
    }

    public double getPositionY_() {
        return positionY_;
    }

    public void setPositionY_(double positionY_) {
        this.positionY_ = positionY_;
    }

    public String toString(){
        return positionX_ + ", " + positionY_;
    }
}
