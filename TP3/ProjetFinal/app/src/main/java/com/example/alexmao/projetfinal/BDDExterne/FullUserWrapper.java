package com.example.alexmao.projetfinal.BDDExterne;

/**
 * Created by filou on 16/04/16.
 */
public class FullUserWrapper {
    private LocalUserProfilEBDD localUserProfilEBDD;
    private Position position;
    private Picture picture;
    private UserParamsEBDD userParamsEBDD;

    public FullUserWrapper() {
    }

    public FullUserWrapper(LocalUserProfilEBDD localUserProfilEBDD,
                           Position position, Picture picture, UserParamsEBDD userParamsEBDD) {
        this.localUserProfilEBDD = localUserProfilEBDD;
        this.position = position;
        this.picture = picture;
        this.userParamsEBDD = userParamsEBDD;
    }

    public LocalUserProfilEBDD getLocalUserProfilEBDD() {
        return localUserProfilEBDD;
    }

    public void setLocalUserProfilEBDD(LocalUserProfilEBDD localUserProfilEBDD) {
        this.localUserProfilEBDD = localUserProfilEBDD;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public UserParamsEBDD getUserParamsEBDD() {
        return userParamsEBDD;
    }

    public void setUserParamsEBDD(UserParamsEBDD userParamsEBDD) {
        this.userParamsEBDD = userParamsEBDD;
    }

    @Override
    public String toString() {
        return "FullUserWrapper{" +
                "userParamsEBDD=" + userParamsEBDD +
                ", position=" + position +
                ", localUserProfilEBDD=" + localUserProfilEBDD +
                '}';
    }
}
