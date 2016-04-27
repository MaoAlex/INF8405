package com.example.alexmao.projetfinal.BDDExterne;

import java.util.List;

/**
 * Created by filou on 26/04/16.
 */
public interface OnGroupOnly {
    void onGroupOnly(MyLocalGroupEBDD myLocalGroupEBDD,
                     List<FullUserWrapper> wrappers);
}
