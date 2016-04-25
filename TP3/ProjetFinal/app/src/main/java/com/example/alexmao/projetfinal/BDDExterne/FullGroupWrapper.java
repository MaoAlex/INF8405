package com.example.alexmao.projetfinal.BDDExterne;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by filou on 21/04/16.
 */
public class FullGroupWrapper {
    private ConversationEBDD conversationEBDD;
    private MyLocalGroupEBDD  myLocalGroupEBDD;
    private MyLocalEventEBDD myLocalEventEBDD;
    private List<FullUserWrapper> fullUserWrappers;

    public FullGroupWrapper(ConversationEBDD conversationEBDD,
                            MyLocalGroupEBDD myLocalGroupEBDD,
                            MyLocalEventEBDD myLocalEventEBDD,
                            List<FullUserWrapper> fullUserWrappers) {
        this.conversationEBDD = conversationEBDD;
        this.myLocalGroupEBDD = myLocalGroupEBDD;
        this.myLocalEventEBDD = myLocalEventEBDD;
        this.fullUserWrappers = fullUserWrappers;
    }

    public FullGroupWrapper() {
        fullUserWrappers = new ArrayList<>();
    }

    public ConversationEBDD getConversationEBDD() {
        return conversationEBDD;
    }

    public void setConversationEBDD(ConversationEBDD conversationEBDD) {
        this.conversationEBDD = conversationEBDD;
    }

    public MyLocalGroupEBDD getMyLocalGroupEBDD() {
        return myLocalGroupEBDD;
    }

    public void setMyLocalGroupEBDD(MyLocalGroupEBDD myLocalGroupEBDD) {
        this.myLocalGroupEBDD = myLocalGroupEBDD;
    }

    public MyLocalEventEBDD getMyLocalEventEBDD() {
        return myLocalEventEBDD;
    }

    public void setMyLocalEventEBDD(MyLocalEventEBDD myLocalEventEBDD) {
        this.myLocalEventEBDD = myLocalEventEBDD;
    }

    public List<FullUserWrapper> getFullUserWrappers() {
        return fullUserWrappers;
    }

    public void setFullUserWrappers(List<FullUserWrapper> fullUserWrappers) {
        this.fullUserWrappers = fullUserWrappers;
    }

    @Override
    public String toString() {
        String mToString = "FullGroupWrapper{" +
                "conversationEBDD=" + conversationEBDD +
                ", myLocalGroupEBDD=" + myLocalGroupEBDD +
                ", myLocalEventEBDD=" + myLocalEventEBDD;
        mToString += ", ";
        for (FullUserWrapper fullUserWrapper: fullUserWrappers) {
            mToString += fullUserWrapper.toString();
        }
        mToString += " }\n";
        return mToString;
    }
}
