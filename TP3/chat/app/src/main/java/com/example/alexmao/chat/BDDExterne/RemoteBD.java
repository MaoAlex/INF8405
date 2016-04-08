package com.example.alexmao.chat.BDDExterne;

/**
 * Interface de la base de données, les id sont les id au sens firebase,
 * ne pas confondre avec ceux de la BD locale (SQL)
 */
public interface RemoteBD {
    void updateLocationOnServer(UserProfilEBDD user, String id);

    String getLastDataFromServer(String path);

    String addUserProfil(UserProfilEBDD user);

    void getUserProfil(String id, UserProfilEBDD user,
                       OnUserProfilReceived onUserProfilReceivedCallback);

    void getUserProfilFromMail(String mailAdr, LocalUserProfilEBDD user,
                               OnUserProfilReceived onUserProfilReceivedCallback);

    //Mise à jour de l'utilisateur
    void listenToChangeOnUser(final LocalUserProfilEBDD user, final String userBDID);

    String addGroup(MyGroupEBDD myGroupEBDD);

    void addUserToGroup(String groupID, String userID);

    void getGroup(String groupID, MyLocalGroupEBDD myGroup, OnGroupReceived onGroupReceived);

    void getGroupFromName(String name, MyLocalGroupEBDD myGroup, OnGroupReceived onGroupReceived);

    void listenToChangeOnGroup(final MyGroupEBDD group, final String groupBDID);

    void addMdpToUser(String mail, String mdp);

    void addPositionToUser(String userID, Position position);

    void getUserPosition(String userID, OnPositionReceived onPositionReceived);

    void getMdp(String mail, OnStringReceived onMdpReceivedCallback);

    void getExistGroup(String name, OnBooleanReceived onBooleanReceived);

    void getExistUser(String mailADR, OnBooleanReceived onBooleanReceived);

    void changeMail(LocalUserProfilEBDD localUserProfil, String newMail);

    void changeGroupName(MyLocalGroupEBDD myLocalGroup, String newName);

    void addPicToUser(String userID, Picture picture);

    void getUserPIc(LocalUserProfilEBDD localUserProfil, String userID,
                    OnPictureReceived onPictureReceivedCallback);

    void getUserPIc(Picture picture, String userID,
                    OnPictureReceived onPictureReceivedCallback);

    String addDiscussion(ConversationEBDD discussion);

    void updateTimeLastChange(String userID, long timeMIlli);

    void getTimeLastChange(String userID, OnTimeReceived timeCallback);

    String addMsgToDiscussion(String discussionID, MessageBDD message);

    //envoie un message à un utilisateur
    String notifyUserForMsg(String userID, MessageBDD message, String conversationID);

    String addMsgAndNotify(String localUserID, MessageBDD message, String conversationID,
                           MyGroupEBDD receivers);

    //ajoute une callback appellée à chaque nouveau message
    void listenToConversation(String conversationID, final String userBDID,
                              OnMessageReceiveCallback onMessageReceiveCallback);

    String addEvent(MyEventEBDD myEvent);

    void addEventToGroup(String eventID, String groupID);

    void getEventFromGroup(String groupID, OnStringReceived onStringReceived);
}
