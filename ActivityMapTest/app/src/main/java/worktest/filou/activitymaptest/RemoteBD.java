package worktest.filou.activitymaptest;

import java.util.List;

/**
 * Created by filou on 05/03/16.
 */
public interface RemoteBD {
    void setOnQuery(OnQuery onQuery);
    void updateLocationOnServer(User user, String id) ;
    String getLastDataFromServer(String path);
    String addUser(User user);
    void getUser(String id, LocalUser user);
    void getUserFromMail(String mailAdr, LocalUser user);
    void listenToChangeOnUser(final LocalUser user, final String userBDID);
    String addGroup(MyGroup myGroup);
    void addUserToGroup(String groupID, String userID);
    void getGroup(String groupID, MyGroup myGroup);
    void getGroupFromName(String name, MyGroup myGroup);
    void listenToChangeOnGroup(final MyGroup group, final String groupBDID);
    void addUserPref(String id, UserPreferences pref);
    void getUserPref(String id, LocalUserPreferences preferences);
    void addMdpToUser(String mail, String mdp);
    void getMdp(String mail, MdpWrapper mdpWrapper);
    void getPlaceProposal(String groupID, LocalPlaceProposals placeProposals);
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
    void addPlacesProposal(PlaceProposals meetingPlace, String groupID);
    void addTimeProposal(TimeSlots timeSlots, String groupID);
    void getTimeProposal(LocalTimeSlots timeSlots, String groupID);
    void setPlaceChoice(int index, String userID);
    void setTimeChoice(int index, String userID);
    void update(LocalUser localUser, MyLocalGroup myLocalGroup,
                LocalUserPreferences localUserPreferences, OnUpdateComplete onUpdateComplete);
}
