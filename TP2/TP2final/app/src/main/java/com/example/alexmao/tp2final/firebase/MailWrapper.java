package com.example.alexmao.tp2final.firebase;

/**
 * Created by filou on 22/03/16.
 */
public class MailWrapper {
    private String mail;
    public interface OnRespond {
        void onRespond(boolean b);
    }

    private OnRespond onRespond;

    public MailWrapper(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setOnRespond(OnRespond onRespond) {

        this.onRespond = onRespond;
    }

    public void update(boolean b) {
        if (onRespond != null)
            onRespond.onRespond(b);
    }
}
