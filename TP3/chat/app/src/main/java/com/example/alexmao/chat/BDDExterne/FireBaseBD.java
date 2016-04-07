package com.example.alexmao.chat.BDDExterne;

/**
 * Classe pour communiquer avec un serveur Firebase,
 * les résultats sont obtenus via des callback
 * les classes ayant le mot "local" dans leur nom possède une callback qui est appelée
 * qd la données est récupérée
 */

import android.content.Context;
import android.util.Log;

import com.example.alexmao.chat.R;
import com.example.alexmao.chat.classeApp.Conversation;
import com.example.alexmao.chat.classeApp.Message;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class FireBaseBD implements RemoteBD {
    private static String TAG = "FireBaseBD";
    private Firebase myFireBaseRef;

    public FireBaseBD(Context context) {
        //connection to BD
        Firebase.setAndroidContext(context);
        //setter l'url du projet firebase
        myFireBaseRef = new Firebase(context.getString(R.string.myFireBaseUrl));
    }

    @Override
    public void updateLocationOnServer(UserProfil user, String id) {
        myFireBaseRef.child("users").child(id).setValue(user);
    }

    @Override
    public String getLastDataFromServer(String path) {
        return myFireBaseRef.child("message").getKey();
    }

    @Override
    public String addUserProfil(UserProfil user) {
        Firebase userBD = myFireBaseRef.child("users").child("profil").push();
        userBD.setValue(user);

        addUserToID(userBD.getKey(), user.getMailAdr());
        return userBD.getKey();
    }

    @Override
    public void addUserToGroup(final String groupID, final String userID) {
        final Firebase groupBD = myFireBaseRef.child("groups").child(groupID);
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                MyGroup groupSnapshot = snapshot.getValue(MyGroup.class);
                groupSnapshot.addMember(userID);
                groupBD.setValue(groupSnapshot);
                Firebase userToGroup = myFireBaseRef.child("usersToGroup").child(userID);
                userToGroup.setValue(groupID);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    private void addUserToID(String id, String mailAdr) {
        Firebase userBD = myFireBaseRef.child("userToID").child(mailAdr.replace('.', ')'));
        userBD.setValue(id);
    }

    //update user when modified on server
    @Override
    public void listenToChangeOnUser(final LocalUser user, final String userBDID) {
        myFireBaseRef.child("users").child("profil").child(userBDID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("onDataChanged", "Child of id: " + userBDID + " modified");
                UserProfil userFromBD = snapshot.getValue(UserProfil.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    @Override
    public void changeGroupName(MyLocalGroup myLocalGroup, String newName) {
        String oldName = myLocalGroup.getGroupName();
        myLocalGroup.setGroupName(newName);
        Firebase groupBD = myFireBaseRef.child("groups").child(myLocalGroup.getDatabaseID());
        groupBD.setValue(myLocalGroup);
        Firebase groupNameToIDBD = myFireBaseRef.child("groupToID").child(oldName);
        groupNameToIDBD.setValue(null);
        groupNameToIDBD = myFireBaseRef.child("groupToID").child(newName);
        groupNameToIDBD.setValue(myLocalGroup.getDatabaseID());
    }

    @Override
    public void changeMail(LocalUser localUser, String newMail) {
        String oldMail = localUser.getMailAdr();
        localUser.setMailAdr(newMail);
        Firebase userBD = myFireBaseRef.child("users").child("profil").child(localUser.getDataBaseId());
        userBD.setValue(localUser);
        Firebase userToID = myFireBaseRef.child("userToID").child(oldMail.replace(".", ")"));
        userToID.setValue(null);
        userToID = myFireBaseRef.child("userToID").child(newMail.replace(".", ")"));
        userToID.setValue(localUser.getDataBaseId());
    }

    @Override
    public String addGroup(MyGroup myGroup) {
        Firebase groupBD = myFireBaseRef.child("groups").push();
        groupBD.setValue(myGroup);
        addGroupNameToID(myGroup.getGroupName(), groupBD.getKey());
        return groupBD.getKey();
    }

    private void addGroupToID(String groupID, String groupName) {
        Firebase groupBD = myFireBaseRef.child("groupToID").child(groupName);
        groupBD.setValue(groupID);
    }

    @Override
    public void getUserProfil(String id, final LocalUser user) {
        Firebase userBD = myFireBaseRef.child("users").child("profil").child(id);
        userBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                UserProfil userSnapshot = snapshot.getValue(UserProfil.class);
                user.update(userSnapshot);
                user.update();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void getUserProfilFromMail(String mailAdr, final LocalUser user) {
        Firebase groupBD = myFireBaseRef.child("userToID").child(mailAdr.replace('.', ')'));
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String id = (String) snapshot.getValue();
                user.setDataBaseId(id);
                getUserProfil(id, user);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void getGroup(String groupID, final MyLocalGroup myGroup) {
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
    public void getGroupFromName(String name, final MyLocalGroup myGroup) {
        Firebase groupBD = myFireBaseRef.child("groupToID").child(name);
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String groupID = (String) snapshot.getValue();
                myGroup.setDatabaseID(groupID);
                getGroup(groupID, myGroup);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    private void addGroupNameToID(String groupName, String groupID) {
        Firebase groupBD = myFireBaseRef.child("groupToID").child(groupName);
        groupBD.setValue(groupID);
    }

    @Override
    public void listenToChangeOnGroup(final MyGroup group, final String groupBDID) {
        myFireBaseRef.child("groups").child(groupBDID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("group of id: " + groupBDID + " modified");
                MyGroup groupFromBD = snapshot.getValue(MyGroup.class);
                group.setMembersID(groupFromBD.getMembersID());
                group.setGroupName(groupFromBD.getGroupName());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    @Override
    public void addMdpToUser(String mail, String mdp) {
        Firebase mdpOnBD = myFireBaseRef.child("mdp").child(mail.replace('.', ')'));
        mdpOnBD.setValue(mdp);
    }

    @Override
    public void getMdp(String mail, final MdpWrapper mdpWrapper) {
        Firebase mdpBD = myFireBaseRef.child("mdp").child(mail.replace('.', ')'));
        mdpBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String mdp = null;
                if (snapshot.exists()) {
                    mdp = (String) snapshot.getValue();
                }
                mdpWrapper.update(mdp);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void getExistGroup(String name, final ExistWrapper existWrapper) {
        Firebase groups = myFireBaseRef.child("groups").child(name);
        groups.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean exist = false;
                if (snapshot.exists()) {
                    exist = true;
                }
                existWrapper.update(exist);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });

    }

    @Override
    public void getExistUser(String mailADR, final ExistWrapper existWrapper) {
        Firebase usersBD = myFireBaseRef.child("userToID").child(mailADR);
        usersBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean exist = false;
                if (snapshot.exists()) {
                    exist = true;
                }
                existWrapper.update(exist);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void addPicToUser(String userID, Picture picture) {
        Firebase picBD = myFireBaseRef.child("pictures").child(userID);
        picBD.setValue(picture);
    }

    @Override
    public void getUserPIc(final LocalUser localUser, final String userID) {
        Firebase picBD = myFireBaseRef.child("pictures").child(userID);
        picBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Picture picture = dataSnapshot.getValue(Picture.class);
                localUser.setProfilPic(picture);
                localUser.update();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void getUserPIc(final LocalPicture localPicture, String userID) {
        Firebase picBD = myFireBaseRef.child("pictures").child(userID);
        picBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Picture picture = dataSnapshot.getValue(Picture.class);
                localPicture.update(picture);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public String addDiscussion(Conversation discussion) {
        Firebase discusionBD = myFireBaseRef.child("discussions").push();
        discusionBD.setValue(discussion);

        return discusionBD.getKey();
    }

    @Override
    public String addMsgToDiscussion(String discussionID, Message conversation) {
        Firebase discusionBD = myFireBaseRef.child("discussions").child(discussionID).child("messages").push();
        discusionBD.setValue(conversation);

        return discusionBD.getKey();
    }

    @Override
    public String notifyUserForMsg(String userID, Message conversation, String conversationID) {
        Firebase discusionBD = myFireBaseRef.child("users").child("unread").child(userID).child(conversationID).push();
        discusionBD.setValue(conversation);

        return discusionBD.getKey();
    }

    @Override
    public void listenToConversation(final String conversationID,
                                     final String userBDID,
                                     final OnMessageReceiveCallback onMessageReceiveCallback ) {
        myFireBaseRef.child("users").child("unread").child(userBDID).child(conversationID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Log.d("onDataChanged", "User " + userBDID + " has a new message");
                        ArrayList<String> ids = new ArrayList<String>();
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            Message conversation = dataSnapshot.getValue(Message.class);
                            onMessageReceiveCallback.onNewMessage(conversation);
                            ids.add(dataSnapshot.getKey());
                        }

                        for (String id: ids) {
                            myFireBaseRef.child("users").child("unread").child(userBDID)
                                    .child(conversationID).child(id).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
    }

    @Override
    public void addPositionToUser(String userID, Position position) {
        Firebase positionBD = myFireBaseRef.child("users").child("position").child(userID);
        positionBD.setValue(position);
    }

    @Override
    public void getUserPosition(String userID, final OnPositionReceived onPositionReceived) {
        Firebase positionBD = myFireBaseRef.child("users").child("position").child(userID);
        positionBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Position position = dataSnapshot.getValue(Position.class);
                onPositionReceived.onPostionReceived(position);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void updateTimeLastChange(String userID, long timeMilli) {
        Firebase timeLastChangeBD = myFireBaseRef.child("users").child("timeLastChange").child(userID);
        timeLastChangeBD.setValue(timeMilli);
    }

    @Override
    public void getTimeLastChange(String userID, final OnTimeReceived timeCallback) {
        Firebase timeLastChangeBD = myFireBaseRef.child("users").child("timeLastChange").child(userID);
        timeLastChangeBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long time = (long) dataSnapshot.getValue();
                timeCallback.onTimeReceived(time);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
