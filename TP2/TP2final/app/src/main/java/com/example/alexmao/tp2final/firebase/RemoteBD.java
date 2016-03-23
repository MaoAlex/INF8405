package com.example.alexmao.tp2final.firebase;

/**
 * Created by filou on 05/03/16.
 */
public interface RemoteBD {
    void updateLocationOnServer(UserFirebase user, String id) ;
    String getLastDataFromServer(String path);
    String addUser(UserFirebase user);
    void getUser(String id, LocalUser user);
    void getUserFromMail(String mailAdr, LocalUser user);
    //Mise à jour de l'utilisateur
    void listenToChangeOnUser(final LocalUser user, final String userBDID);
    String addGroup(MyGroup myGroup);
    void addUserToGroup(String groupID, String userID);

    void getGroup(String groupID, MyGroup myGroup);
    void getGroupFromName(String name, MyGroup myGroup);

    void listenToChangeOnGroup(final MyGroup group, final String groupBDID);
    void addMdpToUser(String mail, String mdp);
    void getMdp(String mail, MdpWrapper mdpWrapper);
    void getExistGroup(String name, ExistWrapper existWrapper);
    void getExistUser(String mailADR, ExistWrapper existWrapper);
    void requestAvailabilities(String userID);
    void requestChoicePlace(String userID);
    void requestChoiceTime(String userID);
    void listenToRequest(String userID);
    void listenToTimeRespond(String userID);
    void listenToPlaceRespond(String userID);
    void listenToAvailabilities(String userID);
    void addMeeting(MeetingFinalChoice meetingFinalChoice, String groupID);
    void setPlaceChoice(int index, String userID);
    void setTimeChoice(int index, String userID);
    void getUserPref(String id, LocalUserPreferences preferences);
    void changeMail(LocalUser localUser, String newMail);
    //Recuperation de l'utilisateur, des groupes auxquels il appartient et mise à jour.
    void update(LocalUser localUser, MyLocalGroup myLocalGroup,
                LocalUserPreferences localUserPreferences,
                OnUpdateComplete onUpdateComplete);
    void addUserPref(String id, UserPreferences pref);
    void addTimeProposal(TimeSlots timeSlots, String groupID);
    void addPlacesProposal(PlaceProposals meetingPlace, String groupID);
    void getPlaceProposal(String groupID, final LocalPlaceProposals placeProposals);
    void getTimeProposal(final LocalTimeSlots timeSlots, String groupID);
    void changeGroupName(MyLocalGroup myLocalGroup, String newName);

    void setOnQuery(OnQuery onQuery);
}
