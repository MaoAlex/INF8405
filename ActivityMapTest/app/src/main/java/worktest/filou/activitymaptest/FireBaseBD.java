package worktest.filou.activitymaptest;

/**
 * Created by filou on 05/03/16.
 */

import android.content.Context;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FireBaseBD implements RemoteBD {
    Firebase myFireBaseRef;

    public FireBaseBD(Context context) {
        //connection to BD
        Firebase.setAndroidContext(context);
        myFireBaseRef = new Firebase(context.getString(R.string.myFireBaseUrl));
    }

    @Override
    public void updateLocationOnServer(User user, String id) {
        myFireBaseRef.child("users" ).child(id).setValue(user);
    }

    @Override
    public String getLastDataFromServer(String path) {
        return myFireBaseRef.child("message").getKey();
    }

    @Override
    public String addUser(User user) {
        Firebase userBD = myFireBaseRef.child("users").push();
        userBD.setValue(user);

        addUserToID(userBD.getKey(), user.getMailAdr());
        return userBD.getKey();
    }

    private void addUserToID(String id, String mailAdr) {
        Firebase userBD = myFireBaseRef.child("userToID").child(mailAdr.replace('.', ')'));
        userBD.setValue(id);
    }

    //update user when modified on server
    @Override
    public void listenToChangeOnUser(final User user, final String userBDID) {
        myFireBaseRef.child("users").child(userBDID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("onDataChanged", "Child of id: " + userBDID + " modified");
                User userFromBD = snapshot.getValue(User.class);
                user.setLat(userFromBD.getLat());
                Log.d("onDataChange: ", "new latitude " + user.getLat());
                user.setLongi(userFromBD.getLongi());
                Log.d("onDataChange: ", "new longitude " + user.getLongi());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    @Override
    public String addGroup(MyGroup myGroup) {
        Firebase groupBD = myFireBaseRef.child("groups").push();
        groupBD.setValue(myGroup);

        return groupBD.getKey();
    }

    private void addGroupToID(String groupID, String groupName) {
        Firebase groupBD = myFireBaseRef.child("groupToID").child(groupName);
        groupBD.setValue(groupID);
    }

    @Override
    public void getUser(String id, final User user) {
        Firebase userBD = myFireBaseRef.child("users").child(id);
        userBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User userSnapshot = snapshot.getValue(User.class);
                user.update(userSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void getUserFromMail(String mailAdr, final LocalUser user) {
        Firebase groupBD = myFireBaseRef.child("userToID").child(mailAdr.replace(')', '.'));
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String id = (String) snapshot.getValue();
                user.setDataBaseId(id);
                getUser(id, user);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void getGroup(String groupID, final MyGroup myGroup) {
        Firebase groupBD = myFireBaseRef.child("groups").child(groupID);
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                MyGroup groupSnapshot = snapshot.getValue(MyGroup.class);
                myGroup.update(groupSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void getGroupFromName(String name, final MyGroup myGroup) {
        Firebase groupBD = myFireBaseRef.child("groupToID").child(name);
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String groupID = (String) snapshot.getValue();
                getGroup(groupID, myGroup);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void listenToChangeOnGroup(final MyGroup group, final String groupBDID) {
        myFireBaseRef.child("groups").child(groupBDID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("group of id: " + groupBDID + " modified");
                MyGroup groupFromBD = snapshot.getValue(MyGroup.class);
                group.setMembersID(groupFromBD.getMembersID());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
}
