package com.example.alexmao.chat.BDDExterne;

import com.example.alexmao.chat.classeApp.Conversation;
import com.example.alexmao.chat.classeApp.Message;

/**
 * Interface de la base de données, les id sont les id au sens firebase,
 * ne pas confondre avec ceux de la BD locale (SQL)
 */
public interface RemoteBD {
    void updateLocationOnServer(UserProfil user, String id);

    String getLastDataFromServer(String path);

    String addUserProfil(UserProfil user);

    void getUserProfil(String id, LocalUser user);

    void getUserProfilFromMail(String mailAdr, LocalUser user);

    //Mise à jour de l'utilisateur
    void listenToChangeOnUser(final LocalUser user, final String userBDID);

    String addGroup(MyGroup myGroup);

    void addUserToGroup(String groupID, String userID);

    void getGroup(String groupID, MyLocalGroup myGroup);

    void getGroupFromName(String name, MyLocalGroup myGroup);

    void listenToChangeOnGroup(final MyGroup group, final String groupBDID);

    void addMdpToUser(String mail, String mdp);

    void addPositionToUser(String userID, Position position);

    void getUserPosition(String userID, OnPositionReceived onPositionReceived);

    void getMdp(String mail, MdpWrapper mdpWrapper);

    void getExistGroup(String name, ExistWrapper existWrapper);

    void getExistUser(String mailADR, ExistWrapper existWrapper);

    void changeMail(LocalUser localUser, String newMail);

    void changeGroupName(MyLocalGroup myLocalGroup, String newName);

    void addPicToUser(String userID, Picture picture);

    void getUserPIc(LocalUser localUser, String userID);

    void getUserPIc(LocalPicture picture, String userID);

    String addDiscussion(Conversation discussion);

    void updateTimeLastChange(String userID, long timeMIlli);

    void getTimeLastChange(String userID, OnTimeReceived timeCallback);

    String addMsgToDiscussion(String discussionID, Message message);

    String notifyUserForMsg(String userID, Message message, String conversationID);

    void listenToConversation(String conversationID, final String userBDID,
                              OnMessageReceiveCallback onMessageReceiveCallback);
}
