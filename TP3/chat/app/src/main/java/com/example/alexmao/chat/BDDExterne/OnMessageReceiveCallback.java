package com.example.alexmao.chat.BDDExterne;

import com.example.alexmao.chat.classeApp.Message;

/**
 * Created by filou on 02/04/16.
 */
public interface OnMessageReceiveCallback {
    void onNewMessage(Message message);
}
