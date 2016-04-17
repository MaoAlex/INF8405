package com.example.alexmao.projetfinal.BDDExterne;

import java.util.Map;

/**
 * Interface de la base de données, les id sont les id au sens firebase,
 * ne pas confondre avec ceux de la BD locale (SQL)
 */
public interface RemoteBD {
    void updateLocationOnServer(UtilisateurProfilEBDD user, String id);

    String getLastDataFromServer(String path);

    String addUserProfil(UtilisateurProfilEBDD user);

    void getUserProfil(String id, UtilisateurProfilEBDD user,
                       OnUserProfilReceived onUserProfilReceivedCallback);

    void getUserProfilFromMail(String mailAdr, LocalUserProfilEBDD user,
                               OnUserProfilReceived onUserProfilReceivedCallback);

    //Mise à jour de l'utilisateur
    void listenToChangeOnUser(final LocalUserProfilEBDD user, final String userBDID);

    String addGroup(MyGroupEBDD myGroupEBDD);

    void addUserToGroup(String groupID, String userID);

    void getGroup(String groupID, MyLocalGroupEBDD myGroup,
                  OnGroupReceived onGroupReceivedCallback);

    void getGroupFromName(String name, MyLocalGroupEBDD myGroup,
                          OnGroupReceived onGroupReceivedCallback);

    void listenToChangeOnGroup(final MyGroupEBDD group, final String groupBDID);

    void addMdpToUser(String mail, String mdp);

    void addPositionToUser(String userID, Position position);

    void getUserPosition(String userID, OnPositionReceived onPositionReceivedCallback);

    void listenToPositionChanges(String userID, OnPositionReceivedForUser onPositionReceivedCallback);

    void getMdp(String mail, OnStringReceived onMdpReceivedCallbackCallback);

    void getExistGroup(String name, OnBooleanReceived onBooleanReceivedCallback);

    void getExistUser(String mailADR, OnBooleanReceived onBooleanReceivedCallback);

    void changeMail(LocalUserProfilEBDD localUserProfil, String newMail);

    void changeGroupName(MyLocalGroupEBDD myLocalGroup, String newName);

    void addPicToUser(String userID, Picture picture);

    void getUserPIc(LocalUserProfilEBDD localUserProfil, String userID,
                    OnPictureReceived onPictureReceivedCallback);

    void getUserPIc(Picture picture, String userID,
                    OnPictureReceived onPictureReceivedCallback);

    String addDiscussion(ConversationEBDD discussion);

    void getDiscussion(String discussionID, OnConversationReceived onConversationRecievedCallback);

    void updateTimeLastChange(String userID, long timeMIlli);

    void getTimeLastChange(String userID, OnTimeReceived timeCallback);

    String addMsgToDiscussion(String discussionID, MessageEBDD message);

    //envoie un message à un utilisateur
    String notifyUserForMsg(String userID, MessageEBDD message, String conversationID);

    String addMsgAndNotify(String localUserID, MessageEBDD message, String conversationID,
                           MyGroupEBDD receivers);

    //ajoute une callback appellée à chaque nouveau message
    void listenToConversation(String conversationID, final String userBDID,
                              OnMessageReceiveCallback onMessageReceiveCallback);

    String addEvent(MyEventEBDD myEvent);

    void addEventToGroup(String eventID, String groupID);

    void getEventFromGroup(String groupID, OnStringReceived onStringReceived);

    void addUserParam(UserParamsEBDD userParamsEBDD, String userID);

    void getUserParam(String userID, OnUserParamReceived onUserParamReceivedCallback);

    String addNotificationToUser(String userID, NotificationBDD notificationBDD);

    void listenToNotification(String userID, Map<String,
            OnNotificationReceived> typeToActionCallback);
}
