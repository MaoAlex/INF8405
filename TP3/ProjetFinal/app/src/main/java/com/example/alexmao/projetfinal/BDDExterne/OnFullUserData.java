package com.example.alexmao.projetfinal.BDDExterne;

import com.example.alexmao.projetfinal.BDDInterne.ParametreUtilisateurBDD;

/**
 * Created by filou on 16/04/16.
 */
public interface OnFullUserData {
    void onFullUserData(LocalUserProfilEBDD localUserProfilEBDD,
                        Position position, Picture picture,
                       UserParamsEBDD params);
}
