package worktest.filou.activitymaptest;

import java.util.List;

/**
 * Created by filou on 05/03/16.
 */
public interface RemoteBD {
    void updateLocationOnServer(User user, String id) ;
    String getLastDataFromServer(String path);
    String addUser(User user);
    void getUser(String id, LocalUser user);
    void getUserFromMail(String mailAdr, LocalUser user);
    void listenToChangeOnUser(final LocalUser user, final String userBDID);
    String addGroup(MyGroup myGroup);
    void getGroup(String groupID, MyGroup myGroup);
    void getGroupFromName(String name, MyGroup myGroup);
    void listenToChangeOnGroup(final MyGroup group, final String groupBDID);
    void addUserPref(String id, List<String> pref);
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
}
