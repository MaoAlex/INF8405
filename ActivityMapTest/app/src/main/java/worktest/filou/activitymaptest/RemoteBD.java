package worktest.filou.activitymaptest;

import java.security.acl.Group;

/**
 * Created by filou on 05/03/16.
 */
public interface RemoteBD {

    void updateLocationOnServer(User user, String id) ;
    String getLastDataFromServer(String path);
    String addUser(User user);
    void getUser(String id, User user);
    void getUserFromMail(String mailAdr, LocalUser user);
    void listenToChangeOnUser(final User user, final String userBDID);
    String addGroup(MyGroup myGroup);
    void getGroup(String groupID, MyGroup myGroup);
    void getGroupFromName(String name, MyGroup myGroup);
    void listenToChangeOnGroup(final MyGroup group, final String groupBDID);
}
