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

    void getUserProfil(String id, UserProfil user,
                       OnUserProfilReceived onUserProfilReceivedCallback);

    void getUserProfilFromMail(String mailAdr, LocalUserProfil user,
                               OnUserProfilReceived onUserProfilReceivedCallback);

    //Mise à jour de l'utilisateur
    void listenToChangeOnUser(final LocalUserProfil user, final String userBDID);

    String addGroup(MyGroup myGroup);

    void addUserToGroup(String groupID, String userID);

    void getGroup(String groupID, MyLocalGroup myGroup, OnGroupReceived onGroupReceived);

    void getGroupFromName(String name, MyLocalGroup myGroup, OnGroupReceived onGroupReceived);

    void listenToChangeOnGroup(final MyGroup group, final String groupBDID);

    void addMdpToUser(String mail, String mdp);

    void addPositionToUser(String userID, Position position);

    void getUserPosition(String userID, OnPositionReceived onPositionReceived);

    void getMdp(String mail, OnStringReceived onMdpReceivedCallback);

    void getExistGroup(String name, OnBooleanReceived onBooleanReceived);

    void getExistUser(String mailADR, OnBooleanReceived onBooleanReceived);

    void changeMail(LocalUserProfil localUserProfil, String newMail);

    void changeGroupName(MyLocalGroup myLocalGroup, String newName);

    void addPicToUser(String userID, Picture picture);

    void getUserPIc(LocalUserProfil localUserProfil, String userID,
                    OnPictureReceived onPictureReceivedCallback);

    void getUserPIc(Picture picture, String userID,
                    OnPictureReceived onPictureReceivedCallback);

    String addDiscussion(Conversation discussion);

    void updateTimeLastChange(String userID, long timeMIlli);

    void getTimeLastChange(String userID, OnTimeReceived timeCallback);

    String addMsgToDiscussion(String discussionID, Message message);

    //envoie un message à un utilisateur
    String notifyUserForMsg(String userID, Message message, String conversationID);

    String addMsgAndNotify(String localUserID, Message message, String conversationID,
                           MyGroup receivers);

    //ajoute une callback appellée à chaque nouveau message
    void listenToConversation(String conversationID, final String userBDID,
                              OnMessageReceiveCallback onMessageReceiveCallback);

    String addEvent(MyEvent myEvent);

    void addEventToGroup(String eventID, String groupID);

    void getEventFromGroup(String groupID, OnStringReceived onStringReceived);
}
