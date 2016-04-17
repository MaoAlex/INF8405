package com.example.alexmao.projetfinal.BDDExterne;

import java.util.List;

/**
 * Created by filou on 16/04/16.
 */
public interface OnFullGroup {
    void onFullGroup(MyLocalGroupEBDD myLocalGroupEBDD,
                     List<FullUserWrapper> wrappers,
                     ConversationEBDD conversationEBDD);
}
